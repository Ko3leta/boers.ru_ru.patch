package net.foxy.boers;

import net.foxy.boers.client.BoersClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = BoersMod.MODID, dist = Dist.CLIENT)
public class BoersModClient {

    public BoersModClient(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modContainer.registerConfig(ModConfig.Type.CLIENT, BoersClientConfig.CONFIG_SPEC);
    }
}
