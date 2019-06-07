package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Profile;
import com.gmail.etauroginskaya.online_market.service.converter.ProfileConverter;
import com.gmail.etauroginskaya.online_market.service.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {

    @Override
    public ProfileDTO toDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setTelephone(profile.getTelephone());
        return profileDTO;
    }
}
