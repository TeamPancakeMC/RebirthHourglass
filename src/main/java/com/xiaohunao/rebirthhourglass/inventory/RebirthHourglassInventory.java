package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.item.RebirthHourglassItem;
import com.xiaohunao.rebirthhourglass.registry.CapabilityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RebirthHourglassInventory implements IInventory{
    private static final String ID = "rebirthhourglass";
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        Player player = deathInfo.getPlayer();
        if (player == null) return;
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof RebirthHourglassItem hourglassItem) {
                deathInfo.addInventory(ID, itemStack.save(new CompoundTag()));
            }
        });
    }

    @Override
    public void Load(IPlayerRebirthCapability newDeathInfo, IPlayerRebirthCapability oldDeathInfo) {
        Player player = newDeathInfo.getPlayer();
        if (player == null) return;
        CompoundTag compoundTag = (CompoundTag) oldDeathInfo.getInventory(ID);
        if (compoundTag == null) return;
        player.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(cap ->{
            Tag vanilla = cap.getInventory("vanilla");
            if(vanilla == null) {
                for (ItemStack itemStack : player.getInventory().items) {
                    if (itemStack.isEmpty()) {
                        ItemStack itemStack1 = ItemStack.of(compoundTag);
                        player.addItem(itemStack1);
                        break;
                    }
                }
            }
        });
        oldDeathInfo.addInventory(ID, null);
    }
}
