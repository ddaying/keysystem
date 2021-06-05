package com.ddaying.kakaopay.keysystem.domain;

public enum DisplayStatus {

    SHOW, DELETE;

    public boolean isDelete() {
        return this.equals(DELETE);
    }
}
