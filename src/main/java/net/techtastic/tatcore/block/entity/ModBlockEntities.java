package net.techtastic.tatcore.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.techtastic.tatcore.ToilAndTroubleCoreMod;
import net.techtastic.tatcore.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<CastIronOvenBlockEntity> CAST_IRON_OVEN_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        CAST_IRON_OVEN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ToilAndTroubleCoreMod.MOD_ID, "cast_iron_oven"),
                FabricBlockEntityTypeBuilder.create(CastIronOvenBlockEntity::new, ModBlocks.CAST_IRON_OVEN).build(null));
    }
}
