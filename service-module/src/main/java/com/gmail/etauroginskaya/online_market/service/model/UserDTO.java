package com.gmail.etauroginskaya.online_market.service.model;

import com.gmail.etauroginskaya.online_market.service.validator.annotations.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @NotBlank
    @Size(max = 40)
    @Pattern(regexp = "[A-Za-z]+", message = "only Latin characters must be used!")
    private String surname;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "[A-Za-z]+", message = "only Latin characters must be used!")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    @UniqueEmail
    private String email;

    private String password;

    private RoleDTO role;

    private ProfileDTO profileDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }
}
