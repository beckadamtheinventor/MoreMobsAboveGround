package com.beckadam.moremobsaboveground;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreMobsAboveGround.MODID)
public class MoreMobsAboveGround {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "moremobsaboveground";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public MoreMobsAboveGround() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
