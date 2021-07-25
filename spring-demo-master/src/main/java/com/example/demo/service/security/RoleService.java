package com.example.demo.service.security;

import com.example.demo.model.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    public Role save(Role role);
    public Page<Role> getRolesPage(Pageable pageable);
    public Role remove(Long roleId);
    public Role update(Role role);
    public Role getUserRole();
}
