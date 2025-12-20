package site.codecrew.data.redis.hash;

public interface RedisValueHasher {
    String hash(String value);
    boolean matches(String rawValue, String hashedValue);
}