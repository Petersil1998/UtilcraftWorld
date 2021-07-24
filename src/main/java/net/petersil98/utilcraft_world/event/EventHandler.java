package net.petersil98.utilcraft_world.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import net.petersil98.utilcraft_world.utils.PlayerUtils;

@Mod.EventBusSubscriber(modid = UtilcraftWorld.MOD_ID)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.PlayerRespawnEvent event) {
        if(event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            BlockPos respawnPoint = player.getRespawnPosition();
            if(respawnPoint == null) {
                respawnPoint = player.level.getServer().getLevel(World.OVERWORLD).getSharedSpawnPos();
            }
            PlayerUtils.teleportToWorld(player, UtilcraftWorld.AFTERLIFE_WORLD, respawnPoint, true);
        }
    }
}
