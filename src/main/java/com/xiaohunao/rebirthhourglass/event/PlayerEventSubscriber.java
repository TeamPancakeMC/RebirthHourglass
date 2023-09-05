package com.xiaohunao.rebirthhourglass.event;

import com.xiaohunao.rebirthhourglass.Config;
import com.xiaohunao.rebirthhourglass.RebirthHourglass;
import com.xiaohunao.rebirthhourglass.inventory.IInventory;
import com.xiaohunao.rebirthhourglass.inventory.RebirthHourglassInventory;
import com.xiaohunao.rebirthhourglass.item.RebirthHourglassItem;
import com.xiaohunao.rebirthhourglass.registry.CapabilityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerEventSubscriber{
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;

        Level level = player.level();
        if (level.isClientSide()) return;

        BlockPos pos = player.blockPosition();
        int gameTime = (int) level.getGameTime() / 20;

       player.getInventory().items.forEach(itemStack -> {
           if (itemStack.getItem() instanceof RebirthHourglassItem hourglassItem) {
               player.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(cap ->{
                   if(hourglassItem.canUseNoDrop(itemStack)){
                       cap.setTime(gameTime);
                       cap.setPos(pos);
                       cap.setIsTeleport(true);
                       hourglassItem.consumeStoredTime(itemStack, Config.getDeath());

                       RebirthHourglass.INVENTORIES.forEach(iInventory -> {
                           if (!(iInventory instanceof RebirthHourglassInventory)) {
                               iInventory.Save(cap);
                           }
                       });
                       player.getInventory().clearContent();
                   }else {
                       RebirthHourglass.INVENTORIES.get(2).Save(cap);
                       player.getInventory().removeItem(itemStack);
                   }
               });

           }
       });


    }

    @SubscribeEvent
    public static void playerRespawn(final PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (event.isWasDeath()){
            Player original = event.getOriginal();
            original.revive();
            original.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(originalCap -> {
                player.getCapability(CapabilityRegistry.PLAYER_REBIRTH).ifPresent(cap -> {
                    for (IInventory iInventory : RebirthHourglass.INVENTORIES) {
                        iInventory.Load(cap, originalCap);
                    }
                    cap.clearInventory();
                });
            });
            original.remove(Entity.RemovalReason.DISCARDED);
        }
    }
}
