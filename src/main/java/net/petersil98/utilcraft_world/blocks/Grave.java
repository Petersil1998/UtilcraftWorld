package net.petersil98.utilcraft_world.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class Grave extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    protected static final VoxelShape EAST_SHAPE = Block.box(16.0D, 0.0D, 2.0D, 15.0D, 16.0D, 14.0D);
    protected static final VoxelShape NORTH_SHAPE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 1.0D);
    protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 2.0D, 1.0D, 16.0D, 14.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(2.0D, 0.0D, 16.0D, 14.0D, 16.0D, 15.0D);

    public Grave() {
        super(AbstractBlock.Properties
                .of(Material.STONE, MaterialColor.STONE)
                .noOcclusion()
                .noDrops()
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nonnull
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        Direction sideSlabType = state.getValue(FACING);
        switch(sideSlabType) {
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return NORTH_SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    protected boolean isValidGround(BlockState state) {
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT);
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Override
    @NotNull
    public BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull IWorld world, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull IWorldReader world, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return this.isValidGround(world.getBlockState(blockpos));
    }
}
