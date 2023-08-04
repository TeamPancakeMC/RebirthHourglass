package com.xiaohunao.rebirthhourglass;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RebirthHourglass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();



    private static final ForgeConfigSpec.IntValue MAX_STORED_TIME = BUILDER
            .defineInRange("max_stored_time", 24 * 60, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DEATH = BUILDER
            .defineInRange("death", 6 * 60, 0, Integer.MAX_VALUE);


    private static final ForgeConfigSpec.IntValue TELEPORT = BUILDER
            .defineInRange("teleport", 6 * 60, 0, Integer.MAX_VALUE);
    //Exp
    private static final ForgeConfigSpec.DoubleValue EXP = BUILDER
            .defineInRange("exp", 0.5, 0.0, Float.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int getMaxStoredTime() {
        return MAX_STORED_TIME.get();
    }

    public static int getDeath() {
        return DEATH.get();
    }

    public static int getTeleport() {
        return TELEPORT.get();
    }

    public static double getExp() {
        return EXP.get();
    }
}
