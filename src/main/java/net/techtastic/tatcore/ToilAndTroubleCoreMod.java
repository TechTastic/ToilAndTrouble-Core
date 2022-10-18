package net.techtastic.tatcore;

import net.fabricmc.api.ModInitializer;
import net.techtastic.tatcore.block.ModBlocks;
import net.techtastic.tatcore.block.entity.ModBlockEntities;
import net.techtastic.tatcore.item.ModItems;
import net.techtastic.tatcore.recipe.ModRecipes;
import net.techtastic.tatcore.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ToilAndTroubleCoreMod implements ModInitializer {
	public static final String MOD_ID = "tatcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();
	}
}
