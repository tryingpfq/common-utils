package com.tryingpfq.rank.baselist;

import com.tryingpfq.rank.common.REntity;
import com.tryingpfq.rank.common.ROperator;
import com.tryingpfq.rank.common.RankType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * @author tryingpfq
 * @date 2020/9/21
 **/
public class CommonRankList {

    private transient ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private RankType rankType;

    private transient List<REntity> rankList;

    private transient Map<Long,REntity> rankMap;

    public static CommonRankList valueOf(RankType rankType) {
        CommonRankList rankList = new CommonRankList();
        rankList.rankType = rankType;
        rankList.rankList = new ArrayList<>(rankType.getMax() + 1);
        rankList.rankMap = new ConcurrentHashMap<>(rankType.getMax() + 1);
        return rankList;
    }

    public REntity update(long id, long newValue, Consumer<REntity> updater) {
        lock.writeLock().lock();
        try {
            REntity entity = rankMap.get(id);
            if (entity == null) {
                //未上榜
                //判断榜是否已经满了
                entity = new REntity(rankType, id, rankList.size(), newValue);
                //上榜
                insert(entity);

            }else{
               entity.update(newValue);

            }
            return entity;
        }finally {
            lock.writeLock().unlock();;
        }
    }

    private void doUpdate(REntity entity, Consumer<REntity> updater, ROperator operator, boolean isFirst) {
        if (operator == ROperator.NONE) {
            return;
        }
        if (updater != null) {
            updater.accept(entity);
        }

        int mark = operator.getMark();

        int oldRank = entity.getCurRank();

        for (int i = oldRank + mark; i < rankList.size() && i >= 0; i++) {
            REntity exit = rankList.get(i);
            if (rankType.getHandler().compareTo(exit, entity) != operator) {
                //不再需要调整了
                break;
            }
            rankList.set(i - mark, exit);
            if (exit.getCurRank() != i - mark) {
                exit.setCurRank(i - mark);
            }
            rankList.set(i, entity);
            entity.setCurRank(i);
        }
        if (rankList.size() > rankType.getMax()) {
            removeWithIndex(rankList.size() - 1);
        }
        //通知
    }

    public void removeWithIndex(int index) {
        REntity remove = rankList.remove(index);
        if (remove != null) {
            rankMap.remove(remove.getId());
        }
    }

    private void insert(REntity entity) {
        if (rankMap.put(entity.getId(), entity) == null) {
            rankList.add(entity);
        }
    }
}
