package net.petersil98.utilcraft_world.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1.0";
    private static int id = 0;
    private static SimpleChannel INSTANCE;

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(UtilcraftWorld.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
    }

    public static <PACKET> void sendToServer(PACKET myPacket){
        INSTANCE.sendToServer(myPacket);
    }

    public static <PACKET> void sendToClients(PACKET myPacket, PlayerEntity affectedEntity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> affectedEntity), myPacket);
    }
}
