package com.enigmacamp.tokonyadia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String transactionId;
    private ProductResponse productResponse;
    private Long productPrice;
    private Integer qty;
}
