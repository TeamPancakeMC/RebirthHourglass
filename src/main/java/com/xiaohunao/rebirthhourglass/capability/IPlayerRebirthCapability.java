package com.xiaohunao.rebirthhourglass.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

public interface IPlayerRebirthCapability {
    void addInventory(String modid, Tag nbt);

    Tag getInventory(String modid);

    void clearInventory();

    Player getPlayer();

    BlockPos getPos();

    void setPos(BlockPos pos);

    int getTime();

    void setTime(int time);

    boolean isIsTeleport();

    void setIsTeleport(boolean isTeleport);
}
