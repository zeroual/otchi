package com.otchi.application.utils;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateFactoryImpl implements DateFactory {

    @Override
    public Date now() {
        return new Date();
    }
}
