package net.foxy.drills.base;

import net.foxy.drills.data.DrillHead;
import net.foxy.drills.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModRegistries {
    public static final ResourceKey<Registry<DrillHead>> DRILL_HEAD = ResourceKey.createRegistryKey(Utils.rl("drill_head"));

    public static final ResourceKey<DrillHead> COPPER = ResourceKey.create(DRILL_HEAD, Utils.rl("copper"));
    public static final ResourceKey<DrillHead> DEFAULT = ResourceKey.create(DRILL_HEAD, Utils.rl("default"));
    public static final ResourceKey<DrillHead> IRON = ResourceKey.create(DRILL_HEAD, Utils.rl("iron"));
    public static final ResourceKey<DrillHead> DIAMOND = ResourceKey.create(DRILL_HEAD, Utils.rl("diamond"));
    public static final ResourceKey<DrillHead> NETHERITE = ResourceKey.create(DRILL_HEAD, Utils.rl("netherite"));
    public static final ResourceKey<DrillHead> GOLDEN = ResourceKey.create(DRILL_HEAD, Utils.rl("golden"));
}
