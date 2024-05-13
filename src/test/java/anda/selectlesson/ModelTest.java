package anda.selectlesson;

import anda.selectlesson.enums.AuthorityType;
import anda.selectlesson.model.po.User;
import anda.selectlesson.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelTest {
    @Autowired
    UserRepo userRepo;
    @Test
    public void userModelTest(){
        User user = new User();
        user.setAuthority(AuthorityType.MANAGER);
        user.setUsername("wfj");
        user.setPassword("123456");
        user.setBadNum(0);
        userRepo.save(user);
    }
}
