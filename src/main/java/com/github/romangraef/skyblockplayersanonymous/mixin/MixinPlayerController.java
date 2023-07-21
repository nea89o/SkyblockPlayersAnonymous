package com.github.romangraef.skyblockplayersanonymous.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.romangraef.skyblockplayersanonymous.SPA.isSkyblockNpc;
import static com.github.romangraef.skyblockplayersanonymous.SPA.warnPlayer;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerController {

    @Inject(method = "attackEntity", cancellable = true, at = @At("HEAD"))
    public void onAttack(EntityPlayer playerIn, Entity targetEntity, CallbackInfo ci) {
        if (isSkyblockNpc(targetEntity)) {
            ci.cancel();
            warnPlayer();
        }
    }

    @Inject(method = "isPlayerRightClickingOnEntity", cancellable = true, at = @At("HEAD"))
    public void onRightClick(EntityPlayer player, Entity entityIn, MovingObjectPosition movingObject, CallbackInfoReturnable<Boolean> cir) {
        if (isSkyblockNpc(entityIn)) {
            cir.setReturnValue(false);
            warnPlayer();
        }
    }

    @Inject(method = "interactWithEntitySendPacket", cancellable = true, at = @At("HEAD"))
    public void interactWithEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfoReturnable<Boolean> cir) {
        if (isSkyblockNpc(targetEntity)) {
            cir.setReturnValue(false);
            warnPlayer();
        }
    }

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
            warnPlayer();
        }
    }
}
