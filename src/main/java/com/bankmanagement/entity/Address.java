package com.bankmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address {
    
    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address cannot exceed 255 characters")
    @Column(name = "street_address")
    private String streetAddress;
    
    @Column(name = "street_address2")
    private String streetAddress2;
    
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    @Column(name = "city")
    private String city;
    
    @NotBlank(message = "State/Province is required")
    @Size(max = 100, message = "State/Province cannot exceed 100 characters")
    @Column(name = "state_province")
    private String stateProvince;
    
    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    @Column(name = "postal_code")
    private String postalCode;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    @Column(name = "country")
    private String country;
    
    // Constructors
    public Address() {}
    
    public Address(String streetAddress, String city, String stateProvince, String postalCode, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.stateProvince = stateProvince;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    // Getters and Setters
    public String getStreetAddress() { 
        return streetAddress; 
    }
    
    public void setStreetAddress(String streetAddress) { 
        this.streetAddress = streetAddress; 
    }
    
    public String getStreetAddress2() { 
        return streetAddress2; 
    }
    
    public void setStreetAddress2(String streetAddress2) { 
        this.streetAddress2 = streetAddress2; 
    }
    
    public String getCity() { 
        return city; 
    }
    
    public void setCity(String city) { 
        this.city = city; 
    }
    
    public String getStateProvince() { 
        return stateProvince; 
    }
    
    public void setStateProvince(String stateProvince) { 
        this.stateProvince = stateProvince; 
    }
    
    public String getPostalCode() { 
        return postalCode; 
    }
    
    public void setPostalCode(String postalCode) { 
        this.postalCode = postalCode; 
    }
    
    public String getCountry() { 
        return country; 
    }
    
    public void setCountry(String country) { 
        this.country = country; 
    }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s %s, %s", 
            streetAddress, city, stateProvince, postalCode, country);
    }
}
