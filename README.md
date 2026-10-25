# LTHDT - Tuần 7: Quản lý thiết bị phòng dạy

Phí Việt Hiếu - 202419055

Bài tập đa hình và generic: quản lý điều khiển máy chiếu, điều khiển điều hòa, micro không dây.

## Chạy chương trình

Cần JDK 25 và Maven.

```
mvn compile exec:java
mvn test
```

## Ghi chú về điểm ưu tiên của điều khiển điều hòa

Đề cho điểm bằng 100 nếu điều khiển tương thích với phòng, nhưng `getDefaultPriorityScore()` không nhận tham số phòng. Vì vậy:

- `getDefaultPriorityScore()` trả 100 khi điều khiển có ít nhất một phòng tương thích, ngược lại trả 0.
- Muốn chấm điểm theo một phòng cụ thể thì dùng `tinhDiemTheoPhong(roomId)`, ví dụ:

```java
new HighestPriorityStrategy<AirConditionerRemote>(d -> d.tinhDiemTheoPhong("A203"))
```

Cách thường dùng hơn là lọc theo phòng bằng `allocate(d -> d.isCompatibleWith("A203"))`.
