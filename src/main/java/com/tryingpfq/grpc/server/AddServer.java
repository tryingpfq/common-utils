package com.tryingpfq.grpc.server;

import com.tryingpfq.grpc.AddReply;
import com.tryingpfq.grpc.AddRequest;
import com.tryingpfq.grpc.AddServiceGrpc;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class AddServer extends AddServiceGrpc.AddServiceImplBase {

    public static void main(String[] args) throws IOException {
        ServerBuilder.forPort(8080)
                .addService(new AddServer())
                .build()
                .start();
        System.err.println("serverStart");
        System.in.read();
    }

    @Override
    public void add(AddRequest request, StreamObserver<AddReply> responseObserver) {
        responseObserver.onNext(AddReply.newBuilder().setRes(request.getA() + request.getB()).build());
        responseObserver.onCompleted();
    }

}
