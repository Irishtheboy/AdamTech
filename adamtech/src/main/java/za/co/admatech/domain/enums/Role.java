package za.co.admatech.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    CUSTOMER(Set.of(Permission.CUSTOMER_READ, Permission.CUSTOMER_UPDATE, Permission.CUSTOMER_DELETE)),
    ADMIN(Set.of(
        Permission.CUSTOMER_READ, Permission.CUSTOMER_UPDATE, Permission.CUSTOMER_DELETE, Permission.CUSTOMER_CREATE,
        Permission.PRODUCT_READ, Permission.PRODUCT_UPDATE, Permission.PRODUCT_DELETE, Permission.PRODUCT_CREATE,
        Permission.ORDER_READ, Permission.ORDER_UPDATE, Permission.ORDER_DELETE, Permission.ORDER_CREATE
    )),
    MANAGER(Set.of(
        Permission.CUSTOMER_READ, Permission.CUSTOMER_UPDATE,
        Permission.PRODUCT_READ, Permission.PRODUCT_UPDATE, Permission.PRODUCT_CREATE,
        Permission.ORDER_READ, Permission.ORDER_UPDATE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
