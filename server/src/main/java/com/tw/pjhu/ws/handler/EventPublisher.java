package com.tw.pjhu.ws.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import static org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization;

@RequiredArgsConstructor
@Component
public class EventPublisher {
    @NonNull
    private final ApplicationEventPublisher eventBus;

    public void publish(Object event) {
        eventBus.publishEvent(event);
    }

}

