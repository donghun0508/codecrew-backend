package site.codecrew.data.redis.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String key();
    long waitTime() default 5L;   // 락 획득 대기 시간 (seconds)
    long leaseTime() default 3L;  // 락 점유 유효 시간 (seconds)
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}