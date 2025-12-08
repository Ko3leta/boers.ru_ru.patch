package net.foxy.drills.item;

import net.foxy.drills.data.DrillHead;
import net.foxy.drills.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DrillHeadItem extends Item {
    public DrillHeadItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        DrillHead head = Utils.getDrill(stack);
        return head != null ? head.durability() : 0;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        Holder<DrillHead> head = Utils.getDrillHolder(stack);

        if (head == null) {
            return super.getDescriptionId(stack);
        }

        return super.getDescriptionId(stack) + "." + head.getKey().location().toString().replace(":", ".");
    }
}
