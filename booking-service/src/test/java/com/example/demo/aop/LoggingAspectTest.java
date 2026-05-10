package com.example.demo.aop;

import com.example.demo.service.DummyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingAspectTest {

    @Autowired
    private DummyService dummyService;

    @Test
    void testNormalExecution() {
        dummyService.testMethod();
    }

    @Test
    void testExceptionExecution() {
        try {
            dummyService.errorMethod();
        } catch (Exception e) {
            // Expected exception
        }
    }
}