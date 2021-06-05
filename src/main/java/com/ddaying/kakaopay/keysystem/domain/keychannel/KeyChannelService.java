package com.ddaying.kakaopay.keysystem.domain.keychannel;

import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class KeyChannelService {

    @Autowired
    private KeyChannelRepository keyChannelRepository;


    public KeyChannel create(String name, String description, SystemType type) {
        return this.create(name, description, type, null, null);
    }

    public KeyChannel create(String name, String description, SystemType type, @Nullable String generator, @Nullable Integer length) {
        KeyChannel keyChannel = new KeyChannel();
        keyChannel.setName(name);
        keyChannel.setDescription(description);
        keyChannel.setType(type);

        if (type.isNumber()) {
            keyChannel.setGenerator(generator);
            keyChannel.setLength(length);
        }
        keyChannel.toShow();

        try {
            return keyChannelRepository.save(keyChannel);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ApiStatus.ALREADY_REGISTERED_KEY);
        }
    }

    public KeyChannel getByName(String name) {
        return keyChannelRepository.findByName(name).orElseThrow(() -> new ApiException(ApiStatus.INVALID_KEY));
    }

}
