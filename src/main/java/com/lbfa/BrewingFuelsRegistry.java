package com.lbfa;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class BrewingFuelsRegistry {

    private static final Map<Item, Integer> FUEL_VALUES = new HashMap<>();

    public static int getFuel(Item item) {
        return FUEL_VALUES.getOrDefault(item, 0);
    }

    public static boolean isFuel(Item item) {
        return FUEL_VALUES.containsKey(item);
    }

    public static void clear() {
        FUEL_VALUES.clear();
    }

    public static void registerFuel(Item item, int value) {
        if (value < 1 || value > 20) return;
        FUEL_VALUES.put(item, value);
    }
}