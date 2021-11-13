package com.tryingpfq.rpc.invoke;

import com.tryingpfq.rpc.factory.BeanManager;
import com.tryingpfq.rpc.protocol.Header;
import com.tryingpfq.rpc.protocol.Message;
import com.tryingpfq.rpc.protocol.Request;
import com.tryingpfq.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class InvokeRunnable implements Runnable {
    private ChannelHandlerContext handlerContext;

    private Message<Request> message;

    public InvokeRunnable(Message<Request> message, ChannelHandlerContext channelHandlerContext) {
        this.handlerContext = channelHandlerContext;
        this.message = message;
    }

    @Override
    public void run() {
        Response response = new Response();
        Object result = null;

        try {
            Request request = message.getContext();
            String serviceName = request.getServiceName();

            Object bean = BeanManager.getBean(serviceName);
            if (bean == null) {
                System.err.println("bean is null by serviceName " + serviceName);
                return;
            }
            Method method = bean.getClass().getMethod(request.getMethodName(), request.getArgTypes());
            result = method.invoke(bean, request.getArgs());
        } catch (Exception e) {

        }finally {

        }
        Header header = message.getHeader();
        header.setExtraInfo((byte) 1);
        response.setResult(result);
        handlerContext.writeAndFlush(new Message(header, response));
    }
}
