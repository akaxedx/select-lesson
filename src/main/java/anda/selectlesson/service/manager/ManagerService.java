package anda.selectlesson.service.manager;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.enums.TeacherIdentity;
import anda.selectlesson.model.po.Room;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.RoomRepo;
import anda.selectlesson.repo.StudentRepo;
import anda.selectlesson.repo.TeacherRepo;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.managerReq.BuildBuildingReq;
import anda.selectlesson.req.managerReq.EditTeacherReq;
import anda.selectlesson.service.user.UserService;
import anda.selectlesson.system.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

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

    public Response<Boolean> buildBuilding(BuildBuildingReq req) {
        if (req.getBuilding() == null) {
            return Response.error("请为楼栋取名字");
        }
        Room exitRoom = roomRepo.getByBuilding(req.getBuilding());
        if (exitRoom != null) {
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
                if (TeacherIdentity.COMMON_TEACHER.equals(req.getIdentity())) {
                    exitTeacher.setIdentity(req.getIdentity());
                } else if (TeacherIdentity.CLASS_TEACHER.equals(req.getIdentity()) &&
                        exitTeacher.getIdentity().equals(TeacherIdentity.COMMON_TEACHER)) {
                    exitTeacher.setIdentity(req.getIdentity());
                } else {
                    return Response.error("老师身份只能升级不能降级");
                }
                return Response.ok(teacherRepo.save(exitTeacher));
            } else {
                return Response.error("该老师已存在");
            }
        } else {
            Teacher teacher = new Teacher();
            teacher.setUserId(req.getUserId());
            if (req.getIdentity().equals(TeacherIdentity.CLASS_TEACHER)){
                teacher.setIdentity(req.getIdentity());
            } else {
                teacher.setIdentity(TeacherIdentity.COMMON_TEACHER);
            }
            user.setAuthority(AuthorityType.TEACHER);
            userRepo.save(user);
            return Response.ok(teacherRepo.save(teacher));
        }
    }
}
