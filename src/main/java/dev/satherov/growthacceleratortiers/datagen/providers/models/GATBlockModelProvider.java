package dev.satherov.growthacceleratortiers.datagen.providers.models;

import dev.satherov.growthacceleratortiers.api.GAT;
import dev.satherov.growthacceleratortiers.block.GATGrowthAcceleratorBlock;
import dev.satherov.growthacceleratortiers.core.definitions.GATBlocks;

import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;

import appeng.core.definitions.BlockDefinition;
import appeng.datagen.providers.models.AE2BlockStateProvider;

public class GATBlockModelProvider extends AE2BlockStateProvider {

    public GATBlockModelProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, GAT.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        boostedGrowthAccelerator();
        crankedGrowthAccelerator();
        monoGrowthAccelerator();
    }

    private String modelPath(BlockDefinition<?> block) {
        return block.id().getPath();
    }

    private void boostedGrowthAccelerator() {
        var unpoweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.BOOSTED_GROWTH_ACCELERATOR),
                GAT.rl("block/boosted/growth_accelerator_side"),
                GAT.rl("block/boosted/growth_accelerator_bottom"),
                GAT.rl("block/boosted/growth_accelerator_top"));
        var poweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.BOOSTED_GROWTH_ACCELERATOR) + "_on",
                GAT.rl("block/boosted/growth_accelerator_side_on"),
                GAT.rl("block/boosted/growth_accelerator_bottom"),
                GAT.rl("block/boosted/growth_accelerator_top_on"));

        multiVariantGenerator(GATBlocks.BOOSTED_GROWTH_ACCELERATOR)
                .with(createFacingDispatch(90, 0))
                .with(PropertyDispatch.property(GATGrowthAcceleratorBlock.POWERED)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, unpoweredModel.getLocation()))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, poweredModel.getLocation())));

        itemModels().withExistingParent(modelPath(GATBlocks.BOOSTED_GROWTH_ACCELERATOR), unpoweredModel.getLocation());
    }

    private void crankedGrowthAccelerator() {
        var unpoweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.CRANKED_GROWTH_ACCELERATOR),
                GAT.rl("block/cranked/growth_accelerator_side"),
                GAT.rl("block/cranked/growth_accelerator_bottom"),
                GAT.rl("block/cranked/growth_accelerator_top"));
        var poweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.CRANKED_GROWTH_ACCELERATOR) + "_on",
                GAT.rl("block/cranked/growth_accelerator_side_on"),
                GAT.rl("block/cranked/growth_accelerator_bottom"),
                GAT.rl("block/cranked/growth_accelerator_top_on"));

        multiVariantGenerator(GATBlocks.CRANKED_GROWTH_ACCELERATOR)
                .with(createFacingDispatch(90, 0))
                .with(PropertyDispatch.property(GATGrowthAcceleratorBlock.POWERED)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, unpoweredModel.getLocation()))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, poweredModel.getLocation())));

        itemModels().withExistingParent(modelPath(GATBlocks.CRANKED_GROWTH_ACCELERATOR), unpoweredModel.getLocation());
    }

    private void monoGrowthAccelerator() {
        var unpoweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.DIRECTIONAL_GROWTH_ACCELERATOR),
                GAT.rl("block/mono/growth_accelerator_side"),
                GAT.rl("block/mono/growth_accelerator_bottom"),
                GAT.rl("block/mono/growth_accelerator_top"));
        var poweredModel = models().cubeBottomTop(
                modelPath(GATBlocks.DIRECTIONAL_GROWTH_ACCELERATOR) + "_on",
                GAT.rl("block/mono/growth_accelerator_side_on"),
                GAT.rl("block/mono/growth_accelerator_bottom"),
                GAT.rl("block/mono/growth_accelerator_top_on"));

        multiVariantGenerator(GATBlocks.DIRECTIONAL_GROWTH_ACCELERATOR)
                .with(createFacingDispatch(90, 0))
                .with(PropertyDispatch.property(GATGrowthAcceleratorBlock.POWERED)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, unpoweredModel.getLocation()))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, poweredModel.getLocation())));

        itemModels().withExistingParent(modelPath(GATBlocks.DIRECTIONAL_GROWTH_ACCELERATOR), unpoweredModel.getLocation());
    }
}
