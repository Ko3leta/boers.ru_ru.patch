package net.foxy.drills.event;

import net.foxy.drills.DrillsMod;
import net.foxy.drills.data.DrillManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = DrillsMod.MODID)
public class ModEvents {
    public static final DrillManager DRILL_MANAGER = new DrillManager();

    @SubscribeEvent
    public static void registerDrillManager(AddReloadListenerEvent event) {
        event.addListener(DRILL_MANAGER);
    }

}
