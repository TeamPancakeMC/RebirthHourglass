package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.Config;
import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.player.Player;

public class EXPInventory implements IInventory{
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        if (player == null) return;
        int totalExperience = player.totalExperience;

        IntTag intTag = IntTag.valueOf((int) (totalExperience * Config.getExp()));
        deathInfo.addInventory("exp", intTag);
    }

    @Override
    public void Load(IPlayerRebirthCapability newDeathInfo, IPlayerRebirthCapability oldDeathInfo) {
        Player player = newDeathInfo.getPlayer();
        if (player == null) return;
        IntTag exp = (IntTag) oldDeathInfo.getInventory("exp");
        if (exp == null) return;
        player.giveExperiencePoints(exp.getAsInt());
    }
}
