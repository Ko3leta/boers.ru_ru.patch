package net.foxy.drills.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import net.foxy.drills.base.ModRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.Map;

public class DrillManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, DrillHead> drillHeads = ImmutableMap.of();
    private static final Logger LOGGER = LogUtils.getLogger();

    public DrillManager() {
        super(GSON, Registries.elementsDirPath(ModRegistries.DRILL_HEAD));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> drills, ResourceManager resourceManager, ProfilerFiller profiler) {
        DynamicOps<JsonElement> ops = makeConditionalOps();
        ImmutableMap.Builder<ResourceLocation, DrillHead> builder = ImmutableMap.builder();
        drills.forEach((id, drill) -> {
            try {
                if (id.getPath().startsWith("_")) return;
                drill.getAsJsonObject().addProperty("id", id.toString());
                builder.put(id, DrillHead.CODEC.parse(ops, drill).getOrThrow(JsonParseException::new));
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading drill {}", id, jsonparseexception);
            }
        });

        drillHeads = builder.build();
    }

    public Map<ResourceLocation, DrillHead> getDrillHeads() {
        return drillHeads;
    }

    public DrillHead getDrillHead(ResourceLocation id) {
        return drillHeads.get(id);
    }
}
