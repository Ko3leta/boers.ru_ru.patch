package net.foxy.drills.data;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.commons.lang3.ArrayUtils;

public class ConditionalDrillOutput implements DrillOutput {
    private final DrillOutput inner;
    private final ICondition[] conditions;

    public ConditionalDrillOutput(DrillOutput inner, ICondition[] conditions) {
        this.inner = inner;
        this.conditions = conditions;
    }

    @Override
    public void accept(ResourceLocation id, DrillHead recipe, ICondition... conditions) {
        ICondition[] innerConditions;
        if (conditions.length == 0) {
            innerConditions = this.conditions;
        } else if (this.conditions.length == 0) {
            innerConditions = conditions;
        } else {
            innerConditions = ArrayUtils.addAll(this.conditions, conditions);
        }
        inner.accept(id, recipe, innerConditions);
    }
}
