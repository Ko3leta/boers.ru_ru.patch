package net.foxy.boers.util;

import net.foxy.boers.BoersMod;
import net.foxy.boers.base.ModDataComponents;
import net.foxy.boers.base.ModItems;
import net.foxy.boers.data.BoerHead;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Utils {
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(BoersMod.MODID, path);
    }


    public static ItemStack boer(Holder<BoerHead> head) {
        ItemStack boer = ModItems.BOER_HEAD.toStack();
        boer.set(ModDataComponents.BOER, head);
        return boer;
    }

    public static BoerHead getBoer(ItemStack stack) {
        Holder<BoerHead> boerHead = getBoerHolder(stack);

        return boerHead == null ? null : boerHead.value();
    }

    public static Holder<BoerHead> getBoerHolder(ItemStack stack) {

        return stack.get(ModDataComponents.BOER.get());
    }
}
