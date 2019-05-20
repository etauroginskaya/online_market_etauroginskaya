package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Profile;
import com.gmail.etauroginskaya.online_market.service.model.ProfileDTO;

public interface ProfileConverter {

    ProfileDTO toDTO(Profile profile);

    Profile toEntity(ProfileDTO profileDTO);
}
