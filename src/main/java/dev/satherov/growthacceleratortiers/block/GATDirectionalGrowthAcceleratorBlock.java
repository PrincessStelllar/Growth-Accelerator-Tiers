package dev.satherov.growthacceleratortiers.block;

import dev.satherov.growthacceleratortiers.blockentity.GATDirectionalGrowthAcceleratrorBlockEntity;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class GATDirectionalGrowthAcceleratorBlock extends GATGrowthAcceleratorBlock<GATDirectionalGrowthAcceleratrorBlockEntity> {

    public static final EnumProperty<Directions> DIRECTION = EnumProperty.create("direction", Directions.class);

    public GATDirectionalGrowthAcceleratorBlock() {
        this.registerDefaultState(this.defaultBlockState().setValue(DIRECTION, Directions.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
        super.createBlockStateDefinition(builder);
    }

    public enum Directions implements StringRepresentable {
        UP,
        DOWN,
        NORTH,
        SOUTH,
        EAST,
        WEST,
        OPPOSITE;

        public Direction getDirection(Direction facing) {
            return switch (this) {
                case DOWN -> Direction.DOWN;
                case NORTH -> Direction.NORTH;
                case SOUTH -> Direction.SOUTH;
                case EAST -> Direction.EAST;
                case WEST -> Direction.WEST;
                case OPPOSITE -> facing;
                default -> Direction.UP;
            };
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
