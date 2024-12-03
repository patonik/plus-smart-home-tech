package ru.yandex.practicum.infra.analyzer.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

@Configuration
public class GrpcConfig {

    @GrpcClient("hub-router")
    private ManagedChannel hubRouterChannel;

    @Bean
    public HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterBlockingStub() {
        return HubRouterControllerGrpc.newBlockingStub(hubRouterChannel);
    }

    @Bean
    public ManagedChannel hubRouterChannel(GrpcChannelsProperties grpcChannelsProperties) {
        var clientConfig = grpcChannelsProperties.getClient().get("hub-router");
        return ManagedChannelBuilder
            .forAddress(clientConfig.getAddress().toString(), clientConfig.getAddress().getPort())
            .usePlaintext()
            .keepAliveWithoutCalls(true)
            .enableRetry()
            .build();
    }
}

