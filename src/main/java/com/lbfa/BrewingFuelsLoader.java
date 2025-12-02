package com.lbfa;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.Reader;
import java.util.Map;

public class BrewingFuelsLoader implements SimpleSynchronousResourceReloadListener {

    public static final Identifier ID = Identifier.of(LotusBrewingFuelsAPI.MOD_ID, "brewing_fuels_loader");

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        BrewingFuelsRegistry.clear();

        Map<Identifier, Resource> resources =
                manager.findResources("brewing_fuels", path -> path.getPath().endsWith(".json"));

        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();

            try (Reader reader = resource.getReader()) {

                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray fuels = json.getAsJsonArray("brewing_fuels");

                for (JsonElement e : fuels) {
                    JsonObject fuelObj = e.getAsJsonObject();

                    String itemId = fuelObj.get("item").getAsString();
                    int fuelValue = fuelObj.get("fuel").getAsInt();

                    Item item = Registries.ITEM.get(Identifier.of(itemId));
                    BrewingFuelsRegistry.registerFuel(item, fuelValue);
                }

            } catch (Exception ex) {
                System.err.println("Error reading brewing fuel json: " + id + " - " + ex);
            }
        }
    }
}