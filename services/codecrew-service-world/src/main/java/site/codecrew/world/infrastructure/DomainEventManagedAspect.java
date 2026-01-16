package site.codecrew.world.infrastructure;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import site.codecrew.r2dbc.jpa.AggregateRoot;

@Aspect
@Component
@RequiredArgsConstructor
public class DomainEventManagedAspect {

    private final EventBus eventBus;

    @Around("@annotation(site.codecrew.world.domain.DomainEventManaged)")
    public Object publish(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        
        if (result instanceof Mono<?> mono) {
            return mono.doOnSuccess(obj -> {
                if (obj instanceof AggregateRoot agg) {
                    agg.flushEvents().forEach(eventBus::publish);
                }
            });
        }
        return result;
    }
}