package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DummyService {

    public String testMethod() {
        return "success";
    }

    public void errorMethod() {
        throw new RuntimeException("Test exception");
    }
}