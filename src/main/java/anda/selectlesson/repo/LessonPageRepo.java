package anda.selectlesson.repo;

import anda.selectlesson.model.po.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LessonPageRepo extends PagingAndSortingRepository<Lesson, Long> {
    Page<Lesson> getLessonsByLessonNameLike(String lessonName, Pageable pageable);
}
