package com.otchi.application.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateFactoryImpl implements DateFactory {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
