package com.xiaohunao.rebirthhourglass.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public interface IPlayerRebirthCapability {
    void addInventory(String modid, Tag nbt);

    Tag getInventory(String modid);

    void clearInventory();

    Map<String, Tag> getStorageInventory();

    void setStorageInventory(Map<String, Tag> storageInventory);

    boolean isTeleport();

    void setTeleport(boolean teleport);

    Player getPlayer();

    BlockPos getPos();

    void setPos(BlockPos pos);

    int getTime();

    void setTime(int time);
}
