package anda.selectlesson.repo;

import anda.selectlesson.model.po.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonsRepo extends JpaRepository<Lesson, Long> {
    Lesson getByTeacherIdAndLessonName(Long teacherId, String lessonName);
    List<Lesson> getLessonsByIdIsIn(List<Long> ids);
}
