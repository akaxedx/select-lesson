package anda.selectlesson.service.manager;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.enums.TeacherIdentity;
import anda.selectlesson.model.dto.PageUserDTO;
import anda.selectlesson.model.dto.PageUserTeacherDTO;
import anda.selectlesson.model.po.Room;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.*;
import anda.selectlesson.req.BaseReq;
import anda.selectlesson.req.managerReq.BuildBuildingReq;
import anda.selectlesson.req.managerReq.EditTeacherReq;
import anda.selectlesson.req.studentReq.GetAllTeachersReq;
import anda.selectlesson.service.user.UserService;
import anda.selectlesson.system.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ManagerService {
    @Autowired
    public UserRepo userRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    UserPageRepo userPageRepo;

    public Response<Boolean> buildBuilding(BuildBuildingReq req) {
        if (req.getBuilding() == null) {
            return Response.error("请为楼栋取名字");
        }
        List<Room> exitRoom = roomRepo.getRoomsByBuilding(req.getBuilding());
        if (!exitRoom.isEmpty()) {
            return Response.error("该楼已建");
        }
        if (req.getNum() < 5) {
            return Response.error("每层教室太少，至少5间");
        }
        if (req.getNum() > 10) {
            return Response.error("每层教室太多，最多10间");
        }
        if (req.getFloor() < 3) {
            return Response.error("楼层太低，至少3楼");
        }
        if (req.getFloor() > 7) {
            return Response.error("楼层太高，最多7楼");
        }

        String roomId = UUID.randomUUID().toString();
        for (Integer i = 1; i <= req.getFloor(); i++) {
            for (Integer j = 1; j <= req.getNum(); j++) {
                Room room = new Room();
                room.setRoomId(roomId);
                room.setFloor(i);
                room.setNum(j);
                room.setBuilding(req.getBuilding());

                roomRepo.save(room);
            }
        }
        return Response.ok(true);
    }
    public Response<Teacher> editTeacher(EditTeacherReq req) {
        if (req.getUserId() == null) {
            return Response.error("userId 丢失");
        }
        User user = userRepo.getUserById(req.getUserId());
        if (user == null) {
            return Response.error("该用户不存在");
        }
        Teacher exitTeacher = teacherRepo.getByUserId(req.getUserId());
        if (exitTeacher != null) {
            if (req.getIdentity() != null) {
               if (TeacherIdentity.COMMON_TEACHER.equals(exitTeacher.getIdentity())) {
                   exitTeacher.setIdentity(TeacherIdentity.CLASS_TEACHER);
                   teacherRepo.save(exitTeacher);
                   return Response.ok("已升级");
               }
               if (TeacherIdentity.CLASS_TEACHER.equals(exitTeacher.getIdentity())) {
                   return Response.error("已是最高级");
               }
               exitTeacher.setIdentity(TeacherIdentity.COMMON_TEACHER);
               teacherRepo.save(exitTeacher);
               return Response.error("不知道");
            } else {
                return Response.error("请指定教师等级");
            }
        } else {
            Teacher teacher = new Teacher();
            teacher.setUserId(req.getUserId());
            teacher.setIdentity(TeacherIdentity.COMMON_TEACHER);
            user.setAuthority(AuthorityType.TEACHER);
            userRepo.save(user);
            teacher.setStudentIds("[]");
            teacher.setLessonIds("[]");
            return Response.ok(teacherRepo.save(teacher));
        }
    }

    public Response<PageUserDTO> getAllUsers (GetAllTeachersReq req) {
        Pageable pageable = PageRequest.of(req.getPageNum()-1, 10);
        Page<User> all = userPageRepo.findAll(pageable);
        List<Long> userIds = all.getContent().stream().map(User::getId).toList();
        List<Teacher> teachers = teacherRepo.getByUserIdIn(userIds);
        Map<Long, Teacher> teachersMap = teachers.stream().collect(Collectors.toMap(Teacher::getUserId, Function.identity()));
        PageUserDTO pageUserDTO = new PageUserDTO();
        List<PageUserTeacherDTO> users = new ArrayList<>();
        for (User user : all.getContent()) {
            PageUserTeacherDTO pageUserTeacherDTO = new PageUserTeacherDTO();
            if (teachersMap.get(user.getId()) != null) {
                pageUserTeacherDTO.setTeacherIdentity(teachersMap.get(user.getId()).getIdentity());
            }
            pageUserTeacherDTO.setUser(user);
            users.add(pageUserTeacherDTO);
        }
        pageUserDTO.setUsers(users);
        pageUserDTO.setPageTotal(all.getTotalPages());
        return Response.ok(pageUserDTO);
    }
}
