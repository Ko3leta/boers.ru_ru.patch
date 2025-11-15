package net.foxy.drills.data;

import com.google.common.collect.Sets;
import net.foxy.drills.base.ModRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DrillHeadsProvider implements DataProvider {
    protected final PackOutput.PathProvider drillPathProvider;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public DrillHeadsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.drillPathProvider = output.createRegistryElementsPathProvider(ModRegistries.DRILL_HEAD);
        this.registries = registries;
    }

    @Override
    public final CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose(p_323133_ -> this.run(output, p_323133_));
    }

    public CompletableFuture<?> run(CachedOutput output, final HolderLookup.Provider registries) {
        final Set<ResourceLocation> set = Sets.newHashSet();
        final List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildDrills(
                (p_312039_, p_312254_, conditions) -> {
                    if (!set.add(p_312039_)) {
                        throw new IllegalStateException("Duplicate recipe " + p_312039_);
                    } else {
                        list.add(DataProvider.saveStable(output, registries, DrillHead.CONDITIONAL_CODEC, Optional.of(new net.neoforged.neoforge.common.conditions.WithConditions<>(p_312254_, conditions)), DrillHeadsProvider.this.drillPathProvider.json(p_312039_)));
                    }
                }
        );
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }


    protected void buildDrills(DrillOutput output) {}

    @Override
    public String getName() {
        return "DrillHeads";
    }
}
