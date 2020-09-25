package com.tryingpfq.rank.common;

import com.tryingpfq.rank.baselist.CommonRankList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tryingpfq
 * @date 2020/9/21
 **/
public class RankController {

    private Map<RankType, CommonRankList> rankCache = new ConcurrentHashMap<>();

    public CommonRankList loadOrCreateRankList(RankType rankType) {
        return rankCache.computeIfAbsent(rankType, CommonRankList::valueOf);
    }
}
