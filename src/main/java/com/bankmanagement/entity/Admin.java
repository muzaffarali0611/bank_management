package com.bankmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    
    @Column(name = "admin_id", unique = true, nullable = false)
    private String adminId;
    
    @Column(name = "admin_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminLevel adminLevel;
    
    @Column(name = "super_admin", nullable = false)
    private Boolean superAdmin = false;
    
    @Column(name = "last_system_access")
    private LocalDateTime lastSystemAccess;
    
    @Column(name = "ip_whitelist")
    private String ipWhitelist;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "admin_permissions",
        joinColumns = @JoinColumn(name = "admin_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
    
    // Enums
    public enum AdminLevel { 
        SYSTEM_ADMIN, BANK_ADMIN, BRANCH_ADMIN, OPERATIONS_ADMIN 
    }
    
    // Constructors
    public Admin() {}
    
    public Admin(String username, String passwordHash, String firstName, String lastName, String email) {
        super(username, passwordHash, firstName, lastName, email);
        this.adminId = generateAdminId();
    }
    
    // Business methods
    private String generateAdminId() {
        return "ADM" + System.currentTimeMillis() % 1000000;
    }
    
    public boolean hasPermission(String permissionName) {
        return permissions.stream().anyMatch(permission -> permission.getName().equals(permissionName));
    }
    
    public boolean canManageUsers() {
        return hasPermission("USER_MANAGEMENT") || superAdmin;
    }
    
    public boolean canManageSystem() {
        return hasPermission("SYSTEM_MANAGEMENT") || superAdmin;
    }
    
    public boolean canViewAuditLogs() {
        return hasPermission("AUDIT_VIEW") || superAdmin;
    }
    
    // Getters and Setters
    public String getAdminId() { 
        return adminId; 
    }
    
    public void setAdminId(String adminId) { 
        this.adminId = adminId; 
    }
    
    public AdminLevel getAdminLevel() { 
        return adminLevel; 
    }
    
    public void setAdminLevel(AdminLevel adminLevel) { 
        this.adminLevel = adminLevel; 
    }
    
    public Boolean getSuperAdmin() { 
        return superAdmin; 
    }
    
    public void setSuperAdmin(Boolean superAdmin) { 
        this.superAdmin = superAdmin; 
    }
    
    public LocalDateTime getLastSystemAccess() { 
        return lastSystemAccess; 
    }
    
    public void setLastSystemAccess(LocalDateTime lastSystemAccess) { 
        this.lastSystemAccess = lastSystemAccess; 
    }
    
    public String getIpWhitelist() { 
        return ipWhitelist; 
    }
    
    public void setIpWhitelist(String ipWhitelist) { 
        this.ipWhitelist = ipWhitelist; 
    }
    
    public Set<Permission> getPermissions() { 
        return permissions; 
    }
    
    public void setPermissions(Set<Permission> permissions) { 
        this.permissions = permissions; 
    }
}
