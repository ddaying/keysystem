package com.ddaying.kakaopay.keysystem.domain.system;

import com.ddaying.kakaopay.keysystem.domain.BaseEntity;
import com.ddaying.kakaopay.keysystem.domain.DisplayStatus;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.key.Key;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "system", schema = "insurance")
public class System  extends BaseEntity {

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

    @Enumerated(value = EnumType.STRING)
    @Column
    private DisplayStatus displayStatus;


    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL)
    private List<Key> keys = Lists.newArrayList();


    public void addKey(Key key) {
        this.keys.add(key);
        key.setSystem(this);
    }

    public void toShow() {
        this.displayStatus = DisplayStatus.SHOW;
    }

    public boolean isDeleted() {
        return this.displayStatus.isDelete();
    }

}
