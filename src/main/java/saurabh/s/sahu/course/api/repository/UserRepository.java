package saurabh.s.sahu.course.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saurabh.s.sahu.course.api.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // have an entry of user with username:user and password:pass
    Optional<User> findByUserName(String userName);
}