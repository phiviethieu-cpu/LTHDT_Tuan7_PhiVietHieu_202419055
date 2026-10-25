package com.lthdt.tuan7.exception;

public class NoSuitableItemException extends RuntimeException {
    public NoSuitableItemException() {
        super("Khong co doi tuong phu hop");
    }

    public NoSuitableItemException(String message) {
        super(message);
    }
}
