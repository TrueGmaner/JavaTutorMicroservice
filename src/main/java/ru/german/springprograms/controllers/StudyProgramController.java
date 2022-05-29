package ru.german.springprograms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.german.springprograms.dto.StudyProgramDto;
import ru.german.springprograms.entities.StudyProgram;
import ru.german.springprograms.mapper.StudyProgramMapper;
import ru.german.springprograms.repos.StudyProgramRepos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/programs")
public class StudyProgramController {
    @Autowired
    private StudyProgramRepos studyProgramRepos;
    @Autowired
    private StudyProgramMapper studyProgramMapper;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudyProgramDto> getAllPrograms (){
        return studyProgramRepos.findAll().stream()
                .map(studyProgramMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyProgramDto getById(@PathVariable("id") Integer id){
        Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findById(id);
        return studyProgramOptional.map(studyProgramMapper::mapToDto).orElse(null);
    }

    @GetMapping(path = "/findByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyProgramDto getByName(@PathVariable("name") String name){
        Optional<StudyProgram> studyProgramOptional = studyProgramRepos.findByName(name);
        return studyProgramOptional.map(studyProgramMapper::mapToDto).orElse(null);
    }

//    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public StudyProgramDto add (@RequestBody StudyProgramDto studyProgramDto){
//        StudyProgram studyProgram = new StudyProgram();
//        studyProgram.updateFromDto(studyProgramDto);
//        studyProgramRepos.save(studyProgram);
//        return studyProgramMapper.mapToDto(studyProgram);
//    }

    @PostMapping(path = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudyProgramDto add(@RequestParam("file") MultipartFile multipartFile) {
        if (!multipartFile.getOriginalFilename().endsWith(".zip")) throw new RuntimeException("Not zip type!");
        File file = new File("D:/Tutor/"+multipartFile.getOriginalFilename());
        try {
            boolean newFile = file.createNewFile();
            if (newFile){
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(multipartFile.getBytes());

                }
            }
            else throw new RuntimeException("file already exists");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setName(multipartFile.getOriginalFilename());
        studyProgramRepos.save(studyProgram);
        return studyProgramMapper.mapToDto(studyProgram);
    }

    @DeleteMapping(path = "/delete")
    public long delete (@RequestParam Integer id){
        Long recordsBeforeDelete = studyProgramRepos.count();
        if (studyProgramRepos.findById(id).isPresent())studyProgramRepos.deleteById(id);
        Long recordsAfterDelete = studyProgramRepos.count();
        return recordsBeforeDelete-recordsAfterDelete;
    }

    @PutMapping(path = "/changePrice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyProgramDto changePrice(@RequestBody StudyProgramDto studyProgramDto){
        String name = studyProgramDto.getName();
        Optional<StudyProgram> byName = studyProgramRepos.findByName(name);
        if (byName.isPresent()) {
            StudyProgram studyProgram1 = byName.get();
            studyProgram1.setPrice(studyProgramDto.getPrice());
            studyProgramRepos.save(studyProgram1);
            return studyProgramMapper.mapToDto(studyProgram1);
        }
        return null;
    }

}
