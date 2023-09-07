package com.xiaohunao.rebirthhourglass;

import com.google.common.collect.Lists;
import com.xiaohunao.rebirthhourglass.inventory.*;
import com.xiaohunao.rebirthhourglass.item.RebirthHourglassItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod(RebirthHourglass.MOD_ID)
public class RebirthHourglass {
    public static final String MOD_ID = "rebirth_hourglass";

    public static final List<IInventory> INVENTORIES = Lists.newArrayList();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> REBIRTH_HOURGLASS = ITEMS.register("rebirth_hourglass", RebirthHourglassItem::new);

    public static CreativeModeTab tab = new CreativeModeTab("tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(REBIRTH_HOURGLASS.get());
        }
    };

    public RebirthHourglass() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        registerInventory();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    //registerInventory
    public static void registerInventory(int inx,IInventory inventory) {
        INVENTORIES.add(inx,inventory);
    }
    public static void registerInventory(IInventory inventory) {
        INVENTORIES.add(inventory);
    }

    public static void registerInventory() {
        registerInventory(0,new VanillaInventory());
        registerInventory(1,new CuriosInventory());
        registerInventory(2,new RebirthHourglassInventory());
        registerInventory(3,new EXPInventory());
    }


}
