package com.gmail.etauroginskaya.online_market.service.model;

<<<<<<< HEAD
public class ReviewDTO {

    private Long id;
=======
import javax.validation.constraints.NotBlank;

public class ReviewDTO {

    private Long id;
    @NotBlank
>>>>>>> develop
    private String description;
    private String created;
    private Boolean show;
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
