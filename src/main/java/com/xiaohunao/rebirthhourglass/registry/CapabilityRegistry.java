package com.xiaohunao.rebirthhourglass.registry;

import com.xiaohunao.rebirthhourglass.capability.PlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.RebirthHourglass;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityRegistry {
    public static final Capability<PlayerRebirthCapability> PLAYER_REBIRTH = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(PlayerRebirthCapability.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player player) {
            event.addCapability(RebirthHourglass.asResource("rebirth"), new PlayerRebirthCapability(player));
        }
    }
}