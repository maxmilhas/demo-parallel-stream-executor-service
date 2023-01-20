package com.example;

import org.springframework.stereotype.Service;

@Service
public class ExternalService {

    public String slowCall(String name) {
        try {
            final String callName = Thread.currentThread().toString() + " | " + name;
            System.out.println(callName);
            Thread.sleep(3_000);
            return callName;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
