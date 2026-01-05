package site.codecrew.world.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import site.codecrew.world.adapter.web.WorldEnterWebSocketHandler;
import site.codecrew.world.constant.EndpointConstants;

@Configuration
@RequiredArgsConstructor
@EnableWebFlux
public class WebSocketConfig implements WebFluxConfigurer {

    @Bean
    public HandlerMapping webSocketMapping(WorldEnterWebSocketHandler worldEnterWebSocketHandler) {
        Map<String, WebSocketHandler> map = Map.of(EndpointConstants.WS_ENTRY, worldEnterWebSocketHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(1);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
