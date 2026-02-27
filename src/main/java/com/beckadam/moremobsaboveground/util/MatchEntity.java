package com.beckadam.moremobsaboveground.util;

import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.Set;

public class MatchEntity {
    public static boolean Type(EntityType<?> ent, Set<EntityType<?>> list) {
        return list.contains(ent);
    }
    public static boolean Tags(Set<String> tags, List<String> list) {
        for (String tag : list) {
            if (tags.contains(tag)) {
                return true;
            }
        }
        return false;
    }
}
