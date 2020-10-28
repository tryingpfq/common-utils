package com.tryingpfq.rpc.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
@Setter
public class Header {


    private short magic;

    private byte version;

    /**
     * 附加信息
     */
    private byte extraInfo;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 消除长度
     */
    private Integer size;

    public Header(short magic, byte version) {
        this.magic = magic;
        this.version = version;
        this.extraInfo = 0;
    }


    public Header(short magic, byte version, byte extraInfo, Long messageId, Integer size) {
        this.magic = magic;
        this.version = version;
        this.extraInfo = extraInfo;
        this. messageId = messageId;
        this.size = size;
    }
}
