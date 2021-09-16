package net.petersil98.utilcraft_world.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import org.jetbrains.annotations.NotNull;

import static net.petersil98.utilcraft_world.utils.PlayerUtils.*;

public class Switcher extends Item {

    public Switcher(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if(!world.isClientSide) {
            if(isPlayerInWorld((ServerPlayer) player, Level.OVERWORLD)) {
                player.getAbilities().instabuild = true;
                teleportToWorld((ServerPlayer) player, UtilcraftWorld.AFTERLIFE_WORLD);
            } else if(isPlayerInWorld((ServerPlayer) player, UtilcraftWorld.AFTERLIFE_WORLD)) {
                player.getAbilities().instabuild = true;
                teleportToWorld((ServerPlayer) player, Level.OVERWORLD);
            }
        }
        return super.use(world, player, hand);
    }
}
