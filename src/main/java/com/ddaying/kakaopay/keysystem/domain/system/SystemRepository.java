package com.ddaying.kakaopay.keysystem.domain.system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemRepository extends JpaRepository<System, Long> {

    Optional<System> findByName(String name);
}
