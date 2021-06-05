package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.system.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class KeyService {

    @Autowired
    private KeyRepository keyRepository;


    public Key create(System system, String value) {
        Key key = new Key();
        key.setSystem(system);
        key.setValue(value);
        key.toShow();

        return keyRepository.save(key);
    }
}
