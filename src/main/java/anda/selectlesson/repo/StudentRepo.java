package anda.selectlesson.repo;

import anda.selectlesson.model.po.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Student getByUserId(Long userId);
    List<Student> getStudentsByClassId(Long classId);
    List<Student> getStudentsByIdIsIn(List<Long> ids);
}
