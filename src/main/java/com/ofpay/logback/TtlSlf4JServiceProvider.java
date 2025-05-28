package com.ofpay.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LogbackServiceProvider;
import org.slf4j.TtlMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 *
 * @author akihiro
 * @date 2025/05/28.
 */
public class TtlSlf4JServiceProvider extends LogbackServiceProvider {

	private MDCAdapter ttlMDCAdapter;

	@Override
	public void initialize() {
		this.ttlMDCAdapter = new TtlMDCAdapter();
		super.initialize();
		((LoggerContext)super.getLoggerFactory()).setMDCAdapter(ttlMDCAdapter);
	}

	@Override
	public MDCAdapter getMDCAdapter() {
		return this.ttlMDCAdapter;
	}
}
