package net.petersil98.utilcraft_world.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import org.jetbrains.annotations.NotNull;

import static net.petersil98.utilcraft_world.utils.PlayerUtils.*;

public class Switcher extends Item {

    public Switcher(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        if(!world.isClientSide) {
            if(isPlayerInWorld((ServerPlayerEntity) player, World.OVERWORLD)) {
                player.abilities.instabuild = true;
                teleportToWorld((ServerPlayerEntity) player, UtilcraftWorld.AFTERLIFE_WORLD);
            } else if(isPlayerInWorld((ServerPlayerEntity) player, UtilcraftWorld.AFTERLIFE_WORLD)) {
                player.abilities.instabuild = true;
                teleportToWorld((ServerPlayerEntity) player, World.OVERWORLD);
            }
        }
        return super.use(world, player, hand);
    }
}
