package com.tryingpfq.grpc.client;

import com.tryingpfq.grpc.AddReply;
import com.tryingpfq.grpc.AddRequest;
import com.tryingpfq.grpc.AddServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class AddClient {

    AddServiceGrpc.AddServiceBlockingStub stub;

    ManagedChannel channel;

    public AddClient(){
        channel = ManagedChannelBuilder.forAddress("127.0.0.1",8080)
                .usePlaintext()
                .build();
        stub = AddServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        AddClient addClient = new AddClient();

        AddReply replay = addClient.stub.add(AddRequest.newBuilder().setA(1).setB(2).build());
        System.err.println(replay.getRes());
    }
}
