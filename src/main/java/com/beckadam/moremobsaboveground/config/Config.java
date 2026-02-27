package com.beckadam.moremobsaboveground.config;


import com.beckadam.moremobsaboveground.MoreMobsAboveGround;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MoreMobsAboveGround.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DEBUG_LOGGING =
            BUILDER.comment("Enable debug logging")
                    .define("enable_debug_logging", false);

    private static final ForgeConfigSpec.ConfigValue<List<String>> MOBS_TO_REGULATE =
            BUILDER.comment("Mobs that should be affected by spawn restrictions")
                    .define("mobs_to_regulate", new ArrayList<>());

    private static final ForgeConfigSpec.ConfigValue<List<String>> MOB_TAGS_TO_REGULATE =
            BUILDER.comment("Mob tags that should be affected by spawn restrictions")
                    .define("mob_tags_to_regulate", new ArrayList<>());

    private static final ForgeConfigSpec.ConfigValue<Double> MAX_DISTANCE =
            BUILDER.comment("Maximum 3D distance at which to allow spawning listed mobs (0 to disable)")
                    .define("max_distance", 0.0);

    private static final ForgeConfigSpec.ConfigValue<Double> MAX_DISTANCE_Y =
            BUILDER.comment("Maximum vertical distance at which to allow spawning listed mobs (0 to disable)")
                    .define("max_distance_y", 0.0);

    private static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_UNDERGROUND_WHEN_ABOVE_GROUND =
            BUILDER.comment("Whether or not to allow spawning listed mobs underground if the player is above ground.")
                    .define("spawn_underground_when_above_ground", false);

    public static boolean enableDebugLogging;
    public static final Set<EntityType<?>> mobsToRegulate = new HashSet<>();
    public static final List<String> mobTagsToRegulate = new ArrayList<>();
    public static double maxDistance;
    public static double maxDistanceY;
    public static boolean spawnUndergroundWhenAboveGround;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableDebugLogging = ENABLE_DEBUG_LOGGING.get();
        mobsToRegulate.clear();
        for (String mob : MOBS_TO_REGULATE.get()) {
            mobsToRegulate.add(ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.parse(mob)));
        }
        mobTagsToRegulate.clear();
        mobTagsToRegulate.addAll(MOB_TAGS_TO_REGULATE.get());
        maxDistance = MAX_DISTANCE.get();
        maxDistanceY = MAX_DISTANCE_Y.get();
        spawnUndergroundWhenAboveGround = SPAWN_UNDERGROUND_WHEN_ABOVE_GROUND.get();
    }
}
