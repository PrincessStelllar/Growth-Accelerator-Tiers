package dev.satherov.growthacceleratortiers.blockentity;

import dev.satherov.growthacceleratortiers.core.GATConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import appeng.api.orientation.BlockOrientation;
import appeng.blockentity.misc.CrankBlockEntity;

public class GATDirectionalGrowthAcceleratrorBlockEntity extends GATGrowthAcceleratorBlockEntity {

    public static final int MAX_STORED_POWER = GATConfig.instance().getDirectionalInternalEnergyMultiplier() * CrankBlockEntity.POWER_PER_CRANK_TURN;
    public static final int POWER_PER_TICK = GATConfig.instance().getDirectionalIdlePowerConsumption();
    public static final double MULTIPLIER = GATConfig.instance().getDirectionalSpeedMultiplier();

    public GATDirectionalGrowthAcceleratrorBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(MAX_STORED_POWER, POWER_PER_TICK, MULTIPLIER, blockEntityType, pos, blockState);
        setPowerSides(getGridConnectableSides(getOrientation()));
    }

    @Override
    protected void onOrientationChanged(BlockOrientation orientation) {
        super.onOrientationChanged(orientation);
        setPowerSides(getGridConnectableSides(getOrientation()));
    }
}
