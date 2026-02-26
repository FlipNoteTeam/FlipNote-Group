package flipnote.group.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    @Bean(destroyMethod = "shutdownNow")
    public ManagedChannel imageCommandChannel(
        @Value("${grpc.image.address:localhost:9092}") String target
    ) {
        return ManagedChannelBuilder.forTarget(target)
            .usePlaintext()
            .build();
    }

    @Bean
    public ImageCommandServiceGrpc.ImageCommandServiceBlockingStub imageCommandServiceStub(
        ManagedChannel imageCommandChannel
    ) {
        return ImageCommandServiceGrpc.newBlockingStub(imageCommandChannel);
    }
}
