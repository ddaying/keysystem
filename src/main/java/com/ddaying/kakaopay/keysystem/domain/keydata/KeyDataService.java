package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import org.springframework.beans.factory.annotation.Autowired;
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

        return keyDataRepository.save(keyData);
    }
}
