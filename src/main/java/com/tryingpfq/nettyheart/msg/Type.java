package com.tryingpfq.nettyheart.msg;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
public enum Type {

    Ping(1),

    Pong(2),

    Msg(3),
    ;

    private int type;

    Type(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
