/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

package com.lthdt.tuan7.exception;

// khong co ung vien phu hop de chon
public class NoSuitableItemException extends RuntimeException {
    public NoSuitableItemException() {
        super("Khong co doi tuong phu hop");
    }

    public NoSuitableItemException(String message) {
        super(message);
    }
}
