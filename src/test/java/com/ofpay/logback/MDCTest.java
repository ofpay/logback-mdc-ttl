package com.ofpay.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MDCTest {

    Logger log = (Logger) LoggerFactory.getLogger("com.ofpay");

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeEach
    void setUp() {
        listAppender.start();
        log.addAppender(listAppender);
    }

    @Test
    void testMdc() throws InterruptedException, ExecutionException {
        final String uuidKey = "uuid";
        final String testUuid = UUID.randomUUID().toString();
        MDC.put(uuidKey, testUuid);

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<?> submit = executorService.submit(() -> log.info("1"));

        submit.get();
        String msg = listAppender.list.get(0).getMDCPropertyMap().get(uuidKey);
        MDC.remove(uuidKey);

        Future<?> submit2 = executorService.submit(() -> log.info("2"));
        submit2.get();
        String msg2 = listAppender.list.get(1).getMDCPropertyMap().get(uuidKey);

        Assertions.assertEquals(testUuid, msg);
        Assertions.assertNull(msg2);
    }
}
