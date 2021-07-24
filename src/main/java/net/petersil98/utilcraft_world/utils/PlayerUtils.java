package net.petersil98.utilcraft_world.utils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.TicketType;

public class PlayerUtils {

    public static void teleportToWorld(ServerPlayerEntity player, RegistryKey<World> world) {
        teleportToWorld(player, world, player.getPosition(), false);
    }

    public static void teleportToWorld(ServerPlayerEntity player, RegistryKey<World> world, BlockPos position, boolean shouldCheckHeight) {
        if(player.world.getServer() != null && player.world.getServer().getWorld(world) != null) {
            ChunkPos chunkpos = new ChunkPos(position);
            player.world.getServer().getWorld(world).getChunk(position);
            player.getServerWorld().getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkpos, 1, player.getEntityId());
            player.stopRiding();
            if (player.isSleeping()) {
                player.stopSleepInBed(true, true);
            }
            int height = position.getY();
            if (shouldCheckHeight) {
                height = player.world.getServer().getWorld(world).getHeight(Heightmap.Type.WORLD_SURFACE, position).getY();
            }
            player.teleport(player.world.getServer().getWorld(world), position.getX(), height, position.getZ(), 0, 0);
            player.setRotationYawHead(0);

            if (!player.isElytraFlying()) {
                player.setMotion(player.getMotion().mul(1.0D, 0.0D, 1.0D));
                player.setOnGround(true);
            }
        }
    }

    public static boolean isPlayerInWorld(ServerPlayerEntity player, RegistryKey<World> world) {
        return player.getServer().getWorld(world).equals(player.getServerWorld());
    }
}
