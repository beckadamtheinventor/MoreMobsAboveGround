package com.beckadam.moremobsaboveground.handlers;

import com.beckadam.moremobsaboveground.MoreMobsAboveGround;
import com.beckadam.moremobsaboveground.config.Config;
import com.beckadam.moremobsaboveground.util.MatchEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = MoreMobsAboveGround.MODID)
public class LivingSpawnEventHandler {
    @SubscribeEvent
    public static void LivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawnReason() == MobSpawnType.NATURAL) {
            Entity entity = event.getEntity();
            if (!MatchEntity.Type(entity.getType(), Config.mobsToRegulate)) {
                Set<String> tags = entity.getTags();
                if (!MatchEntity.Tags(tags, Config.mobTagsToRegulate)) {
                    return;
                }
            }
            boolean shouldSpawn = true;
            LevelAccessor level = event.getLevel();
            for (Player player : event.getLevel().players()) {
                if (Config.maxDistance > 0.0 && entity.distanceToSqr(player) >= Config.maxDistance) {
                    shouldSpawn = false;
                    break;
                }
                if (Config.maxDistanceY > 0.0 && Math.abs(entity.position().y - player.position().y) >= Config.maxDistance) {
                    shouldSpawn = false;
                    break;
                }
                if (!Config.spawnUndergroundWhenAboveGround && level.canSeeSky(player.blockPosition())) {
                    shouldSpawn = false;
                    break;
                }
            }
            if (!shouldSpawn) {
                if (Config.enableDebugLogging) MoreMobsAboveGround.LOGGER.debug("Denying mob spawn at %f,%f,%f".formatted(entity.getX(), entity.getY(), entity.getZ()));
                event.setResult(Event.Result.DENY);
            } else {
                if (Config.enableDebugLogging) MoreMobsAboveGround.LOGGER.debug("Allowing mob spawn at %f,%f,%f".formatted(entity.getX(), entity.getY(), entity.getZ()));
            }
        }
    }
}
