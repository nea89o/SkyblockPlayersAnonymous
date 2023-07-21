package com.github.romangraef.skyblockplayersanonymous.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerController {

    @Inject(method = "windowClick", cancellable = true, at = @At("HEAD"))
    public void onClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> cir) {
        if (playerIn == null || playerIn.openContainer == null) return;
        Slot slot = playerIn.openContainer.getSlot(slotId);
        if (slot == null) {
            return;
        }
        ItemStack stack = slot.getStack();
        if (stack == null) {
            return;
        }
        if (stack.getItem() == Items.skull && stack.getDisplayName().startsWith("§aSkyBlock")) {
            cir.setReturnValue(null);
        }
    }
}
