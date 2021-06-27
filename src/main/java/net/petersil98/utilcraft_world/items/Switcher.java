package net.petersil98.utilcraft_world.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.TicketType;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import org.jetbrains.annotations.NotNull;

public class Switcher extends Item {

    public Switcher() {
        super(new Item.Properties().group(UtilcraftWorld.ITEM_GROUP));
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        if(!world.isRemote) {
            if(isPlayerInWorld((ServerPlayerEntity) player, World.OVERWORLD)) {
                player.abilities.isCreativeMode = true;
                teleportToWorld((ServerPlayerEntity) player, UtilcraftWorld.AFTERLIFE_WORLD);
            } else if(isPlayerInWorld((ServerPlayerEntity) player, UtilcraftWorld.AFTERLIFE_WORLD)) {
                player.abilities.isCreativeMode = true;
                teleportToWorld((ServerPlayerEntity) player, World.OVERWORLD);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private boolean isPlayerInWorld(ServerPlayerEntity player, RegistryKey<World> world) {
        return player.getServer().getWorld(world).equals(player.getServerWorld());
    }

    private void teleportToWorld(ServerPlayerEntity player, RegistryKey<World> world) {
        ChunkPos chunkpos = new ChunkPos(player.getPosition());
        player.getServerWorld().getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkpos, 1, player.getEntityId());
        player.stopRiding();
        if (player.isSleeping()) {
            player.stopSleepInBed(true, true);
        }
        player.teleport(player.world.getServer().getWorld(world), player.getPosition().getX(), 100, player.getPosition().getZ(), 0, 0);

        player.setRotationYawHead(0);

        if (!player.isElytraFlying()) {
            player.setMotion(player.getMotion().mul(1.0D, 0.0D, 1.0D));
            player.setOnGround(true);
        }
    }
}
