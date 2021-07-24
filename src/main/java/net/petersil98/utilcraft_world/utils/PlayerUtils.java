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
        teleportToWorld(player, world, player.blockPosition(), false);
    }

    public static void teleportToWorld(ServerPlayerEntity player, RegistryKey<World> world, BlockPos position, boolean shouldCheckHeight) {
        if(player.level.getServer() != null && player.level.getServer().getLevel(world) != null) {
            ChunkPos chunkpos = new ChunkPos(position);
            player.level.getServer().getLevel(world).getChunk(position);
            player.getLevel().getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, player.getId());
            player.stopRiding();
            if (player.isSleeping()) {
                player.stopSleepInBed(true, true);
            }
            int height = position.getY();
            if (shouldCheckHeight) {
                height = player.level.getServer().getLevel(world).getHeightmapPos(Heightmap.Type.WORLD_SURFACE, position).getY();
            }
            player.teleportTo(player.level.getServer().getLevel(world), position.getX(), height, position.getZ(), 0, 0);
            player.setYHeadRot(0);

            if (!player.isFallFlying()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
                player.setOnGround(true);
            }
        }
    }

    public static boolean isPlayerInWorld(ServerPlayerEntity player, RegistryKey<World> world) {
        return player.getServer().getLevel(world).equals(player.getLevel());
    }
}
