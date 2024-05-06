package com.example.chumbatelegramm.exceptions;

public class BotRequestException extends RuntimeException {
    public BotRequestException(String key) {
        super(key);
    }
}
