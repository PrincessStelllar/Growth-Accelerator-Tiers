package dev.satherov.growthacceleratortiers.mixins;

import dev.satherov.growthacceleratortiers.util.BuddingBlockGrowthHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import com.shynieke.geore.block.BuddingGeoreBlock;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;


@Mixin(BuddingGeoreBlock.class)
public class BuddingGeoreBlockMixin implements BuddingBlockGrowthHandler {


    @Shadow
    @Final
    private Supplier<? extends AmethystClusterBlock> smallSupplier;
    @Shadow
    @Final
    private Supplier<? extends AmethystClusterBlock> mediumSupplier;
    @Shadow
    @Final
    private Supplier<? extends AmethystClusterBlock> largeSupplier;
    @Shadow
    @Final
    private Supplier<? extends AmethystClusterBlock> clusterSupplier;

    @Inject(
            method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 0),
            cancellable = true
    )
    private void onRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (growthAcceleratorTiers$checkForAccelerator(state, level, pos, random)) {
            ci.cancel();
        }
    }

    @Override
    @Unique
    public void growthAcceleratorTiers$handleGrowth(ServerLevel level, BlockPos pos, BlockPos growthPos, Direction direction, RandomSource randomSource) {
        BlockState targetState = level.getBlockState(growthPos);
        Block newBlock = null;

        if (BuddingAmethystBlock.canClusterGrowAtState(targetState)) {
            newBlock = smallSupplier.get();
        } else if (targetState.is(smallSupplier.get()) &&
                targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            newBlock = mediumSupplier.get();
        } else if (targetState.is(mediumSupplier.get()) &&
                targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            newBlock = largeSupplier.get();
        } else if (targetState.is(largeSupplier.get()) &&
                targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            newBlock = clusterSupplier.get();
        }

        if (newBlock != null) {
            BlockState newState = newBlock.defaultBlockState()
                    .setValue(AmethystClusterBlock.FACING, direction)
                    .setValue(AmethystClusterBlock.WATERLOGGED, targetState.getFluidState().getType() == Fluids.WATER);
            level.setBlockAndUpdate(growthPos, newState);
        }
    }
}
