package com.company.Vers3.Services;

import org.springframework.beans.factory.annotation.Value;

public class Service_Three_A {
    @Value("${prop.E}")
    private String one;

    public String getOne() {
        return one;
    }
}
