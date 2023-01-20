package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    private final BusinessService businessService;

    public DemoController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/parallel-stream")
    public List<String> getParallelStream(@RequestParam String name, @RequestParam int numberOfProviders) {
        final String fullName = "ParallelStream " + name;
        return businessService.executeParallelStream(fullName, numberOfProviders);
    }

    @GetMapping("/executor-service")
    public List<String> getExecutorService(@RequestParam String name, @RequestParam int numberOfProviders) {
        final String fullName = "ExecutorService " + name;
        return businessService.executeExecutorService(fullName, numberOfProviders);
    }
}
