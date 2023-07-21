package com.github.romangraef.skyblockplayersanonymous;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(modid = "skyblockplayersanonymous", useMetadata = true)
public class SPA {

    public static boolean isSkyblockNpc(Entity entity) {
        if (entity == null) return false;
        if (!(entity instanceof EntityOtherPlayerMP)) return false;
        ItemStack heldItem = ((EntityOtherPlayerMP) entity).getHeldItem();
        return heldItem != null && heldItem.getDisplayName().equals("§bSkyBlock");
    }

    public static void warnPlayer() {
        Minecraft.getMinecraft().ingameGUI.getChatGUI()
                .printChatMessage(new ChatComponentText("§c§l§kaaa§c§l WARNING!!!! YOU TRIED TO JOIN SKYBLOCK!!!! §c§l§kaaa"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {
        if (tick.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null) {
            if (new Vec3(-175, 125, 118).squareDistanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) < 100) {
                Minecraft.getMinecraft().ingameGUI.getChatGUI()
                        .printChatMessage(new ChatComponentText("§c§l§kaaa§c§l WARNING!!!! YOU ARE DANGEROUSLY CLOSE TO THE PORTAL TO SKYBLOCK!!!! §c§l§kaaa"));
            }
        }
    }
}
