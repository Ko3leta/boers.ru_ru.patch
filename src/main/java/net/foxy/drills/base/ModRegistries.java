package net.foxy.drills.base;

import net.foxy.drills.data.DrillHead;
import net.foxy.drills.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModRegistries {
    public static final ResourceKey<Registry<DrillHead>> DRILL_HEAD = ResourceKey.createRegistryKey(Utils.rl("drill_head"));
}
