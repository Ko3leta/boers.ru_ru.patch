package net.foxy.boers.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxy.boers.base.ModDataComponents;
import net.foxy.boers.base.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record BoerHead(Texture texture, float defaultMiningSpeed, int durability, List<Rule> miningRules) {
    public static final Codec<BoerHead> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Texture.CODEC.fieldOf("texture").forGetter(BoerHead::texture),
                    Codec.FLOAT.fieldOf("default_mining_speed").forGetter(BoerHead::defaultMiningSpeed),
                    Codec.INT.fieldOf("durability").forGetter(BoerHead::durability),
                    Rule.CODEC.listOf().fieldOf("mining_rules").forGetter(BoerHead::miningRules)
            ).apply(instance, BoerHead::new)
    );
    public static final Codec<Holder<BoerHead>> ITEM_CODEC = RegistryFixedCodec.create(ModRegistries.BOER_HEAD);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BoerHead>> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.BOER_HEAD);

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        for (Rule tool$rule : miningRules) {
            if (tool$rule.speed().isPresent() && state.is(tool$rule.blocks())) {
                if (tool$rule.maxSpeed.isPresent() && tool$rule.speedPerTick.isPresent()) {
                    return (Math.min(tool$rule.speed.get() + tool$rule.speedPerTick.get() * stack.getOrDefault(ModDataComponents.USED_FOR, 0), tool$rule.maxSpeed.get()));
                }
                return tool$rule.speed().get();
            }
        }

        return this.defaultMiningSpeed;
    }

    public boolean isCorrectForDrops(BlockState state) {
        for (Rule tool$rule : this.miningRules) {
            if (tool$rule.correctForDrops().isPresent() && state.is(tool$rule.blocks())) {
                return tool$rule.correctForDrops().get();
            }
        }

        return false;
    }
    public BoerHead(ResourceLocation texture, float miningSpeed, int durability, TagKey<Block> miningLevel) {
        this(new Texture(ResourceLocation.fromNamespaceAndPath(texture.getNamespace(),
                        texture.getPath() + "_idle"), texture), 1.0f, durability,
                List.of(Rule.deniesDrops(miningLevel),
                        Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, miningSpeed),
                        Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, miningSpeed)
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


    public static record Rule(HolderSet<Block> blocks, Optional<Float> speed, Optional<Float> maxSpeed, Optional<Float> speedPerTick, Optional<Boolean> correctForDrops) {
        public static final Codec<Rule> CODEC = RecordCodecBuilder.create(
                p_337954_ -> p_337954_.group(
                                RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(Rule::blocks),
                                ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("speed").forGetter(Rule::speed),
                                ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("max_speed").forGetter(Rule::maxSpeed),
                                ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("speed_per_tick").forGetter(Rule::speedPerTick),
                                Codec.BOOL.optionalFieldOf("correct_for_drops").forGetter(Rule::correctForDrops)
                        )
                        .apply(p_337954_, Rule::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, Rule> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderSet(Registries.BLOCK),
                Rule::blocks,
                ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional),
                Rule::speed,
                ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional),
                Rule::maxSpeed,
                ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional),
                Rule::speedPerTick,
                ByteBufCodecs.BOOL.apply(ByteBufCodecs::optional),
                Rule::correctForDrops,
                Rule::new
        );

        public static Rule minesAndDrops(List<Block> blocks, float speed) {
            return forBlocks(blocks, Optional.of(speed), Optional.empty(), Optional.empty(), Optional.of(true));
        }

        public static Rule minesAndDrops(TagKey<Block> blocks, float speed) {
            return forTag(blocks, Optional.of(speed), Optional.empty(), Optional.empty(), Optional.of(true));
        }

        public static Rule deniesDrops(TagKey<Block> blocks) {
            return forTag(blocks, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false));
        }

        public static Rule overrideSpeed(TagKey<Block> blocks, float speed) {
            return forTag(blocks, Optional.of(speed), Optional.empty(), Optional.empty(), Optional.empty());
        }

        public static Rule overrideSpeed(List<Block> blocks, float speed) {
            return forBlocks(blocks, Optional.of(speed), Optional.empty(), Optional.empty(), Optional.empty());
        }

        private static Rule forTag(TagKey<Block> tag, Optional<Float> speed, Optional<Float> maxSpeed, Optional<Float> maxSpeedPerTick, Optional<Boolean> correctForDrops) {
            return new Rule(BuiltInRegistries.BLOCK.getOrCreateTag(tag), speed, maxSpeed, maxSpeedPerTick, correctForDrops);
        }

        private static Rule forBlocks(List<Block> blocks, Optional<Float> speed, Optional<Float> maxSpeed, Optional<Float> maxSpeedPerTick, Optional<Boolean> correctForDrops) {
            return new Rule(HolderSet.direct(blocks.stream().map(Block::builtInRegistryHolder).collect(Collectors.toList())), speed, maxSpeed, maxSpeedPerTick, correctForDrops);
        }
    }
}
