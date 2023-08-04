package com.xiaohunao.rebirthhourglass.capability;

import com.xiaohunao.rebirthhourglass.registry.CapabilityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlayerRebirthCapability implements ICapabilitySerializable<CompoundTag>, IPlayerRebirthCapability{
    private static BlockPos pos = BlockPos.ZERO;
    private static int time = 0;

    private static boolean isTeleport;
    private static Map<String, Tag> storageInventory = new HashMap<>();
    private final Player player;

    public PlayerRebirthCapability(Player player) {
        this.player = player;
    }



    @Override
    public void addInventory(String modid, Tag nbt) {
        this.storageInventory.put(modid, nbt);
    }

    @Override
    public Tag getInventory(String modid) {
        return this.storageInventory.get(modid);
    }
    @Override
    public void clearInventory() {
        this.storageInventory.clear();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean isIsTeleport() {
        return this.isTeleport;
    }

    @Override
    public void setIsTeleport(boolean isTeleport) {
        this.isTeleport = isTeleport;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityRegistry.PLAYER_REBIRTH.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("pos", NbtUtils.writeBlockPos(this.getPos()));
        compoundTag.putInt("time", this.getTime());
        compoundTag.putBoolean("isTeleport", this.isIsTeleport());
        CompoundTag inventory = new CompoundTag();
        for (Map.Entry<String, Tag> entry : this.storageInventory.entrySet()) {
            inventory.put(entry.getKey(), entry.getValue());
        }
        compoundTag.put("inventory", inventory);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.setPos(NbtUtils.readBlockPos(nbt.getCompound("pos")));
        this.setTime(nbt.getInt("time"));
        this.setIsTeleport(nbt.getBoolean("isTeleport"));
        CompoundTag inventory = nbt.getCompound("inventory");
        for (String key : inventory.getAllKeys()) {
            this.storageInventory.put(key, inventory.get(key));
        }
    }
}
