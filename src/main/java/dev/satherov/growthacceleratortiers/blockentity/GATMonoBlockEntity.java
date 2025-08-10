package dev.satherov.growthacceleratortiers.blockentity;

import dev.satherov.growthacceleratortiers.block.GATMonoBlock;
import dev.satherov.growthacceleratortiers.data.PositionAttachment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;


public abstract class GATMonoBlockEntity extends GATGrowthAcceleratorBlockEntity {
    
    private boolean cache = false;
    private int cooldown = 0;
    
    public GATMonoBlockEntity(int maxStoredPower, int powerPerTick, double multiplier, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(maxStoredPower, powerPerTick, multiplier, blockEntityType, pos, blockState);
    }

    @Override
    protected void onTick(int ticksSinceLastCall) {
        if (!(getLevel() instanceof ServerLevel level)) {
            if (!cache) super.onTick(ticksSinceLastCall);
            return;
        }
        
        boolean conflicted = cache;
        boolean check = false;
        
        if (cooldown > 0) {
            cooldown -= ticksSinceLastCall;
        }
        
        if (cooldown <= 0) {
            check = true;
            cooldown = 40;
        }
        
        if (check) {
            conflicted = false;
            
            for (Direction dir : Direction.values()) {
                BlockPos relative = getBlockPos().relative(dir).immutable();
                ChunkAccess chunk = level.getChunkAt(relative);
                if (getBlockState().getBlock() instanceof GATMonoBlock<?> block) {
                    PositionAttachment data = chunk.getData(block.getAttachmentType());
                    if (data.get(relative) > 1) {
                        conflicted = true;
                        break;
                    }
                }
            }
        }
        
        if (conflicted != getBlockState().getValue(GATMonoBlock.CONFLICTED)) {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(GATMonoBlock.CONFLICTED, conflicted));
        }
        
        cache = conflicted;
        
        if (!cache) super.onTick(ticksSinceLastCall);
    }
}
