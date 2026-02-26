package flipnote.group.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ImageCommandServiceGrpc.ImageCommandServiceBlockingStub imageCommandServiceStub() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        return ImageCommandServiceGrpc.newBlockingStub(channel);
    }
}
