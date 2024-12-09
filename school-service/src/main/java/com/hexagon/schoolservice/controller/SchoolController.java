package com.hexagon.schoolservice.controller;
import com.hexagon.schoolservice.entity.School;
import com.hexagon.schoolservice.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(value = "/api/schools")
@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public School addSchool(@RequestBody School school){
        return schoolService.addSchool(school);
    }

    @GetMapping
    public List<School> fetchSchools(){
        return schoolService.fetchSchools();
    }

    @GetMapping("/{id}")
    public School fetchSchoolById(@PathVariable int id){
        return schoolService.fetchSchoolById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable int id, @RequestBody School school) {
        return schoolService.updateSchool(id, school);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable int id) {
        return schoolService.deleteSchool(id);
    }
}