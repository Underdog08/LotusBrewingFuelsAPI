package com.lbfa.mixin;

import com.lbfa.BrewingFuelsRegistry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$FuelSlot")
public abstract class FuelSlotMixin {

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void lotus$injectCustomFuel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {

        if (BrewingFuelsRegistry.isFuel(stack.getItem())) {
            cir.setReturnValue(true);
        }
    }
}