package com.hexagon.schoolservice.service;
import com.hexagon.schoolservice.entity.School;
import com.hexagon.schoolservice.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    public School addSchool(School school){
        return schoolRepository.save(school);
    }

    public List<School> fetchSchools(){
        return schoolRepository.findAll();
    }

    public School fetchSchoolById(int id){
        return schoolRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> updateSchool(int id, School updatedSchool) {
        try {
            Optional<School> schoolOptional = schoolRepository.findById(id);
            if (schoolOptional.isPresent()) {
                School school = schoolOptional.get();
                school.setSchoolName(updatedSchool.getSchoolName());
                school.setLocation(updatedSchool.getLocation());
                school.setPrincipalName(updatedSchool.getPrincipalName());
                
                School savedSchool = schoolRepository.save(school);
                return new ResponseEntity<>(savedSchool, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("School not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteSchool(int id) {
        try {
            Optional<School> school = schoolRepository.findById(id);
            if (school.isPresent()) {
                schoolRepository.deleteById(id);
                return new ResponseEntity<>("School successfully deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("School not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}