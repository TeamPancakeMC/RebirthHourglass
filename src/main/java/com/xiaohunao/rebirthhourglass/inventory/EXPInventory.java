package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.Config;
import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.player.Player;

public class EXPInventory implements IInventory{
    public static final String ID = "exp";
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        if (player == null) return;
        int totalExperience = player.totalExperience;

        IntTag intTag = IntTag.valueOf((int) (totalExperience * Config.getExp()));
        deathInfo.addInventory(ID, intTag);
    }

    @Override
    public void Load(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        IntTag exp = (IntTag) deathInfo.getInventory(ID);
        if (exp == null || player == null) return;
        player.giveExperiencePoints(exp.getAsInt());
    }
}
