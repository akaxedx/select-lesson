package anda.selectlesson.repo;

import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Teacher getByUserId(Long userId);
}
