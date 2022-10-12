package net.techtastic.tatcore.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.techtastic.tatcore.ToilAndTroubleCoreMod;

public class ModItems {
    public static final Item CLAY_JAR = registerItem("clay_jar",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item RAW_CLAY_JAR = registerItem("raw_clay_jar",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ToilAndTroubleCoreMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ToilAndTroubleCoreMod.LOGGER.info("Registering Items for " + ToilAndTroubleCoreMod.MOD_ID + "!");
    }
}
