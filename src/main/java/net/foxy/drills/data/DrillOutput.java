package net.foxy.drills.data;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface DrillOutput {
    void accept(ResourceLocation id, DrillHead drill, ICondition... conditions);

    default DrillOutput withConditions(ICondition... conditions) {
        return new ConditionalDrillOutput(this, conditions);
    }
}
