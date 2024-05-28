package anda.selectlesson.repo;

import anda.selectlesson.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByUsernameAndPasswordAndAuthority(String username, String password, String authority);
}
