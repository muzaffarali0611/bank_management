package com.bankmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
@DiscriminatorValue("STAFF")
public class Staff extends User {
    
    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;
    
    @Column(name = "department", nullable = false)
    private String department;
    
    @Column(name = "designation", nullable = false)
    private String designation;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDateTime hireDate;
    
    @Column(name = "salary", precision = 19, scale = 2)
    private BigDecimal salary;
    
    @Column(name = "supervisor_id")
    private Long supervisorId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "staff_level", nullable = false)
    private StaffLevel staffLevel;
    
    @Column(name = "is_authorized_for_transactions")
    private Boolean isAuthorizedForTransactions = false;
    
    @Column(name = "is_authorized_for_account_management")
    private Boolean isAuthorizedForAccountManagement = false;
    
    @Column(name = "is_authorized_for_kyc")
    private Boolean isAuthorizedForKyc = false;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "staff_roles",
        joinColumns = @JoinColumn(name = "staff_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "processedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> processedTransactions = new HashSet<>();
    
    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> approvedAccounts = new HashSet<>();
    
    // Enums
    public enum StaffLevel { 
        JUNIOR, SENIOR, MANAGER, DIRECTOR, EXECUTIVE 
    }
    
    // Constructors
    public Staff() {}
    
    public Staff(String username, String passwordHash, String firstName, String lastName, String email) {
        super(username, passwordHash, firstName, lastName, email);
        this.employeeId = generateEmployeeId();
        this.hireDate = LocalDateTime.now();
    }
    
    // Business methods
    private String generateEmployeeId() {
        return "EMP" + System.currentTimeMillis() % 1000000;
    }
    
    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }
    
    public boolean canProcessTransactions() {
        return isAuthorizedForTransactions && getStatus() == UserStatus.ACTIVE;
    }
    
    public boolean canManageAccounts() {
        return isAuthorizedForAccountManagement && getStatus() == UserStatus.ACTIVE;
    }
    
    public boolean canVerifyKyc() {
        return isAuthorizedForKyc && getStatus() == UserStatus.ACTIVE;
    }
    
    // Getters and Setters
    public String getEmployeeId() { 
        return employeeId; 
    }
    
    public void setEmployeeId(String employeeId) { 
        this.employeeId = employeeId; 
    }
    
    public String getDepartment() { 
        return department; 
    }
    
    public void setDepartment(String department) { 
        this.department = department; 
    }
    
    public String getDesignation() { 
        return designation; 
    }
    
    public void setDesignation(String designation) { 
        this.designation = designation; 
    }
    
    public LocalDateTime getHireDate() { 
        return hireDate; 
    }
    
    public void setHireDate(LocalDateTime hireDate) { 
        this.hireDate = hireDate; 
    }
    
    public BigDecimal getSalary() { 
        return salary; 
    }
    
    public void setSalary(BigDecimal salary) { 
        this.salary = salary; 
    }
    
    public Long getSupervisorId() { 
        return supervisorId; 
    }
    
    public void setSupervisorId(Long supervisorId) { 
        this.supervisorId = supervisorId; 
    }
    
    public StaffLevel getStaffLevel() { 
        return staffLevel; 
    }
    
    public void setStaffLevel(StaffLevel staffLevel) { 
        this.staffLevel = staffLevel; 
    }
    
    public Boolean getIsAuthorizedForTransactions() { 
        return isAuthorizedForTransactions; 
    }
    
    public void setIsAuthorizedForTransactions(Boolean isAuthorizedForTransactions) { 
        this.isAuthorizedForTransactions = isAuthorizedForTransactions; 
    }
    
    public Boolean getIsAuthorizedForAccountManagement() { 
        return isAuthorizedForAccountManagement; 
    }
    
    public void setIsAuthorizedForAccountManagement(Boolean isAuthorizedForAccountManagement) { 
        this.isAuthorizedForAccountManagement = isAuthorizedForAccountManagement; 
    }
    
    public Boolean getIsAuthorizedForKyc() { 
        return isAuthorizedForKyc; 
    }
    
    public void setIsAuthorizedForKyc(Boolean isAuthorizedForKyc) { 
        this.isAuthorizedForKyc = isAuthorizedForKyc; 
    }
    
    public Set<Role> getRoles() { 
        return roles; 
    }
    
    public void setRoles(Set<Role> roles) { 
        this.roles = roles; 
    }
    
    public Set<Transaction> getProcessedTransactions() { 
        return processedTransactions; 
    }
    
    public void setProcessedTransactions(Set<Transaction> processedTransactions) { 
        this.processedTransactions = processedTransactions; 
    }
    
    public Set<Account> getApprovedAccounts() { 
        return approvedAccounts; 
    }
    
    public void setApprovedAccounts(Set<Account> approvedAccounts) { 
        this.approvedAccounts = approvedAccounts; 
    }
}
