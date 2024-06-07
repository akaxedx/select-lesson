package anda.selectlesson.service.lesson;

import anda.selectlesson.model.dto.usedTimeDTO;
import anda.selectlesson.model.po.Lesson;
import anda.selectlesson.repo.LessonsRepo;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class TimeService {
    @Autowired
    LessonsRepo lessonsRepo;
    public Long resolveConflict(List<Long> ids, Lesson lesson) {
        List<Lesson> exitLessons = lessonsRepo.getLessonsByIdIsIn(ids);
        usedTimeDTO currentLessonTime = JSONUtil.toBean(lesson.getTime(), usedTimeDTO.class);
        for (Lesson exitLesson : exitLessons) {
            Date startDate = exitLesson.getStartDate();
            Date endDate = exitLesson.getEndDate();
            if (endDate.before(lesson.getStartDate())) {
                continue;
            }
            if (startDate.after(lesson.getEndDate())) {
                continue;
            }
            usedTimeDTO exitLessonTime = JSONUtil.toBean(exitLesson.getTime(), usedTimeDTO.class);
            // 周一
            for (Integer mondayTime : currentLessonTime.getMonday()) {
                for (Integer exitMondayTime : exitLessonTime.getMonday()) {
                    if(Objects.equals(mondayTime, exitMondayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周二
            for (Integer tuesdayTime : currentLessonTime.getTuesday()) {
                for (Integer exitTuesdayTime : exitLessonTime.getTuesday()) {
                    if(Objects.equals(tuesdayTime, exitTuesdayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周三
            for (Integer wednesdayTime : currentLessonTime.getWednesday()) {
                for (Integer exitWednesdayTime : exitLessonTime.getWednesday()) {
                    if(Objects.equals(wednesdayTime, exitWednesdayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周四
            for (Integer thursdayTime : currentLessonTime.getThursday()) {
                for (Integer exitThursdayTime : exitLessonTime.getThursday()) {
                    if(Objects.equals(thursdayTime, exitThursdayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周五
            for (Integer fridayTime : currentLessonTime.getFriday()) {
                for (Integer exitFridayTime : exitLessonTime.getFriday()) {
                    if(Objects.equals(fridayTime, exitFridayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周六
            for (Integer saturdayTime : currentLessonTime.getSaturday()) {
                for (Integer exitSaturdayTime : exitLessonTime.getSaturday()) {
                    if(Objects.equals(saturdayTime, exitSaturdayTime)) {
                        return exitLesson.getId();
                    }
                }
            }
            // 周日
            for (Integer sundayTime : currentLessonTime.getSunday()) {
                for (Integer exitSundayTime : exitLessonTime.getSunday()) {
                    if(Objects.equals(sundayTime, exitSundayTime)) {
                        return exitLesson.getId();
                    }
                }
            }

        }
        return -1L;
    }
}
