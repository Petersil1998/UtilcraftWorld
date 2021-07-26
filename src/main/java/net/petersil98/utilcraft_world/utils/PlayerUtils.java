package net.petersil98.utilcraft_world.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.TicketType;

public class PlayerUtils {

    public static void teleportToWorld(ServerPlayer player, ResourceKey<Level> world) {
        teleportToWorld(player, world, player.blockPosition(), false);
    }

    public static void teleportToWorld(ServerPlayer player, ResourceKey<Level> world, BlockPos position, boolean shouldCheckHeight) {
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
                height = player.level.getServer().getLevel(world).getHeightmapPos(Heightmap.Types.WORLD_SURFACE, position).getY();
            }
            player.teleportTo(player.level.getServer().getLevel(world), position.getX(), height, position.getZ(), 0, 0);
            player.setYHeadRot(0);

            if (!player.isFallFlying()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
                player.setOnGround(true);
            }
        }
    }

    public static boolean isPlayerInWorld(ServerPlayer player, ResourceKey<Level> world) {
        return player.getServer().getLevel(world).equals(player.getLevel());
    }
}
