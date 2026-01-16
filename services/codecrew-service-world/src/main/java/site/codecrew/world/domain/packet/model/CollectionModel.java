package site.codecrew.world.domain.packet.model;

import java.util.List;

public record CollectionModel<T>(
    long totalCount,
    List<T> items
) {
    public static <T> CollectionModel<T> from(List<T> items) {
        if(items.isEmpty()) {
            return new CollectionModel<>(0, null);
        }
        return new CollectionModel<>(items.size(), items);
    }
}