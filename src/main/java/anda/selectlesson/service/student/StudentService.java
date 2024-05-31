package anda.selectlesson.service.student;

import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.LessonsRepo;
import anda.selectlesson.repo.StudentRepo;
import anda.selectlesson.repo.UserRepo;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentService {
    @Autowired
    LessonsRepo lessonsRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    StudentRepo studentRepo;
    public Boolean selectLesson(Long userId, Long lessonId) {
        Student student = studentRepo.getByUserId(userId);
        //todo
        return null;
    }
}
