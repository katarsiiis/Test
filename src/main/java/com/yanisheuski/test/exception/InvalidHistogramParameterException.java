package com.yanisheuski.test.exception;

public class InvalidHistogramParameterException extends RuntimeException {

    public InvalidHistogramParameterException(String parameter) {
        super(
                "Unsupported histogram parameter: " + parameter
                        + ". Allowed values: brand, city, country, amenities"
        );
    }
}