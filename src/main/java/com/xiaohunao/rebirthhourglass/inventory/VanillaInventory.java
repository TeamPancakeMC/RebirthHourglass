package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.capability.PlayerRebirthCapability;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class VanillaInventory implements IInventory{
    public static final String ID = "vanilla";

    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        if (player == null) return;
        ListTag tags = new ListTag();
        Inventory inventory = player.getInventory();
        inventory.save(tags);
        deathInfo.addInventory(ID, tags);
    }

    @Override
    public void Load(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        ListTag listTag = (ListTag) deathInfo.getInventory(ID);
        if (listTag == null || player == null) return;
        player.getInventory().load(listTag);
    }
}
