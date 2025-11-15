package net.foxy.drills.mixin;

import net.foxy.drills.base.ModDataComponents;
import net.foxy.drills.item.DrillContents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArg(
            method = "breakItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;spawnItemParticles(Lnet/minecraft/world/item/ItemStack;I)V"
            ),
            index = 0
    )
    private static ItemStack particles(ItemStack stack) {
        ItemStack drill = stack.getOrDefault(ModDataComponents.DRILL_CONTENTS, DrillContents.EMPTY).getItemUnsafe();
        if (!drill.isEmpty()) {
            return drill;
        }
        return stack;
    }
}
