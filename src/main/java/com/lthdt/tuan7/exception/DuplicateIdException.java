package com.lthdt.tuan7.exception;

public class DuplicateIdException extends RuntimeException {
    private final String duplicateId;

    public DuplicateIdException(String duplicateId) {
        super("Ma da ton tai: " + duplicateId);
        this.duplicateId = duplicateId;
    }

    public String getDuplicateId() {
        return duplicateId;
    }
}
