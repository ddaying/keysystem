package com.ddaying.kakaopay.keysystem.domain.system;

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
public class SystemService {

    @Autowired
    private SystemRepository systemRepository;


    public System create(String name, String description, SystemType type) {
        return this.create(name, description, type, null, null);
    }

    public System create(String name, String description, SystemType type, @Nullable String generator, @Nullable Integer length) {
        System system = new System();
        system.setName(name);
        system.setDescription(description);
        system.setType(type);

        if (type.isNumber()) {
            system.setGenerator(generator);
            system.setLength(length);
        }
        system.toShow();

        try {
            return systemRepository.save(system);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ApiStatus.ALREADY_REGISTERED_KEY);
        }
    }

    public System getByName(String name) {
        return systemRepository.findByName(name).orElseThrow(() -> new ApiException(ApiStatus.INVALID_KEY));
    }

}
