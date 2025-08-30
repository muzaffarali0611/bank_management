package com.bankmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    
    @NotBlank(message = "Permission name is required")
    @Size(max = 100, message = "Permission name cannot exceed 100 characters")
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", nullable = false)
    private PermissionType permissionType;
    
    @Column(name = "resource")
    private String resource;
    
    @Column(name = "action")
    private String action;
    
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
    
    // Enums
    public enum PermissionType { 
        SYSTEM, USER_MANAGEMENT, ACCOUNT_MANAGEMENT, TRANSACTION_MANAGEMENT, 
        REPORT_VIEW, AUDIT_VIEW, CONFIGURATION, KYC_MANAGEMENT 
    }
    
    // Constructors
    public Permission() {}
    
    public Permission(String name, String description, PermissionType permissionType, String resource, String action) {
        this.name = name;
        this.description = description;
        this.permissionType = permissionType;
        this.resource = resource;
        this.action = action;
    }
    
    // Business methods
    public String getFullPermission() {
        return resource + ":" + action;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public PermissionType getPermissionType() { return permissionType; }
    public void setPermissionType(PermissionType permissionType) { this.permissionType = permissionType; }
    
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
