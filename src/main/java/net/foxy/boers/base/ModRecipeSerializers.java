package net.foxy.boers.base;

import net.foxy.boers.BoersMod;
import net.foxy.boers.data.BoerColoring;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

    public static DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister
            .create(BuiltInRegistries.RECIPE_SERIALIZER, BoersMod.MODID);

    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BoerColoring>> BOER_COLORING = SERIALIZERS.register(
            "crafting_special_boercoloring", () -> new SimpleCraftingRecipeSerializer<>(BoerColoring::new));
}
