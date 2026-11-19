
package org.example.studentmanagementsystem.controller;

import org.example.studentmanagementsystem.entity.Student;
import org.example.studentmanagementsystem.service.StudentService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor Injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Add Student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // 2. Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 3. Search Student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // 4. Update Student Scores and Skills
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return studentService.updateStudent(id, student);
    }

    // 5. Delete Student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully!";
    }

    // 6. Display Merit List
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return studentService.getMeritList();
    }

    // 7. Search by Email
    @GetMapping("/search/email")
    public List<Student> searchByEmail(@RequestParam String email) {
        return studentService.searchByEmail(email);
    }

    // 8. Search by Course
    @GetMapping("/search/course")
    public List<Student> searchByCourse(@RequestParam String course) {
        return studentService.searchByCourse(course);
    }

    // 9. Students with Same Skills
    @GetMapping("/same-skills")
    public List<List<Student>> getSameSkills() {
        return studentService.getStudentsWithSameSkills();
    }

    // 10. Students Needing Improvement
    @GetMapping("/needs-improvement")
    public List<Student> getNeedsImprovement() {
        return studentService.getStudentsNeedingImprovement();
    }
}
