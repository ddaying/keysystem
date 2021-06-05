package com.ddaying.kakaopay.keysystem.domain.keychannel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyChannelRepository extends JpaRepository<KeyChannel, Long> {

    Optional<KeyChannel> findByName(String name);
}
