package ru.german.springprograms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.german.springprograms.dto.BusinessLicenseDto;
import ru.german.springprograms.entities.BusinessLicense;
import ru.german.springprograms.entities.StudyProgram;
import ru.german.springprograms.mapper.BusinessLicenseMapper;
import ru.german.springprograms.repos.BusinessLicenseRepos;
import ru.german.springprograms.repos.StudyProgramRepos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/businessLicenses")
public class BusinessLicenseController {
    private Map<String, String> idToPathMap= new HashMap<String, String>();
    @Autowired
    private StudyProgramRepos studyProgramRepos;
    @Autowired
    private BusinessLicenseRepos businessLicenseRepos;
    @Autowired
    private BusinessLicenseMapper businessLicenseMapper;

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessLicenseDto> getAllBusinessLicenses(){
        return businessLicenseRepos.findAll().stream()
                .map(businessLicenseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessLicenseDto getById(@PathVariable Integer id){
        Optional<BusinessLicense> businessLicenseOptional = businessLicenseRepos.findById(id);
        return businessLicenseOptional.map(businessLicenseMapper::mapToDto).orElse(null);
    }

    @GetMapping(path = "/findLicense", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findLicense(@RequestParam String organizationNumber, @RequestParam String programName){
        List<BusinessLicenseDto> licenseDtoList = getAllBusinessLicenses();

        for (BusinessLicenseDto businessLicenseDto:licenseDtoList) {
            if ((businessLicenseDto.getOrganizationNumber() == Integer.parseInt(organizationNumber))
                    && (businessLicenseDto.getProgramName().equals(programName))
                    && (businessLicenseDto.getCondition().equals("Действует"))) {
                String path= "D:/Tutor/"+programName;
                String id = UUID.randomUUID().toString();
                idToPathMap.put(id, path);
                return "{\"id\":\""+id+"\"}";
            }
        }
        throw new RuntimeException("License is not available");
    }
    @GetMapping(path = "/getFile/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile (@PathVariable String id) throws IOException {
        String s = idToPathMap.get(id);
        File file = new File(s);
        if (file.exists()) return Files.readAllBytes(file.toPath());
        throw new RuntimeException("File not found");
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessLicenseDto add(@RequestBody BusinessLicenseDto businessLicenseDto){
        Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findByName(businessLicenseDto.getProgramName());
        if (studyProgramOptional.isPresent() && studyProgramOptional.get().getPrice()!=null) {
            BusinessLicense businessLicense = new BusinessLicense();
            businessLicense.setCondition("Ожидание");
            businessLicense.updateFromDto(businessLicenseDto);
            Integer price = studyProgramRepos.findByName(businessLicenseDto.getProgramName()).get().getPrice();
            businessLicense.setPrice(businessLicense.getUsersLimit() * price);
            businessLicenseRepos.save(businessLicense);
            return businessLicenseMapper.mapToDto(businessLicense);
        }
        return null;
    }

    @DeleteMapping(path= "/delete")
    public long delete (@RequestParam Integer id){
        Long recordsBeforeDelete = businessLicenseRepos.count();
        if (businessLicenseRepos.findById(id).isPresent())businessLicenseRepos.deleteById(id);
        Long recordsAfterDelete = businessLicenseRepos.count();
        return recordsBeforeDelete-recordsAfterDelete;
    }

    @PutMapping(path = "/changeConditionToOn", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessLicenseDto changeConditionToOn(@RequestParam Integer id){
        Optional<BusinessLicense> byId = businessLicenseRepos.findById(id);
        if (byId.isPresent()){
            BusinessLicense businessLicense = byId.get();
            businessLicense.setCondition("Действует");
            businessLicenseRepos.save(businessLicense);
            return businessLicenseMapper.mapToDto(businessLicense);
        }
        return null;
    }

    @PutMapping(path = "/changeConditionToOff", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessLicenseDto changeConditionToOff(@RequestParam Integer id){
        Optional<BusinessLicense> byId = businessLicenseRepos.findById(id);
        if (byId.isPresent()){
            BusinessLicense businessLicense = byId.get();
            businessLicense.setCondition("Истекла");
            businessLicenseRepos.save(businessLicense);
            return businessLicenseMapper.mapToDto(businessLicense);
        }
        return null;
    }
}
