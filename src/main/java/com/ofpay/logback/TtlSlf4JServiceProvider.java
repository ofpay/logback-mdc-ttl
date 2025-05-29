package com.ofpay.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LogbackServiceProvider;
import org.slf4j.spi.MDCAdapter;

/**
 *
 * @author akihiro
 * @since 2.0.0
 */
public class TtlSlf4JServiceProvider extends LogbackServiceProvider {

    private MDCAdapter ttlMDCAdapter;

    @Override
    public void initialize() {
        super.initialize();
        this.ttlMDCAdapter = TtlLogbackMDCAdapter.getInstance();
        ((LoggerContext)super.getLoggerFactory()).setMDCAdapter(ttlMDCAdapter);
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return this.ttlMDCAdapter;
    }
}
