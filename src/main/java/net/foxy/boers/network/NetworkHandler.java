package net.foxy.boers.network;

import net.foxy.boers.BoersMod;
import net.foxy.boers.network.c2s.SetUseBoerPacket;
import net.foxy.boers.network.c2s.TickBoerPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = BoersMod.MODID)
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "3";

    @SubscribeEvent
    public static void onRegisterPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToServer(
                TickBoerPacket.TYPE,
                        TickBoerPacket.STREAM_CODEC,
                        TickBoerPacket::handle)
                .playToServer(
                        SetUseBoerPacket.TYPE,
                        SetUseBoerPacket.STREAM_CODEC,
                        SetUseBoerPacket::handle);
    }
}
