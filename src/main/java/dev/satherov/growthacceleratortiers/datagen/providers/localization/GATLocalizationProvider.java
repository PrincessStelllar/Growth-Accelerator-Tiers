package dev.satherov.growthacceleratortiers.datagen.providers.localization;

import dev.satherov.growthacceleratortiers.core.annotations.NothingNull;
import dev.satherov.growthacceleratortiers.core.definitions.GATBlocks;
import dev.satherov.growthacceleratortiers.core.definitions.GATItems;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import appeng.datagen.providers.IAE2DataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

@NothingNull
public class GATLocalizationProvider implements IAE2DataProvider {

    private final Map<String, String> localizations = new HashMap<>();

    private final DataGenerator generator;

    private boolean wasSaved = false;

    public GATLocalizationProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {

        add("itemGroup.growthacceleratortiers", "Growth Accelerator Tiers");

        for (var block : GATBlocks.getBlocks()) {
            add("block.growthacceleratortiers." + block.id().getPath(), block.getEnglishName());
        }
        for (var item : GATItems.getItems()) {
            add("item.growthacceleratortiers." + item.id().getPath(), item.getEnglishName());
        }


        add("key.growthacceleratortiers.modifier", "Modifier");
        add("key.growthacceleratortiers.categories", "Growth Accelerator Tiers");

        add("tooltip.growthacceleratortiers.cranked_growth_accelerator", "Does not connect to a network, only works when powered by the Wooden Crank");
        add("tooltip.growthacceleratortiers.boosted_growth_accelerator", "Faster than your average Growth Accelerator");
        add("tooltip.growthacceleratortiers.directional_growth_accelerator", "Forces buds to grow in a specific direction, up by default");
        add("tooltip.growthacceleratortiers.change_direction", "Right click with a directional modifier to change the direction");

        add("tooltip.growthacceleratortiers.conflicted", "Accelerator conflict");

        add("direction.growthacceleratortiers", "Direction");
        
        add("direction.growthacceleratortiers.up", "Up");
        add("direction.growthacceleratortiers.down", "Down");
        add("direction.growthacceleratortiers.north", "North");
        add("direction.growthacceleratortiers.south", "South");
        add("direction.growthacceleratortiers.east", "East");
        add("direction.growthacceleratortiers.west", "West");
        add("direction.growthacceleratortiers.opposite", "Opposite");

        add("config.jade.plugin_growthacceleratortiers.boosted_growth_accelerator", "Boosted Growth Accelerator");
        add("config.jade.plugin_growthacceleratortiers.directional_growth_accelerator", "Directional Growth Accelerator");

        return save(cache, localizations);
    }

    public void add(String key, String text) {
        Preconditions.checkState(!wasSaved, "Cannot add more translations after they were already saved");
        var previous = localizations.put(key, text);
        if (previous != null) {
            throw new IllegalStateException("Localization key " + key + " is already translated to: " + previous);
        }
    }

    private CompletableFuture<?> save(CachedOutput cache, Map<String, String> localizations) {
        wasSaved = true;

        var path = this.generator.getPackOutput().getOutputFolder().resolve("assets/growthacceleratortiers/lang/en_us.json");

        var sorted = new TreeMap<>(localizations);
        var jsonLocalization = new JsonObject();
        for (var entry : sorted.entrySet()) {
            jsonLocalization.addProperty(entry.getKey(), entry.getValue());
        }

        return DataProvider.saveStable(cache, jsonLocalization, path);
    }

    @Override
    public String getName() {
        return "GAT Localization (en_us)";
    }
}
