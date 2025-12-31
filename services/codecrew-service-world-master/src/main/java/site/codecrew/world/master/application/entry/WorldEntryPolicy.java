package site.codecrew.world.master.application.entry;

import site.codecrew.world.master.domain.WorldMember;

public interface WorldEntryPolicy {

    void validate(WorldMember worldMember);
}
