package com.qee.common;

public interface Ordered {
    default int order() {
        return 1;
    }
}
