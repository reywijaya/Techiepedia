package com.enigmacamp.tokonyadia.dto.response;

import com.enigmacamp.tokonyadia.model.Customer;
import com.enigmacamp.tokonyadia.model.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Customer customer;
    private Date date;
    private List<TransactionDetail> transactions;
    private Long totalPayment;
}