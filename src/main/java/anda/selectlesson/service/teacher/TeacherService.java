package anda.selectlesson.service.teacher;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.dto.usedTimeDTO;
import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.model.po.Room;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.*;
import anda.selectlesson.req.teacherReq.SetLessonReq;
import anda.selectlesson.service.lesson.TimeService;
import anda.selectlesson.system.Response;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TeacherService {
    @Autowired
    LessonsRepo lessonsRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TimeService timeService;
    @Autowired
    RoomRepo roomRepo;
    private final static int MAX_NUM = 50;

    public Response<Long> setLesson(SetLessonReq req) throws IOException {
        Long currentUserId = JwtTokenUtils.getCurrentUserId();
        User currentUser = userRepo.getUserById(currentUserId);
        if (!currentUser.getAuthority().equals(AuthorityType.TEACHER)) {
            return Response.error("当前用户不是老师");
        }
        Teacher currentTeacher = teacherRepo.getByUserId(currentUserId);
        List<Long> lessonIds = JSONUtil.toList(currentTeacher.getLessonIds(), Long.class);
        Lesson exitLesson = lessonsRepo.getByTeacherIdAndLessonName(currentTeacher.getId(), req.getLessonName());
        Room room = roomRepo.getReferenceById(req.getRoomId());
        if (null != exitLesson) {
            return Response.error("该课程已被创建");
        }
        if (room.getUsed()) {
            return Response.error("教室已被占用");
        }
        Lesson lesson = new Lesson();
        lesson.setTeacherId(currentTeacher.getId());
        lesson.setRoomId(req.getRoomId());
        room.setUsed(true);
        roomRepo.save(room);
        // time
        usedTimeDTO usedTimeDTO = getLessonTimeDTO(req);
        lesson.setTime(JSONUtil.toJsonStr(usedTimeDTO));

        // date
        lesson.setStartDate(req.getStartDate());
        lesson.setEndDate(req.getEndDate());

        // maxNum
        lesson.setMaxNum(MAX_NUM);

        // name
        lesson.setLessonName(req.getLessonName());

        // currentNum
        lesson.setCurrentNum(0);

        // studentIds
        lesson.setStudentIds("[]");

        // 课程冲突
        Long isConflict = timeService.resolveConflict(lessonIds, lesson);
        if(isConflict != -1L) {
            Lesson conflictLesson = lessonsRepo.getReferenceById(isConflict);
            return Response.error("课程冲突: " + conflictLesson.getLessonName());
        }

        Lesson savedLesson = lessonsRepo.save(lesson);

        lessonIds.add(savedLesson.getId());
        currentTeacher.setLessonIds(JSONUtil.toJsonStr(lessonIds));
        teacherRepo.save(currentTeacher);
        return Response.ok(savedLesson.getId());
    }

    private static usedTimeDTO getLessonTimeDTO(SetLessonReq req) {
        int lessonTime = 0;
        usedTimeDTO usedTimeDTO = new usedTimeDTO();
        // 设置课时
        usedTimeDTO.setMonday(req.getMonday());
        lessonTime += req.getMonday().size();
        usedTimeDTO.setTuesday(req.getTuesday());
        lessonTime += req.getTuesday().size();
        usedTimeDTO.setWednesday(req.getWednesday());
        lessonTime += req.getWednesday().size();
        usedTimeDTO.setThursday(req.getThursday());
        lessonTime += req.getThursday().size();
        usedTimeDTO.setFriday(req.getFriday());
        lessonTime += req.getFriday().size();
        usedTimeDTO.setSaturday(req.getSaturday());
        lessonTime += req.getSaturday().size();
        usedTimeDTO.setSunday(req.getSunday());
        lessonTime += req.getSunday().size();
        usedTimeDTO.setLessonTime(lessonTime);
        return usedTimeDTO;
    }


}
