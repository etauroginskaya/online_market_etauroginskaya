package com.gmail.etauroginskaya.online_market.service.model;

<<<<<<< HEAD
=======
import com.gmail.etauroginskaya.online_market.service.validator.annotations.UniqueEmail;

>>>>>>> develop
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @NotBlank
    @Size(max = 40)
<<<<<<< HEAD
    @Pattern(regexp = "[A-Za-z]+", message = "Only Latin characters must be used!")
=======
    @Pattern(regexp = "[A-Za-z]+", message = "only Latin characters must be used!")
>>>>>>> develop
    private String surname;

    @NotBlank
    @Size(max = 20)
<<<<<<< HEAD
    @Pattern(regexp = "[A-Za-z]+", message = "Only Latin characters must be used!")
    private String name;

    @Size(max = 40)
    @Pattern(regexp = "[A-Za-z]+", message = "Only Latin characters must be used!")
    private String patronymic;

    @NotBlank
    @Size(max = 50)
    @Email
=======
    @Pattern(regexp = "[A-Za-z]+", message = "only Latin characters must be used!")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    @UniqueEmail
>>>>>>> develop
    private String email;

    private String password;

    private RoleDTO role;

<<<<<<< HEAD
=======
    private ProfileDTO profileDTO;

>>>>>>> develop
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

<<<<<<< HEAD
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

=======
>>>>>>> develop
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
<<<<<<< HEAD
=======

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }
>>>>>>> develop
}
