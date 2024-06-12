package anda.selectlesson.repo;

import anda.selectlesson.model.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPageRepo extends PagingAndSortingRepository<User, Long> {
    Page<User> getUsersByAuthority(String authority, Pageable pageable);
}
