package com.ddaying.kakaopay.keysystem.domain.keychannel;

import com.ddaying.kakaopay.keysystem.domain.BaseEntity;
import com.ddaying.kakaopay.keysystem.domain.DisplayStatus;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.keydata.KeyData;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "key_channel", schema = "insurance")
public class KeyChannel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column
    private SystemType type;

    @Column
    private String generator;

    @Column
    private Integer length;

    @Column
    private Long value;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DisplayStatus displayStatus;


    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "keyChannel", cascade = CascadeType.ALL)
    private List<KeyData> keyData = Lists.newArrayList();


    public void addKey(KeyData keyData) {
        this.keyData.add(keyData);
        keyData.setKeyChannel(this);
    }

    public void toShow() {
        this.displayStatus = DisplayStatus.SHOW;
    }

    public boolean isDeleted() {
        return this.displayStatus.isDelete();
    }

}
