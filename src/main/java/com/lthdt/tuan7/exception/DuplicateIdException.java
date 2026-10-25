/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.exception;

// them trung ma vao kho
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
