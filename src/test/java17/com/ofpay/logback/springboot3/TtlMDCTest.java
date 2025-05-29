package com.ofpay.logback.springboot3;

import ch.qos.logback.classic.Logger;
import com.alibaba.ttl.TtlRunnable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * @author akihiro
 * @date 2025/05/28.
 */
@SpringBootTest
public class TtlMDCTest {

    Logger log = (Logger) LoggerFactory.getLogger("com.ofpay");

    @Test
    void testMdc() throws InterruptedException, ExecutionException {
        final String uuidKey = "uuid";
        final String testUuid = UUID.randomUUID().toString();
        MDC.put(uuidKey, testUuid);

        log.info("before simpleAsyncTask uuid:{}...", MDC.get("uuid"));
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setThreadNamePrefix("ttl-thread-");

        final String[] asyncUuid = new String[2];
        Future<?> submit1 = executor.submit(TtlRunnable.get(() -> {
            log.info("in simpleAsyncTask uuid:{}...", MDC.get("uuid"));
            asyncUuid[0] = MDC.get("uuid");
        }));
        submit1.get();
        log.info("after simpleAsyncTask uuid:{}...", MDC.get("uuid"));
        MDC.remove(uuidKey);

        log.info("remove uuid:{}...", MDC.get("uuid"));
        Future<?> submit2 = executor.submit(TtlRunnable.get(() -> {
            log.info("in simpleAsyncTask with remove uuid:{}...", MDC.get("uuid"));
            asyncUuid[1] = MDC.get("uuid");
        }));
        submit2.get();
        log.info("after simpleAsyncTask with remove uuid:{}...", MDC.get("uuid"));
        Assertions.assertEquals(testUuid, asyncUuid[0]);
        Assertions.assertNull(asyncUuid[1]);
    }
}
