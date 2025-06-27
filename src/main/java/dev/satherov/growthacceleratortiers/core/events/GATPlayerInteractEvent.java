package dev.satherov.growthacceleratortiers.core.events;

import dev.satherov.growthacceleratortiers.block.GATDirectionalGrowthAcceleratorBlock;
import dev.satherov.growthacceleratortiers.item.GATDirectionalModifier;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class GATPlayerInteractEvent {

    @SubscribeEvent
    public static void onPlayerUseBlockEvent(PlayerInteractEvent.RightClickBlock event) {

        if (event.getEntity().getMainHandItem().getItem() instanceof GATDirectionalModifier) {

            if (event.getLevel() instanceof ServerLevel level && event.getEntity() instanceof ServerPlayer player) {

                if (level.getBlockState(event.getPos()).getBlock() instanceof GATDirectionalGrowthAcceleratorBlock) {

                    BlockPos pos = event.getPos();
                    BlockState state = level.getBlockState(pos);

                    GATDirectionalGrowthAcceleratorBlock.Directions currentDir = state.getValue(GATDirectionalGrowthAcceleratorBlock.DIRECTION);
                    GATDirectionalGrowthAcceleratorBlock.Directions nextDir = getNextDirection(currentDir);

                    level.setBlock(pos, state.setValue(GATDirectionalGrowthAcceleratorBlock.DIRECTION, nextDir), 3);
                    player.displayClientMessage(Component.literal(getName(nextDir)).withStyle(ChatFormatting.AQUA), true);
                }
            }
        }
    }

    private static GATDirectionalGrowthAcceleratorBlock.Directions getNextDirection(GATDirectionalGrowthAcceleratorBlock.Directions current) {
        GATDirectionalGrowthAcceleratorBlock.Directions[] values = GATDirectionalGrowthAcceleratorBlock.Directions.values();
        int nextIndex = (current.ordinal() + 1) % values.length;
        return values[nextIndex];
    }


    private static String getName(GATDirectionalGrowthAcceleratorBlock.Directions dir) {
        String name = dir.name().toLowerCase(Locale.ROOT);
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
