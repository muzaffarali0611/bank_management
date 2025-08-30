-- Create users table (base table for all user types)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(20),
    street_address VARCHAR(255),
    street_address2 VARCHAR(255),
    city VARCHAR(100),
    state_province VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    last_login TIMESTAMP,
    failed_login_attempts INTEGER DEFAULT 0,
    account_locked_until TIMESTAMP,
    user_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create customers table
CREATE TABLE customers (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    customer_id VARCHAR(50) UNIQUE NOT NULL,
    credit_score INTEGER,
    customer_type VARCHAR(20) NOT NULL DEFAULT 'REGULAR',
    annual_income DECIMAL(19,2),
    employment_status VARCHAR(100),
    employer_name VARCHAR(255),
    kyc_verified BOOLEAN NOT NULL DEFAULT FALSE,
    kyc_verified_at TIMESTAMP,
    kyc_verified_by BIGINT
);

-- Create staff table
CREATE TABLE staff (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    employee_id VARCHAR(50) UNIQUE NOT NULL,
    department VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL,
    hire_date TIMESTAMP NOT NULL,
    salary DECIMAL(19,2),
    supervisor_id BIGINT,
    staff_level VARCHAR(20) NOT NULL,
    is_authorized_for_transactions BOOLEAN DEFAULT FALSE,
    is_authorized_for_account_management BOOLEAN DEFAULT FALSE,
    is_authorized_for_kyc BOOLEAN DEFAULT FALSE
);

-- Create admins table
CREATE TABLE admins (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    admin_id VARCHAR(50) UNIQUE NOT NULL,
    admin_level VARCHAR(30) NOT NULL,
    super_admin BOOLEAN NOT NULL DEFAULT FALSE,
    last_system_access TIMESTAMP,
    ip_whitelist TEXT
);

-- Create roles table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create permissions table
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    permission_type VARCHAR(50) NOT NULL,
    resource VARCHAR(100),
    action VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create role_permissions junction table
CREATE TABLE role_permissions (
    role_id BIGINT REFERENCES roles(id),
    permission_id BIGINT REFERENCES permissions(id),
    PRIMARY KEY (role_id, permission_id)
);

-- Create staff_roles junction table
CREATE TABLE staff_roles (
    staff_id BIGINT REFERENCES staff(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (staff_id, role_id)
);

-- Create admin_permissions junction table
CREATE TABLE admin_permissions (
    admin_id BIGINT REFERENCES admins(id),
    permission_id BIGINT REFERENCES permissions(id),
    PRIMARY KEY (admin_id, permission_id)
);

-- Create accounts table
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    account_type VARCHAR(30) NOT NULL,
    account_status VARCHAR(30) NOT NULL DEFAULT 'PENDING_APPROVAL',
    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    interest_rate DECIMAL(5,4),
    minimum_balance DECIMAL(19,2),
    daily_transaction_limit DECIMAL(19,2),
    monthly_transaction_limit DECIMAL(19,2),
    opening_date TIMESTAMP,
    last_activity_date TIMESTAMP,
    approved_by BIGINT REFERENCES staff(id),
    approved_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create transactions table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(50) UNIQUE NOT NULL,
    from_account_id BIGINT REFERENCES accounts(id),
    to_account_id BIGINT REFERENCES accounts(id),
    transaction_type VARCHAR(30) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    transaction_date TIMESTAMP NOT NULL,
    processed_date TIMESTAMP,
    processed_by BIGINT REFERENCES staff(id),
    reference_number VARCHAR(100),
    fee_amount DECIMAL(19,2) DEFAULT 0.00,
    exchange_rate DECIMAL(19,6) DEFAULT 1.000000,
    ip_address VARCHAR(45),
    user_agent TEXT,
    location VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_customers_customer_id ON customers(customer_id);
CREATE INDEX idx_customers_kyc_verified ON customers(kyc_verified);
CREATE INDEX idx_staff_employee_id ON staff(employee_id);
CREATE INDEX idx_staff_department ON staff(department);
CREATE INDEX idx_admins_admin_id ON admins(admin_id);
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_accounts_customer_id ON accounts(customer_id);
CREATE INDEX idx_accounts_status ON accounts(account_status);
CREATE INDEX idx_transactions_transaction_id ON transactions(transaction_id);
CREATE INDEX idx_transactions_from_account ON transactions(from_account_id);
CREATE INDEX idx_transactions_to_account ON transactions(to_account_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_status ON transactions(status);

-- Insert default roles
INSERT INTO roles (name, description) VALUES 
('ADMIN', 'System Administrator with full access'),
('STAFF', 'Bank staff member with limited access'),
('CUSTOMER', 'Bank customer with basic access');

-- Insert default permissions
INSERT INTO permissions (name, description, permission_type, resource, action) VALUES 
('USER_MANAGEMENT', 'Manage user accounts', 'USER_MANAGEMENT', 'user', 'manage'),
('ACCOUNT_MANAGEMENT', 'Manage bank accounts', 'ACCOUNT_MANAGEMENT', 'account', 'manage'),
('TRANSACTION_MANAGEMENT', 'Process transactions', 'TRANSACTION_MANAGEMENT', 'transaction', 'process'),
('KYC_MANAGEMENT', 'Manage KYC verification', 'KYC_MANAGEMENT', 'kyc', 'verify'),
('REPORT_VIEW', 'View reports', 'REPORT_VIEW', 'report', 'view'),
('AUDIT_VIEW', 'View audit logs', 'AUDIT_VIEW', 'audit', 'view'),
('SYSTEM_MANAGEMENT', 'Manage system configuration', 'SYSTEM_MANAGEMENT', 'system', 'manage');

-- Assign permissions to roles
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'ADMIN';

INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'STAFF' AND p.name IN ('ACCOUNT_MANAGEMENT', 'TRANSACTION_MANAGEMENT', 'KYC_MANAGEMENT');

INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'CUSTOMER' AND p.name IN ('REPORT_VIEW');
