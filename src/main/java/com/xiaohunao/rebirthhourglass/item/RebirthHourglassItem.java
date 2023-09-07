package com.xiaohunao.rebirthhourglass.item;

import com.xiaohunao.rebirthhourglass.Config;
import com.xiaohunao.rebirthhourglass.RebirthHourglass;
import com.xiaohunao.rebirthhourglass.registry.CapabilityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class RebirthHourglassItem extends Item {
    private static final int NODE = 5 * 60 ;
    public RebirthHourglassItem() {
        super(new Item.Properties().tab(RebirthHourglass.tab));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSame(oldStack, newStack);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(itemStack, level, entity, itemSlot, isSelected);
        if (level.isClientSide) {
            return;
        }


        if (level.getGameTime() % 20 == 0) {
            int storedTime = this.getStoredTime(itemStack);
            if (storedTime < Config.getMaxStoredTime()) {
                this.setStoredTime(itemStack, storedTime + 1);
            }else {
                this.setStoredTime(itemStack, Config.getMaxStoredTime());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, world, tooltip, flag);

        int storedTime = this.getStoredTime(itemStack);

        int hours = storedTime / 3600;
        int minutes = (storedTime % 3600) / 60;
        int seconds = storedTime % 60;

        tooltip.add(Component.translatable("tooltip.rebirth_hourglass.death_cd",hours,minutes,seconds));

    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemInHand = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player == null) {
            return InteractionResult.PASS;
        }

        player.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(cap -> {
            int storedTime = this.getStoredTime(itemInHand);
            if (storedTime > Config.getTeleport() ) {
                int teleportationCost = this.getTeleportationCost((int) (level.getGameTime() / 20 - cap.getTime()));
                BlockPos pos = cap.getPos();
                if(teleportationCost <= storedTime && cap.isIsTeleport()){
                    cap.setIsTeleport(false);
                    this.consumeStoredTime(itemInHand, teleportationCost);
                    player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                }

            }
        });
        return InteractionResult.SUCCESS;
    }

    public int getStoredTime(ItemStack stack) {
        return stack.getOrCreateTag().getInt("stored_time");
    }

    public void setStoredTime(ItemStack stack, int time) {
        stack.getOrCreateTag().putInt("stored_time", time);
    }

    public void consumeStoredTime(ItemStack stack, int time) {
        int storedTime = getStoredTime(stack);
        setStoredTime(stack, storedTime - time);
    }

    public boolean canUseNoDrop(ItemStack stack) {
        return getStoredTime(stack) >= Config.getDeath();
    }

    public int getTeleportationCost(int survivalTime) {
        int teleportCD = Config.getTeleport();
        int i = teleportCD - (teleportCD / NODE * survivalTime);
        if (i < 0) {
            i = 0;
        }
        return i;
    }

}
