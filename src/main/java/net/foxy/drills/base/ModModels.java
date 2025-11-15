package net.foxy.drills.base;

import net.foxy.drills.DrillsMod;
import net.foxy.drills.util.Utils;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = DrillsMod.MODID)
public final class ModModels {
    public static final ModelResourceLocation DRILL_BASE =
            ModelResourceLocation.standalone(Utils.rl("item/drill_base_texture"));
    public static final ModelResourceLocation DRILL_BASE_GUI =
            ModelResourceLocation.standalone(Utils.rl("item/drill_base_gui"));

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(DRILL_BASE);
        event.register(DRILL_BASE_GUI);
    }
}

