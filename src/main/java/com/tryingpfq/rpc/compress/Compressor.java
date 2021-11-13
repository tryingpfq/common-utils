package com.tryingpfq.rpc.compress;

import java.io.IOException;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public interface Compressor {

    byte[] compress(byte[] array) throws IOException;

    byte[] unCompress(byte[] array) throws IOException;
}
