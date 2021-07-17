package com.example.demo.repository.security;


import com.example.demo.model.security.Authority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Long> {
    Optional<Authority> findByAuthorityName(String authorityName);
}
