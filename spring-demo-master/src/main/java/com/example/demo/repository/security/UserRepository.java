package com.example.demo.repository.security;




import com.example.demo.model.security.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    Optional<User> findByEmail(String userEmail);
    boolean existsByEmail(String email);
    Optional<User> findByActivationCode(String code);

}
