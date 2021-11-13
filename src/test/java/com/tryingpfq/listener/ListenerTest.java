package com.tryingpfq.listener;

import com.tryingpfq.listener.anno.Listener;
import com.tryingpfq.listener.anno.Order;
import com.tryingpfq.listener.support.ListenerSupport;
import org.junit.Test;

/**
 * @author tryingpfq
 * @date 2020/11/13
 **/
public class ListenerTest {

    @Test
    public void test() {
        ListenerSupport<Inter> support = ListenerSupport.create(Inter.class);
        support.addListener(new One());
        support.addListener(new Two());
        support.fire().test();
    }

    @Listener
    public interface Inter{
        void test();
    }

    class One implements Inter {

        @Override
        @Order(afterClass = Two.class)
        public void test() {
            System.err.println("one");
        }
    }

    class Two implements Inter{
        @Override
        public void test() {
            System.err.println("two");
        }
    }
}
