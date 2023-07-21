package com.github.romangraef.skyblockplayersanonymous.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "addToSendQueue", cancellable = true, at = @At("HEAD"))
    public void onAddtoSendQueue(Packet p_147297_1_, CallbackInfo ci) {
        if (p_147297_1_ instanceof C01PacketChatMessage) {
            String message = ((C01PacketChatMessage) p_147297_1_).getMessage();
            if (message != null && (message.equalsIgnoreCase("/play sb") || message.equalsIgnoreCase("/play skyblock") || message.equalsIgnoreCase("/skyblock"))) {
                ci.cancel();
            }
        }
    }
}
