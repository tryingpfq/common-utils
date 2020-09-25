package com.tryingpfq.rank.handler;

import com.tryingpfq.rank.common.REntity;
import com.tryingpfq.rank.common.ROperator;

/**
 * @author tryingpfq
 * @date 2020/9/21
 **/
public abstract class BaseRankHandler {


    public ROperator compareTo(REntity older, REntity newer) {
        return (older.getValue() < newer.getValue()) ? ROperator.UP :
                ((older.getValue() != newer.getValue()) ? ROperator.DOWN : ROperator.NONE);
    }

}
