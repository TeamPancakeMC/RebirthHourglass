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
    public static final String ID = "curios";
    @Override
    public void Save(IPlayerRebirthCapability deathInfo) {

    }


    @Override
    public void Load(IPlayerRebirthCapability deathInfo) {

    }
}
