package com.tryingpfq.nettyheart.msg;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

/**
 * @Author tryingpfq
 * @Date 2020/5/23
 */
@Message
@Getter
public class MsgData {

    /**
     * 类型
     */
    private int type;

    /**
     * 内容
     */
    private String body;

    public static MsgData valueOf(int type, String body) {
        MsgData msgData = new MsgData();
        msgData.type = type;
        msgData.body = body;
        return msgData;
    }

    @Override
    public String toString() {
        return "MsgData{" +
                "type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
}
