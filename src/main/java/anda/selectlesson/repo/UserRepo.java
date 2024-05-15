package anda.selectlesson.repo;

import anda.selectlesson.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User getUserByUsername(String username);
    public User getUserByUsernameAndPasswordAndAuthority(String username, String password, String authority);
}
