package anda.selectlesson.service.user;

import anda.selectlesson.convert.Req2PO;
import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.enums.TeacherIdentity;
import anda.selectlesson.model.dto.StudentIdsDTO;
import anda.selectlesson.model.dto.UserDTO;
import anda.selectlesson.model.po.Student;
import anda.selectlesson.model.po.Teacher;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.StudentRepo;
import anda.selectlesson.repo.TeacherRepo;
import anda.selectlesson.repo.UserRepo;
import anda.selectlesson.req.LoginReq;
import anda.selectlesson.req.RegisterReq;
import anda.selectlesson.req.RegisterStudentReq;
import anda.selectlesson.utils.JwtTokenUtils;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    public void registerUser(RegisterReq req) {
        confirmRegisterReq(req);
        User user = Req2PO.UserReq2PO(req);
        userRepo.save(user);
    }

    public UserDTO loginUser(LoginReq req) {
        confirmLoginReq(req);
        User user= userRepo.getUserByUsernameAndPasswordAndAuthority(req.getUsername(),
                req.getPassword(),
                req.getAuthority());
        if (null == user) {
            throw new RuntimeException("没有该用户，请去注册");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setAuthority(user.getAuthority());
        userDTO.setUsername(user.getUsername());
        userDTO.setToken(JwtTokenUtils.getToken(user.getId().toString(), user.getPassword()));
        return userDTO;
    }

    public Long registerStudent(RegisterStudentReq req) throws IOException {
        Long id;
        id = JwtTokenUtils.getCurrentUserId();
        User user = userRepo.getUserById(id);
        Gson gson = new Gson();
        if (user.getAuthority().equals(AuthorityType.STUDENT)) {
            Student exitStudent = studentRepo.getByUserId(id);
            if (exitStudent != null) {
                throw new RuntimeException("你已经是学生了，无需注册");
            }
            if (null == req.getTeacherId()) {
                throw new RuntimeException("请选择班主任");
            }
            if (!TeacherIdentity.CLASS_TEACHER.equals(teacherRepo.getReferenceById(req.getTeacherId()).getIdentity())) {
                throw new RuntimeException("该老师不是班主任");
            }
            Teacher teacher = teacherRepo.getReferenceById(req.getTeacherId());
            if (null == teacher) {
                throw new RuntimeException("该老师不存在");
            }
            Type type = new TypeToken<List<StudentIdsDTO>>() {
            }.getType();
            List<StudentIdsDTO> list = gson.fromJson(teacher.getStudentIds(), type);
            boolean success = false;
            for (StudentIdsDTO studentIdsDTO : list) {
                if(studentIdsDTO.getExit()) {
                    continue;
                }
                success = true;
                Student student = studentRepo.getReferenceById(studentIdsDTO.getStudentId());
                student.setUserId(id);
                studentIdsDTO.setExit(true);
                studentRepo.save(student);
                break;
            }
            if (!success) {
                throw new RuntimeException("该老师学生已满，请联系该老师进行扩招");
            }
            teacher.setStudentIds(gson.toJson(list));
            teacherRepo.save(teacher);
        }
        if (user.getAuthority().equals(AuthorityType.TEACHER)) {
            Teacher teacher = teacherRepo.getByUserId(id);
            if (!teacher.getIdentity().equals(TeacherIdentity.CLASS_TEACHER)) {
                throw new RuntimeException("你不是班主任，无权注册学生");
            }
            if (!teacher.getUsedTime().isBlank() || teacher.getUsedTime().equals("true")) {
                throw new RuntimeException("你已经新建一批学生，如有遗漏请联系管理员");
            }
            Long classId = teacher.getClassId();
            List<Student> studentList = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formatterDate = now.format(formatter);
            long baseId = Long.parseLong(formatterDate);
            for (int i = 0; i < 30; i++) {
                Student student = new Student();
                student.setClassId(classId);
                student.setClassTeacherId(teacher.getId());
                studentList.add(student);
            }
            studentRepo.saveAll(studentList);
            List<Student> teacherSStudents = studentRepo.getStudentsByClassId(classId);
            List<StudentIdsDTO> ids = new ArrayList<>();
            for (Student teacherSStudent : teacherSStudents) {
                StudentIdsDTO studentIdsDTO = new StudentIdsDTO();
                studentIdsDTO.setStudentId(teacherSStudent.getId());
                studentIdsDTO.setExit(false);
                ids.add(studentIdsDTO);
            }
            String json = gson.toJson(ids);
            teacher.setUsedTime("true");
            teacher.setStudentIds(json);
            teacherRepo.save(teacher);
        }
        return id;
    }

    private void confirmRegisterReq(RegisterReq req) {
        if (null == req.getUsername() || null == req.getPassword() || null == req.getConfirmPassword()) {
            throw new RuntimeException("请求不能为null");
        } else if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("两次密码不相等");
        }
        User user = userRepo.getUserByUsername(req.getUsername());
        if (user != null) {
            throw new RuntimeException("该用户名已被注册");
        }
    }

    private void confirmLoginReq(LoginReq req) {
        if (null == req.getUsername() || null == req.getPassword() || null == req.getAuthority()) {
            throw new RuntimeException("请求不能为null");
        }
    }
}
