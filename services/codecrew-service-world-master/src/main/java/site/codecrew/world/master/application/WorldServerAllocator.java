package site.codecrew.world.master.application;

import site.codecrew.world.master.domain.ServerNode;


public interface WorldServerAllocator {
    ServerNode allocateNewServer();
}
