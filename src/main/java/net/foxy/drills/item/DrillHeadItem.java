package net.foxy.drills.item;

import net.foxy.drills.base.ModDataComponents;
import net.foxy.drills.data.DrillHead;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DrillHeadItem extends Item {
    public DrillHeadItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return stack.get(ModDataComponents.DRILL).durability();
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return super.getDescriptionId(stack) + stack.getOrDefault(ModDataComponents.DRILL, DrillHead.DEFAULT).id().toString().replace(":", ".");
    }
}
