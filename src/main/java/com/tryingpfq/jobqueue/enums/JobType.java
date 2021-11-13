package com.tryingpfq.jobqueue.enums;

/**
 * 任务类型
 * @author tryingpfq
 * @date 2020/5/7
 **/
public enum  JobType {

    None(0),

    /**
     * 订单
     */
    Order(1),

    ;

    int index;


    JobType(int index) {
        this.index = index;
    }

}
