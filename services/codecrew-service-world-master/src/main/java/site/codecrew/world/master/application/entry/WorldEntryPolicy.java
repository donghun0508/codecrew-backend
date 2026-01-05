package site.codecrew.world.master.application.entry;

import site.codecrew.world.master.application.WorldEntryContext;

public interface WorldEntryPolicy {

    void validate(WorldEntryContext entryContext);
}
