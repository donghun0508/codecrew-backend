package site.codecrew.world.master.application.entry;

import org.springframework.stereotype.Component;
import site.codecrew.world.master.application.WorldEntryContext;

@Component
class WorldEntryAvailabilityPolicy implements WorldEntryPolicy {

    @Override
    public void validate(WorldEntryContext entryContext) {
        entryContext.world().validateEntry();
    }
}
