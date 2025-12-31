package site.codecrew.world.master.application.entry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.WorldMember;

@RequiredArgsConstructor
@Component
public class WorldEntryPolicyChecker {

    private final List<WorldEntryPolicy> entryPolicies;

    public void checkPolicies(WorldMember worldMember) {
        for (WorldEntryPolicy entryPolicy : entryPolicies) {
            entryPolicy.validate(worldMember);
        }
    }
}
