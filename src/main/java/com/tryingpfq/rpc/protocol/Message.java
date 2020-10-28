package com.tryingpfq.rpc.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
@Setter
public class Message<T> {
    private Header header;

    private T context;


    public Message(Header header, T context) {
        this.header = header;
        this.context = context;
    }
}
