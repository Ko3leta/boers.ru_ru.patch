package net.foxy.boers.base;

import net.foxy.boers.BoersMod;
import net.foxy.boers.item.BoerContents;
import net.foxy.boers.util.Utils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, BoersMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEXTURE =
            TABS.register("texture",
                    () -> CreativeModeTab.builder().icon(() -> {
                        ItemStack itemStack = ModItems.BOER_BASE.toStack();
                        itemStack.set(ModDataComponents.BOER_CONTENTS, new BoerContents(ModItems.BOER_HEAD.toStack()));
                        return itemStack;
                            })
                            .title(Component.translatable("item.boers.boers"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.BOER_BASE);
                                pParameters.holders().lookupOrThrow(ModRegistries.BOER_HEAD).listElements().forEach(boerHeadReference -> {
                                    pOutput.accept(Utils.boer(boerHeadReference));
                                });
                            })
                            .build());
}
