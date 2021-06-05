package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.BaseEntity;
import com.ddaying.kakaopay.keysystem.domain.DisplayStatus;
import com.ddaying.kakaopay.keysystem.domain.system.System;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "key", schema = "insurance")
public class Key  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id", referencedColumnName = "id")
    private System system;

    @Column
    private String value;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DisplayStatus displayStatus;

}
