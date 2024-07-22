package com.enigmacamp.techiepedia.model.dto.response;

import com.enigmacamp.techiepedia.model.entities.Customer;
import com.enigmacamp.techiepedia.model.entities.TransactionDetail;
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