package net.foxy.drills.base;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class ModEnums {
    public static final EnumProxy<HumanoidModel.ArmPose> DRILL_STANDING_POS = new EnumProxy<>(
            HumanoidModel.ArmPose.class, true, (IArmPoseTransformer)
            ModEnums::applyPose
    );

    public static void applyPose(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        model.rightArm.xRot = 0;
        model.leftArm.xRot = (float) - Math.toRadians(35);
        model.rightArm.yRot = 0;
        model.leftArm.yRot = 0;
        model.rightArm.zRot = 0;
        model.leftArm.zRot = 0;
    }
}
