package com.sovesting;

public enum Type {
    VEST(1),
    PERF(2),
    SALE(3);

    private final int type;

    private Type(int type) {
        this.type = type;
    }
}
