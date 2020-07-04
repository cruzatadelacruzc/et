package com.example.demo.service.dto.feign;


import java.util.Set;

public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private Set<String> authorities;

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public UserDTO setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public UserDTO setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
