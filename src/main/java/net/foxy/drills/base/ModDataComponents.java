package net.foxy.drills.base;

import net.foxy.drills.DrillsMod;
import net.foxy.drills.data.DrillHead;
import net.foxy.drills.item.DrillContents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DrillsMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DrillHead>> DRILL =
            COMPONENTS.registerComponentType("drill", builder -> builder.persistent(DrillHead.ITEM_CODEC)
                    .networkSynchronized(DrillHead.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> USED =
            COMPONENTS.registerComponentType("used", builder -> builder
                    .networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> BREAKING_POS =
            COMPONENTS.registerComponentType("breaking_pos", builder -> builder
                    .networkSynchronized(BlockPos.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> START_TICK =
            COMPONENTS.registerComponentType("start_tick", builder -> builder
                    .networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DrillContents>> DRILL_CONTENTS = COMPONENTS.registerComponentType(
            "drill_contents", builder -> builder.persistent(DrillContents.CODEC).networkSynchronized(DrillContents.STREAM_CODEC)
    );
}
