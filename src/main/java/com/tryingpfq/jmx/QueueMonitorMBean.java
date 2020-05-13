package com.tryingpfq.jmx;

import javax.management.MXBean;

/**
 * @author tryingpfq
 * @date 2020/4/15
 **/
@MXBean
public interface QueueMonitorMBean {

    int getQueue_Size();
}
