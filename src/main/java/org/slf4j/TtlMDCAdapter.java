package org.slf4j;

import com.ofpay.logback.TtlLogbackMDCAdapter;

/**
 * Created by wuwen on 15/7/31.
 * Update by akihiro on 2025/05/28
 * Copy from ch.qos.logback.classic.util.LogbackMDCAdapter
 * @see ch.qos.logback.classic.util.LogbackMDCAdapter
 * @deprecated Use {@link TtlLogbackMDCAdapter} directly instead of this class.
 */
@Deprecated
public class TtlMDCAdapter extends TtlLogbackMDCAdapter {
    static {
        MDC.mdcAdapter = TtlLogbackMDCAdapter.getInstance();
    }
}