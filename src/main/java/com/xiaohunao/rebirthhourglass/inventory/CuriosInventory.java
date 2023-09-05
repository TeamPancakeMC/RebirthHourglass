package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.capability.PlayerRebirthCapability;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class CuriosInventory implements IInventory{
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {
        CuriosApi.getPlayerSlots().forEach((s, curioStackHandler) -> {
            LazyOptional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(deathInfo.getPlayer());
            curiosHandler.ifPresent(handler -> {
                ListTag tags = handler.saveInventory(true);
                System.out.println("save curios");
                System.out.println(tags);
                deathInfo.addInventory("curios", tags);
            });
        });
    }


    @Override
    public void Load(IPlayerRebirthCapability newDeathInfo, IPlayerRebirthCapability oldDeathInfo) {
        Player player = newDeathInfo.getPlayer();
        LazyOptional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
        curiosHandler.ifPresent(handler -> {
            ICuriosItemHandler helper = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            Tag curios = oldDeathInfo.getInventory("curios");
            System.out.println("load curios");
            System.out.println(curios);
            helper.loadInventory((ListTag) curios);
        });
    }
}
