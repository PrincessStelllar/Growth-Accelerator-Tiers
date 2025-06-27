package dev.satherov.growthacceleratortiers.util;

import dev.satherov.growthacceleratortiers.block.GATDirectionalGrowthAcceleratorBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import org.spongepowered.asm.mixin.Unique;

public interface BuddingBlockGrowthHandler {

    @Unique
    default boolean growthAcceleratorTiers$checkForAccelerator(BlockState state, ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos checkPos = pos.relative(dir);
            BlockState checkState = level.getBlockState(checkPos);

            if (checkState.getBlock() instanceof GATDirectionalGrowthAcceleratorBlock) {
                Direction direction = checkState.getValue(GATDirectionalGrowthAcceleratorBlock.DIRECTION).getDirection(checkState.getValue(BlockStateProperties.FACING));
                BlockPos growthPos = pos.relative(direction);

                growthAcceleratorTiers$handleGrowth(level, pos, growthPos, direction);
                return true;
            }
        }
        return false;
    }

    @Unique
    void growthAcceleratorTiers$handleGrowth(ServerLevel level, BlockPos pos, BlockPos growthPos, Direction direction);
}