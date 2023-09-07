package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.item.RebirthHourglassItem;
import com.xiaohunao.rebirthhourglass.registry.CapabilityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RebirthHourglassInventory implements IInventory{
    public static final String ID = "rebirth_hourglass";
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        if (player == null) return;
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof RebirthHourglassItem) {
                deathInfo.addInventory(ID, itemStack.save(new CompoundTag()));
            }
        });
    }

    @Override
    public void Load(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        CompoundTag compoundTag = (CompoundTag) deathInfo.getInventory(ID);
        if (compoundTag == null || player == null) return;
        player.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(cap ->{
            if(cap.getInventory(VanillaInventory.ID) == null) {
                for (ItemStack itemStack : player.getInventory().items) {
                    if (itemStack.isEmpty()) {
                        ItemStack itemStack1 = ItemStack.of(compoundTag);
                        player.addItem(itemStack1);
                        break;
                    }
                }
            }
        });
        deathInfo.addInventory(ID, null);
    }
}
