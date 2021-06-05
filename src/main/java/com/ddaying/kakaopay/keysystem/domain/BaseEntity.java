package com.ddaying.kakaopay.keysystem.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Column
    @CreatedBy
    private String createBy;

    @Column
    @CreatedDate
    private LocalDateTime createTime;

    @Column
    @LastModifiedBy
    private String modifyBy;

    @Column
    @LastModifiedDate
    private LocalDateTime modifyTime;

    @PrePersist
    public void prePersist() {
        if(this.createBy == null) {
            this.createBy = "system";
        }
        this.createTime = LocalDateTime.now();
    }

}
