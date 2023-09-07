package com.xiaohunao.rebirthhourglass;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaohunao.rebirthhourglass.inventory.*;
import com.xiaohunao.rebirthhourglass.item.RebirthHourglassItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;
import java.util.Map;

@Mod(RebirthHourglass.MOD_ID)
public class RebirthHourglass {
    public static final String MOD_ID = "rebirth_hourglass";

    public static final Map<String,IInventory> INVENTORIES = Maps.newHashMap();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB.location(), MOD_ID);

    public static final RegistryObject<Item> REBIRTH_HOURGLASS = ITEMS.register("rebirth_hourglass", RebirthHourglassItem::new);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",
            () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + MOD_ID))
                    .icon(() -> REBIRTH_HOURGLASS.get().getDefaultInstance())
                    .displayItems((params, output) -> ITEMS.getEntries()
                            .forEach(item -> output.accept(item.get().getDefaultInstance()))).build());

    public RebirthHourglass() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        registerInventory();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    //registerInventory
    public static void registerInventory(String type,IInventory inventory) {
        INVENTORIES.put(type,inventory);
    }

    public static void registerInventory() {
        registerInventory(VanillaInventory.ID,new VanillaInventory());
        registerInventory(RebirthHourglassInventory.ID,new RebirthHourglassInventory());
        registerInventory(EXPInventory.ID,new EXPInventory());
        if (ModList.get().isLoaded("curios")){
            registerInventory(CuriosInventory.ID,new CuriosInventory());
        }
    }


}
