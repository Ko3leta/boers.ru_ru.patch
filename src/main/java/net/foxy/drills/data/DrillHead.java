package net.foxy.drills.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxy.drills.base.ModRegistries;
import net.foxy.drills.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public record DrillHead(Texture texture, float defaultMiningSpeed, int durability, List<Tool.Rule> miningRules) {
    public static final Codec<DrillHead> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Texture.CODEC.fieldOf("texture").forGetter(DrillHead::texture),
                    Codec.FLOAT.fieldOf("default_mining_speed").forGetter(DrillHead::defaultMiningSpeed),
                    Codec.INT.fieldOf("durability").forGetter(DrillHead::durability),
                    Tool.Rule.CODEC.listOf().fieldOf("mining_rules").forGetter(DrillHead::miningRules)
            ).apply(instance, DrillHead::new)
    );
    public static final Codec<Holder<DrillHead>> ITEM_CODEC = RegistryFixedCodec.create(ModRegistries.DRILL_HEAD);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DrillHead>> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.DRILL_HEAD);

    public float getMiningSpeed(BlockState state) {
        for (Tool.Rule tool$rule : miningRules) {
            if (tool$rule.speed().isPresent() && state.is(tool$rule.blocks())) {
                return tool$rule.speed().get();
            }
        }

        return this.defaultMiningSpeed;
    }

    public boolean isCorrectForDrops(BlockState state) {
        for (Tool.Rule tool$rule : this.miningRules) {
            if (tool$rule.correctForDrops().isPresent() && state.is(tool$rule.blocks())) {
                return tool$rule.correctForDrops().get();
            }
        }

        return false;
    }
    public DrillHead(ResourceLocation texture, float miningSpeed, int durability, TagKey<Block> miningLevel) {
        this(new Texture(ResourceLocation.fromNamespaceAndPath(texture.getNamespace(),
                        texture.getPath() + "_idle"), texture), 1.0f, durability,
                List.of(Tool.Rule.deniesDrops(miningLevel),
                        Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, miningSpeed),
                        Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, miningSpeed)
                ));
    }

    public record Texture(ResourceLocation idle, ResourceLocation active) {
        public static StreamCodec<FriendlyByteBuf, Texture> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                Texture::idle,
                ResourceLocation.STREAM_CODEC,
                Texture::active,
                Texture::new
        );

        public static final Codec<Texture> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("idle").forGetter(Texture::idle),
                        ResourceLocation.CODEC.fieldOf("active").forGetter(Texture::active)
                ).apply(instance, Texture::new));
    }
}
