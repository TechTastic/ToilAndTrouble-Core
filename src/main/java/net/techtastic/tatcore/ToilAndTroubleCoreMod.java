package net.techtastic.tatcore;

import net.fabricmc.api.ModInitializer;
import net.techtastic.tatcore.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToilAndTroubleCoreMod implements ModInitializer {
	public static final String MOD_ID = "tatcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}
