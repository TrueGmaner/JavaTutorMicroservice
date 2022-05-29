package ru.german.springprograms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.german.springprograms.dto.GroupProgramDto;
import ru.german.springprograms.entities.GroupProgram;
import ru.german.springprograms.entities.StudyProgram;
import ru.german.springprograms.mapper.GroupProgramMapper;
import ru.german.springprograms.mapper.StudyProgramMapper;
import ru.german.springprograms.repos.GroupProgramRepos;
import ru.german.springprograms.repos.StudyProgramRepos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groupPrograms")
public class GroupProgramController {
    private Map<String, String> idToPathMap= new HashMap<String, String>();
    @Autowired
    GroupProgramRepos groupProgramRepos;
    @Autowired
    GroupProgramMapper groupProgramMapper;
    @Autowired
    StudyProgramRepos studyProgramRepos;
    @Autowired
    private StudyProgramMapper studyProgramMapper;

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupProgramDto> getAllGroupPrograms(){
        return groupProgramRepos.findAll().stream()
                .map(groupProgramMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/findGroupProgram", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findGroupProgram(@RequestParam Integer idProgram, @RequestParam Integer idGroup){
        List<GroupProgramDto> groupProgramDtoList = getAllGroupPrograms();

        for (GroupProgramDto groupProgramDto:groupProgramDtoList) {
            if ((groupProgramDto.getIdProgram()==idProgram) && (groupProgramDto.getIdGroup()==idGroup)) {
                Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findById(idProgram);
                String programName = studyProgramOptional.map(studyProgramMapper::mapToDto).orElse(null).getName();
                String path= "D:/Tutor/"+programName;
                String id = UUID.randomUUID().toString();
                idToPathMap.put(id, path);
                return "{\"id\":\""+id+"\"}";
            }
        }
        return null;
    }

    @GetMapping(path = "/getFile/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile (@PathVariable String id) throws IOException {
        String s = idToPathMap.get(id);
        File file = new File(s);
        if (file.exists()) return Files.readAllBytes(file.toPath());
        throw new RuntimeException("File not found");
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupProgramDto add(@RequestParam Integer idProgram, @RequestParam Integer idGroup){
        Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findById(idProgram);
        if (studyProgramOptional.isPresent()) {
            GroupProgram groupProgram = new GroupProgram(studyProgramOptional.get(), idGroup);
            groupProgramRepos.save(groupProgram);
            return groupProgramMapper.mapToDto(groupProgram);
        }
        return null;
    }

    @DeleteMapping(path= "/delete")
    public long delete (@RequestParam Integer idProgram, @RequestParam Integer idGroup){
        Long recordsBeforeDelete = groupProgramRepos.count();
        List<GroupProgramDto> groupProgramDtoList = getAllGroupPrograms();

        for (GroupProgramDto groupProgramDto:groupProgramDtoList) {
            if ((groupProgramDto.getIdProgram()==idProgram) && (groupProgramDto.getIdGroup()==idGroup)){
                Integer id = groupProgramDto.getId();
                groupProgramRepos.deleteById(id);
                break;
            }
        }
        Long recordsAfterDelete = groupProgramRepos.count();
        return recordsBeforeDelete-recordsAfterDelete;
    }

}
