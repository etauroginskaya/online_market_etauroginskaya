package com.gmail.etauroginskaya.online_market.service.model;

import com.gmail.etauroginskaya.online_market.service.validator.annotations.MatchesXSD;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class FileDTO {

    @NotNull
    @MatchesXSD
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}