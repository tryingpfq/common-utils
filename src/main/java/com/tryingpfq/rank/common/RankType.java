package com.tryingpfq.rank.common;

import com.tryingpfq.rank.handler.BaseRankHandler;
import com.tryingpfq.rank.handler.impl.HotSearchHandler;

import java.util.function.Supplier;

/**
 * @author tryingpfq
 * @date 2020/9/21
 **/
public enum RankType {
    HOT_SEARCH(100,HotSearchHandler::new),

    ;


    private BaseRankHandler handler;

    private int max;


    RankType(int max,Supplier<BaseRankHandler> supplier) {
        this.max = max;
        this.handler = supplier.get();
    }

    public BaseRankHandler getHandler() {
        return handler;
    }

    public int getMax() {
        return max;
    }
}
