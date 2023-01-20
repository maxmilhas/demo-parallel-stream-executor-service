package com.example;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BusinessService {

    private final ExternalService externalService;

    private final ExecutorService customExecutorService;

    public BusinessService(ExternalService externalService, ExecutorService customExecutorService) {
        this.externalService = externalService;
        this.customExecutorService = customExecutorService;
    }

    public List<String> executeParallelStream(String name, int numberOfProviders) {
        return getListOFProviders(numberOfProviders).stream()
            .parallel()
            .map(i -> this.externalService.slowCall(name + " - " + i))
            .collect(Collectors.toList());
    }

    public List<String> executeExecutorService(String name, int numberOfProviders) {
        CompletableFuture[] futures = getListOFProviders(numberOfProviders).stream()
                .map(this::externalServiceSlowCallAsync)
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        return Arrays.stream(futures)
                .map(this::getResult)
                .collect(Collectors.toList());
    }

    private List<String> getListOFProviders(int numberOfProviders) {
        return IntStream.range(0, numberOfProviders)
                .mapToObj(i -> {
                    return "Provider-" + String.valueOf(i);
                })
                .collect(Collectors.toList());
    }

    private CompletableFuture<String> externalServiceSlowCallAsync(String providerName) {
        return CompletableFuture
                .supplyAsync(() -> this.externalService.slowCall(providerName), this.customExecutorService);
    }

    private String getResult(Future future) {
        try {
            return (String) future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
