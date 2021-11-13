package com.tryingpfq.rank.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 排行实体
 *
 * @author tryingpfq
 * @date 2020/9/21
 **/
@Getter
@Setter
public class REntity implements Comparable<REntity> {

    private long id;

    /**
     * 比较值信息
     */
    private long value;

    /**
     * 最后一次更新时间
     */
    private long updateTime;

    /**
     * 当前排汗
     */
    private transient int curRank;

    /**
     * 排行类型
     */

    private transient RankType type;

    public REntity(RankType type, long id, int rank, long value) {
        this.type = type;
        this.id = id;
        this.curRank = rank;
        this.value = value;
        this.updateTime = System.currentTimeMillis();
    }

    public void update(long newValue) {
        this.value = newValue;
        this.updateTime = System.currentTimeMillis();
    }

    @Override
    public int compareTo(REntity o) {
        switch (type.getHandler().compareTo(this, o)) {
            case UP:
                return 1;
            case DOWN:
                return -1;
            default:
                return Long.compare(this.getUpdateTime(), o.getUpdateTime());
        }
    }
}
