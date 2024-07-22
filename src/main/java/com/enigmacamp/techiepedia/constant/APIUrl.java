package com.enigmacamp.techiepedia.constant;

public class APIUrl {

    private APIUrl() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PRODUCT_API = "/api/v1/product";
    public static final String CUSTOMER_API = "/api/v1/customer";
    public static final String TRANSACTION_API = "/api/v1/transaction";
    public static final String AUTH_API = "/api/v1/auth";
}