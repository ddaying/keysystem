package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.domain.BaseEntity;
import com.ddaying.kakaopay.keysystem.domain.DisplayStatus;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "key_data", schema = "insurance")
public class KeyData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    private KeyChannel keyChannel;

    @Column
    private String value;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DisplayStatus displayStatus;

    public void toShow() {
        this.displayStatus = DisplayStatus.SHOW;
    }

}
