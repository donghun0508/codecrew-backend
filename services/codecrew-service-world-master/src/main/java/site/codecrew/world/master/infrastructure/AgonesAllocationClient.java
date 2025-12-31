package site.codecrew.world.master.infrastructure;

import site.codecrew.world.master.infrastructure.AgonesWorldServerAllocator.Allocation;

public interface AgonesAllocationClient {

    Allocation allocateForFleet(String fleetName);
}
