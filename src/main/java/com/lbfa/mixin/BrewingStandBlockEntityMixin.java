package com.lbfa.mixin;

import com.lbfa.BrewingFuelsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {

    @Shadow
    private DefaultedList<ItemStack> inventory;

    @Shadow private int fuel;

    @Inject(method = "tick", at = @At("HEAD"))
    private static void lotus$injectFuelTick(
            World world,
            BlockPos pos,
            BlockState state,
            BrewingStandBlockEntity be,
            CallbackInfo ci
    ) {

        BrewingStandBlockEntityMixin self = (BrewingStandBlockEntityMixin) (Object) be;

        ItemStack fuelStack = self.inventory.get(4);

        if (self.fuel <= 0 && !fuelStack.isEmpty()) {

            Item item = fuelStack.getItem();

            if (BrewingFuelsRegistry.isFuel(item)) {
                int fuelValue = BrewingFuelsRegistry.getFuel(item);

                self.fuel = fuelValue;
                fuelStack.decrement(1);

                be.markDirty();
            }
        }
    }

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void lotus$allowCustomFuel(
            int slot,
            ItemStack stack,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (slot == 4 && BrewingFuelsRegistry.isFuel(stack.getItem())) {
            cir.setReturnValue(true);
        }
    }
}