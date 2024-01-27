package com.fetch.receiptprocessor.exception;

public class ReceiptException {

    public static class InvalidReceiptException extends RuntimeException {
        public InvalidReceiptException(String message) {
            super(message);
        }
    }

    public static class ReceiptProcessingException extends RuntimeException {
        public ReceiptProcessingException(String message) {
            super(message);
        }
        // Additional constructors or methods, if needed
    }
}
