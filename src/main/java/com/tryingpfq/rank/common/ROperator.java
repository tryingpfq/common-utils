package com.tryingpfq.rank.common;

/**
 * 排行比较操作
 *
 * @author tryingpfq
 * @date 2020/9/21
 **/
public enum ROperator {
    UP{
        @Override
        public int getMark() {
            return -1;
        }
    },

    NONE{
        @Override
        public int getMark() {
            return 0;
        }
    },

    DOWN{
        @Override
        public int getMark() {
            return 1;
        }
    },

    ;

    abstract public int getMark();
}
