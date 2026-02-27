package com.beckadam.moremobsaboveground.handlers;

import com.beckadam.moremobsaboveground.MoreMobsAboveGround;
import com.beckadam.moremobsaboveground.config.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = MoreMobsAboveGround.MODID)
public class LivingSpawnEventHandler {
    @SubscribeEvent
    public static void LivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawnReason() == MobSpawnType.NATURAL && event.getEntity() != null) {
            LivingEntity entity = event.getEntity();
            String type = entity.getType().toString();
            if (!Config.mobsToRegulate.contains(type)) {
                if (Config.enableDebugLogging) MoreMobsAboveGround.LOGGER.info("Ignoring mob spawn of type %s".formatted(type));
                return;
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
                if (Config.enableDebugLogging)
                    MoreMobsAboveGround.LOGGER.info("Denying mob spawn of type %s at %f,%f,%f".formatted(type, entity.getX(), entity.getY(), entity.getZ()));
                event.setResult(Event.Result.DENY);
            } else {
                if (Config.enableDebugLogging)
                    MoreMobsAboveGround.LOGGER.info("Allowing mob spawn of type %s at %f,%f,%f".formatted(type, entity.getX(), entity.getY(), entity.getZ()));
            }
        }
    }
}
