package com.beckadam.morenightmobs.handlers;

import com.beckadam.morenightmobs.MoreNightMobs;
import com.beckadam.morenightmobs.config.Config;
import com.beckadam.morenightmobs.util.MatchEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = MoreNightMobs.MODID)
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
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
