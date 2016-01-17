package com.otchi.utils.mocks;

import com.otchi.application.utils.DateFactory;

import java.util.Date;

/**
 * Created by Abdellah on 19/01/2016.
 */
public class MockDateFactory implements DateFactory {
    private Date date;

    @Override
    public Date now() {
        return date;
    }

    //use this function to change the actual date
    public void setNow(Date mockedDate) {
        this.date = mockedDate;
    }
}
