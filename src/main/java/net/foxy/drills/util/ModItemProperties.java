package net.foxy.drills.util;

import net.foxy.drills.DrillsMod;
import net.foxy.drills.base.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class ModItemProperties {
    public static void addModItemProperties() {
        ItemProperties.register(ModItems.DRILL_BASE.get(), Utils.rl("color"),
                (stack, level, entity, seed) -> {
                    int color = stack.getOrDefault(DataComponents.BASE_COLOR, DyeColor.BLUE).getId();
                    if (color == 11) {
                        return -1;
                    }
                    return color;
                });
    }

}
