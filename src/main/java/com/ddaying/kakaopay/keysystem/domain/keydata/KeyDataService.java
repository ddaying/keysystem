package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class KeyDataService {

    @Autowired
    private KeyDataRepository keyDataRepository;


    public KeyData create(KeyChannel keyChannel, String value) {
        KeyData keyData = new KeyData();
        keyData.setKeyChannel(keyChannel);
        keyData.setValue(value);
        keyData.toShow();

        try {
            return keyDataRepository.save(keyData);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ApiStatus.ALREADY_GENERATED_KEY);
        }
    }
}
