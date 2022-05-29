package ru.german.springprograms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.german.springprograms.dto.BusinessLicenseDto;
import ru.german.springprograms.dto.InstituteAccessDto;
import ru.german.springprograms.entities.InstituteAccess;
import ru.german.springprograms.mapper.InstituteAccessMapper;
import ru.german.springprograms.repos.InstituteAccessRepos;
import ru.german.springprograms.repos.StudyProgramRepos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/institutePrograms")
public class InstituteAccessController {
    private Map<String, String> idToPathMap= new HashMap<String, String>();
    @Autowired
    private InstituteAccessRepos instituteAccessRepos;
    @Autowired
    private StudyProgramRepos studyProgramRepos;
    @Autowired
    private InstituteAccessMapper instituteAccessMapper;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstituteAccessDto> getAllListAccess(){
        return instituteAccessRepos.findAll().stream()
                .map(instituteAccessMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstituteAccessDto getById(@PathVariable("id") Integer id){
        Optional<InstituteAccess> instituteAccessOptional = instituteAccessRepos.findById(id);
        return instituteAccessOptional.map(instituteAccessMapper::mapToDto).orElse(null);
    }

    @GetMapping(path = "/findInstituteAccess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findInstituteAccess(@RequestParam String programName){
        List<InstituteAccessDto> instituteAccessDtoList = getAllListAccess();

        for (InstituteAccessDto instituteAccessDto:instituteAccessDtoList) {
            if ((instituteAccessDto.getProgramName().equals(programName))) {
                String path= "D:/Tutor/"+programName;
                String id = UUID.randomUUID().toString();
                idToPathMap.put(id, path);
                return "{\"id\":\""+id+"\"}";
            }
        }
        throw new RuntimeException();
    }

    @GetMapping(path = "/getFile/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile (@PathVariable String id) throws IOException {
        String s = idToPathMap.get(id);
        File file = new File(s);
        if (file.exists()) return Files.readAllBytes(file.toPath());
        throw new RuntimeException("File not found");
    }

//    @GetMapping(path = "/findByName/{programName}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public InstituteAccessDto getByName(@PathVariable("programName") String programName){
//        Optional<InstituteAccess> instituteAccessOptional = instituteAccessRepos.findByProgramName(programName);
//        return instituteAccessOptional.map(instituteAccessMapper::mapToDto).orElse(null);
//    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InstituteAccessDto add (@RequestBody InstituteAccessDto instituteAccessDto){
        if (studyProgramRepos.findByName(instituteAccessDto.getProgramName()).isPresent()){
            InstituteAccess instituteAccess = new InstituteAccess();
            instituteAccess.updateFromDto(instituteAccessDto);
            instituteAccessRepos.save(instituteAccess);
            return instituteAccessMapper.mapToDto(instituteAccess);
        }
        return null;
    }

    @DeleteMapping(path = "/delete")
    public long delete (@RequestParam Integer id){
        Long recordsBeforeDelete = instituteAccessRepos.count();
        if (instituteAccessRepos.findById(id).isPresent())instituteAccessRepos.deleteById(id);
        Long recordsAfterDelete = instituteAccessRepos.count();
        return recordsBeforeDelete-recordsAfterDelete;
    }
}
