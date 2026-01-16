package site.codecrew.world.infrastructure;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.world.application.event.DomainEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventListenerProcessor implements BeanPostProcessor {

    private final EventBus eventBus;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(DomainEventListener.class)) {
                Class<?> eventType = method.getParameterTypes()[0];
                eventBus.on(eventType)
                    .flatMap(event -> invokeHandler(bean, method, event)
                        .onErrorResume(e -> {
                            log.error("[EventBus] Handler error: {}", e.getMessage(), e);
                            return Mono.empty();
                        })
                    )
                    .subscribe();
            }
        }
        return bean;
    }

    private Mono<Void> invokeHandler(Object bean, Method method, Object event) {
        try {
            Object result = method.invoke(bean, event);
            if (result == null) {
                return Mono.empty();
            }
            return (Mono<Void>) result;
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}