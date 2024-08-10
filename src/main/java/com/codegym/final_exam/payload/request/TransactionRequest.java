package com.codegym.final_exam.payload.request;


import com.codegym.final_exam.model.Customer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    @NotNull(message = "Mã giao dịch không được để trống")
    @Pattern(regexp = "^MGD-\\d{4}$", message = "Mã giao dịch phải đúng định dạng (MGD-XXXX), trong đó XXXX là các chữ số (0-9)")
    String transactionCode;

    @NotNull(message = "Tên khách hàng không được để trống")
    Customer customer;

    @NotNull(message = "Ngày giao dịch không được để trống")
    @Future(message = "Ngày giao dịch phải là ngày/tháng/năm và phải lớn hơn thời gian hiện tại")
    LocalDate transactionDate;

    @NotNull(message = "Loại dịch vụ không được để trống")
    String typeService;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "500000", message = "Đơn giá phải là số và phải lớn hơn 500.000 (VND)")
    double price;

    @NotNull(message = "Diện tích không được để trống")
    @DecimalMin(value = "20", message = "Diện tích phải là số và lớn hơn 20 (m2)")
    double area;
}
