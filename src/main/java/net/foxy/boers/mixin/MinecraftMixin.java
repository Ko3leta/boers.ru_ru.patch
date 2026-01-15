package net.foxy.boers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.logging.LogUtils;
import net.foxy.boers.base.ModItems;
import net.foxy.boers.client.BoersClientConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Final
    public Options options;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @ModifyExpressionValue(
            method = "handleKeybinds()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;consumeClick()Z", ordinal = 13)
    )
    public boolean breakWithUseKey(boolean original) {
        if (BoersClientConfig.CONFIG.BREAK_WITH_USE_KEY.get() && player.getMainHandItem().is(ModItems.BOER_BASE) && options.keyUse.consumeClick()) {
            return true;
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "handleKeybinds()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z", ordinal = 4)
    )
    public boolean continueBreakWithUseKey(boolean original) {
        if (BoersClientConfig.CONFIG.BREAK_WITH_USE_KEY.get() && player.getMainHandItem().is(ModItems.BOER_BASE) && options.keyUse.isDown()) {
            return true;
        }

        return original;
    }
}
