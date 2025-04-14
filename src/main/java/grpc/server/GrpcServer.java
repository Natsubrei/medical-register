package grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static final int PORT = 9999;

    public static void main(String[] args) throws Exception {
        // 创建一个gRPC服务端实例
        Server server = ServerBuilder.forPort(PORT)
                .addService(new AppointmentServiceImpl())
                .build()
                .start();

        System.out.println("🚀 gRPC Server started on port " + PORT);

        // 阻塞当前线程，直到服务器终止
        server.awaitTermination();
    }
}
