package anda.selectlesson.repo;

import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Teacher getByUserId(Long userId);
    List<Teacher> getByUserIdIn(List<Long> ids);
}
