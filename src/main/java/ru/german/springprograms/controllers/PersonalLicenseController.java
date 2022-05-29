package ru.german.springprograms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.german.springprograms.dto.PersonalLicenseDto;
import ru.german.springprograms.entities.PersonalLicense;
import ru.german.springprograms.entities.StudyProgram;
import ru.german.springprograms.mapper.PersonalLicenseMapper;
import ru.german.springprograms.repos.PersonalLicenseRepos;
import ru.german.springprograms.repos.StudyProgramRepos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personalLicenses")
public class PersonalLicenseController {
    private Map<String, String> idToPathMap= new HashMap<String, String>();
    @Autowired
    private StudyProgramRepos studyProgramRepos;
    @Autowired
    private PersonalLicenseRepos personalLicenseRepos;
    @Autowired
    private PersonalLicenseMapper personalLicenseMapper;

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonalLicenseDto> getAllPersonalLicenses(){
        return personalLicenseRepos.findAll().stream()
                .map(personalLicenseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonalLicenseDto getById(@PathVariable Integer id){
        Optional<PersonalLicense> personalLicenseOptional = personalLicenseRepos.findById(id);
        return personalLicenseOptional.map(personalLicenseMapper::mapToDto).orElse(null);
    }

    @GetMapping(path = "/findLicense", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findLicense(@RequestParam String userId, @RequestParam String programName){
        List<PersonalLicenseDto> licenseDtoList = getAllPersonalLicenses();

        for (PersonalLicenseDto personalLicenseDto:licenseDtoList) {
            if ((personalLicenseDto.getUserId() == Integer.parseInt(userId))
                    && (personalLicenseDto.getProgramName().equals(programName))
                    && (personalLicenseDto.getCondition().equals("Действует")) ) {
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
    public PersonalLicenseDto add(@RequestBody PersonalLicenseDto personalLicenseDto){
        Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findByName(personalLicenseDto.getProgramName());
        if (studyProgramOptional.isPresent() && studyProgramOptional.get().getPrice()!=null) {
            PersonalLicense personalLicense = new PersonalLicense();
            personalLicense.setCondition("Ожидание");
            personalLicense.updateFromDto(personalLicenseDto);
            Integer price = studyProgramRepos.findByName(personalLicenseDto.getProgramName()).get().getPrice();
            personalLicense.setPrice(price);
            personalLicenseRepos.save(personalLicense);
            return personalLicenseMapper.mapToDto(personalLicense);
        }
        return null;
    }

    @DeleteMapping(path= "/delete")
    public long delete (@RequestParam Integer id){
        Long recordsBeforeDelete = personalLicenseRepos.count();
        if (personalLicenseRepos.findById(id).isPresent())personalLicenseRepos.deleteById(id);
        Long recordsAfterDelete = personalLicenseRepos.count();
        return recordsBeforeDelete-recordsAfterDelete;
    }

    @PutMapping(path = "/changeConditionToOn", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonalLicenseDto changeConditionToOn(@RequestParam Integer id){
        Optional<PersonalLicense> byId = personalLicenseRepos.findById(id);
        if (byId.isPresent()){
            PersonalLicense personalLicense = byId.get();
            personalLicense.setCondition("Действует");
            personalLicenseRepos.save(personalLicense);
            return personalLicenseMapper.mapToDto(personalLicense);
        }
        return null;
    }

    @PutMapping(path = "/changeConditionToOff", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonalLicenseDto changeConditionToOff(@RequestParam Integer id){
        Optional<PersonalLicense> byId = personalLicenseRepos.findById(id);
        if (byId.isPresent()){
            PersonalLicense personalLicense = byId.get();
            personalLicense.setCondition("Истекла");
            personalLicenseRepos.save(personalLicense);
            return personalLicenseMapper.mapToDto(personalLicense);
        }
        return null;
    }
}
