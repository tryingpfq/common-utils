package com.tryingpfq.rpc.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class SnappyCompressor implements Compressor{
    @Override
    public byte[] compress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.compress(array);
    }

    @Override
    public byte[] unCompress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.uncompress(array);
    }
}
