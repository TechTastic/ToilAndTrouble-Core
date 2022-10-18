package net.techtastic.tatcore.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.techtastic.tatcore.ToilAndTroubleCoreMod;
import net.techtastic.tatcore.block.custom.CastIronOvenBlock;
import net.techtastic.tatcore.item.ModItems;

public class ModBlocks {
    public static final Block CAST_IRON_OVEN = registerBlock("cast_iron_oven",
            new CastIronOvenBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.0f)), ModItems.group);

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(ToilAndTroubleCoreMod.MOD_ID, name), block);
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(ToilAndTroubleCoreMod.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(ToilAndTroubleCoreMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }
    public static void registerModBlocks() {
        ToilAndTroubleCoreMod.LOGGER.info("Registering Blocks for " + ToilAndTroubleCoreMod.MOD_ID + "!");
    }
}
