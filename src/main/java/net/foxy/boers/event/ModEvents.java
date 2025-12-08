package net.foxy.boers.event;

import net.foxy.boers.BoersMod;
import net.foxy.boers.base.ModRegistries;
import net.foxy.boers.data.BoerHead;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = BoersMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void registerBoerHeadRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(ModRegistries.BOER_HEAD, BoerHead.CODEC, BoerHead.CODEC);
    }

}
