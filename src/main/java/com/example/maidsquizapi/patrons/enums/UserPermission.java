package com.example.maidsquizapi.patrons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPermission {

    PATRON_READ("patron:read"),
    PATRON_WRITE("patron:write"),
    MANAGER_READ("management:read"),
    MANAGER_WRITE("management:write");


    private final String permission;
}
