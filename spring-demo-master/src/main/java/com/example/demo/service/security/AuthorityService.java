package com.example.demo.service.security;


import com.example.demo.model.security.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorityService {
    public Authority save(Authority authority);
    public Page<Authority> getAuthoritiesPage(Pageable pageable);
    public Authority remove(Long authorityId);
    public Authority update(Authority authority);
}
