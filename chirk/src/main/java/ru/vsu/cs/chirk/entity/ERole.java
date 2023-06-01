package ru.vsu.cs.chirk.entity;


import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.vsu.cs.chirk.entity.RoleAuthority.*;

@RequiredArgsConstructor
@Getter
public enum ERole {
    ORDINARY(ImmutableSet.of(UPLOAD_AUTHORITY, REACT_AUTHORITY)),
    MODERATOR(new ImmutableSet.Builder<RoleAuthority>()
            .addAll(ORDINARY.authorities)
            .add(BAN_PUBLICATION_AUTHORITY)
            .build()),
    ADMIRATOR(new ImmutableSet.Builder<RoleAuthority>()
            .addAll(MODERATOR.authorities)
            .add(ADD_NEW_MODER_AUTHORITY)
            .build());


    private final Set<RoleAuthority> authorities;

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }

}
