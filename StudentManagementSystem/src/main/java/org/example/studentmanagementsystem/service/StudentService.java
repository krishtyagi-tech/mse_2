
package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.entity.Student;
import org.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor Injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 1. Add Student + Validation
    public Student addStudent(Student student) {

        if (studentRepository.existsById(student.getStudentId())) {
            throw new RuntimeException("Student ID already exists!");
        }

        if (student.getProgrammingScore() < 0 ||
                student.getProgrammingScore() > 100) {
            throw new RuntimeException("Invalid Programming Score!");
        }

        if (student.getMathematicsScore() < 0 ||
                student.getMathematicsScore() > 100) {
            throw new RuntimeException("Invalid Mathematics Score!");
        }

        return studentRepository.save(student);
    }

    // 2. Retrieve All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 3. Search Student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found!"));
    }

    // 4. Search Student by Email
    public List<Student> searchByEmail(String email) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    // 5. Search Student by Course
    public List<Student> searchByCourse(String course) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getCourseName().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    // 6. Calculate Average
    public double calculateAverage(Student student) {
        return (student.getProgrammingScore()
                + student.getMathematicsScore()) / 2.0;
    }

    // 7. Performance Category
    public String getPerformanceCategory(Student student) {

        double avg = calculateAverage(student);

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    // 8. Identify Stronger Subject
    public String getStrongerSubject(Student student) {

        if (student.getProgrammingScore() >
                student.getMathematicsScore()) {
            return "Programming";
        }
        else if (student.getMathematicsScore() >
                student.getProgrammingScore()) {
            return "Mathematics";
        }
        else {
            return "Both are Equal";
        }
    }

    // 9. Generate Merit List
    public List<Student> getMeritList() {

        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(
                        this::calculateAverage).reversed())
                .collect(Collectors.toList());
    }

    // 10. Find Students Having Same Technical Skills
    public List<List<Student>> getStudentsWithSameSkills() {

        Map<List<String>, List<Student>> groups =
                studentRepository.findAll()
                        .stream()
                        .collect(Collectors.groupingBy(
                                s -> s.getTechnicalSkills()
                                        .stream()
                                        .sorted()
                                        .collect(Collectors.toList())
                        ));

        return groups.values()
                .stream()
                .filter(group -> group.size() > 1)
                .collect(Collectors.toList());
    }

    // 11. Programming Needs Improvement + Mathematics >= 75
    public List<Student> getStudentsNeedingImprovement() {

        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getProgrammingScore() < 50
                        && s.getMathematicsScore() >= 75)
                .collect(Collectors.toList());
    }

    // 12. Update Student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student student = getStudentById(id);

        if (updatedStudent.getProgrammingScore() < 0 ||
                updatedStudent.getProgrammingScore() > 100 ||
                updatedStudent.getMathematicsScore() < 0 ||
                updatedStudent.getMathematicsScore() > 100) {
            throw new RuntimeException("Invalid scores!");
        }

        student.setProgrammingScore(
                updatedStudent.getProgrammingScore());

        student.setMathematicsScore(
                updatedStudent.getMathematicsScore());

        student.setTechnicalSkills(
                updatedStudent.getTechnicalSkills());

        return studentRepository.save(student);
    }

    // 13. Delete Student
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found!");
        }

        studentRepository.deleteById(id);
    }
}
