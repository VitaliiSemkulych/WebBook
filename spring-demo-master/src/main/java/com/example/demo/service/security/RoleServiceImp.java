package com.example.demo.service.security;


import com.example.demo.enums.AuthorizationRolesType;
import com.example.demo.model.security.Role;
import com.example.demo.repository.security.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> getRolesPage(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public Role remove(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isPresent()) {
            roleRepository.delete(optionalRole.get());
            return optionalRole.get();
        }else{
            throw new IllegalArgumentException(String.format("Role with Id: %s  isn't exist.",roleId));
        }
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public Role update(Role role) {
        if(roleRepository.findById(role.getId()).isPresent()){
            return roleRepository.save(role);
        }else{
            throw new IllegalArgumentException(String.format("Role with Id: %s  isn't exist",
                    role.getId()));

        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = true)
    public Role getUserRole() {
        Optional<Role> optionalRole= roleRepository.findByRoleName("ROLE_USER");
        return  optionalRole.orElseGet(()->new Role("ROLE_"+ AuthorizationRolesType.USER.name()));
    }
}
