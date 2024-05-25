package ma.enset.springangular_students.repository;

import ma.enset.springangular_students.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByCode(String code);
    List<Student> findByProgrammeId(String programmeId);
}
