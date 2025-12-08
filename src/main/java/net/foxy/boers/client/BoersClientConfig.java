package net.foxy.boers.client;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BoersClientConfig {
    public static final BoersClientConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue BREAKING_SOUNDS;

    private BoersClientConfig(ModConfigSpec.Builder builder) {
        BREAKING_SOUNDS = builder.comment("wip, will change").define("breaking_sounds", false);
    }

    static {
        Pair<BoersClientConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(BoersClientConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
