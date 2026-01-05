package site.codecrew.world.constant;

public class EndpointConstants {
    private EndpointConstants() {}

    public static final String WS_ENTRY = "/ws/world";

    public static final String[] AUTH_WHITELIST = {
        "/actuator/**",
        "/favicon.ico"
    };
}