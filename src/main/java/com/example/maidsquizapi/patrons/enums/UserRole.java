package com.example.maidsquizapi.patrons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.maidsquizapi.patrons.enums.UserPermission.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ROLE_PATRON(
            Set.of(
                    PATRON_READ,
                    PATRON_WRITE
            )),

    ROLE_MANAGER(
            Set.of(
                    PATRON_READ,
                    PATRON_WRITE,
                    MANAGER_READ,
                    MANAGER_WRITE
            ));

    private final Set<UserPermission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority(this.name()));

        return authorities;
    }
}
