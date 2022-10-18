package net.techtastic.tatcore.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.techtastic.tatcore.ToilAndTroubleCoreMod;
import net.techtastic.tatcore.item.custom.*;

public class ModItems {
    public static final ItemGroup group = ItemGroup.MISC;

    public static final Item CLAY_JAR = registerItem("clay_jar",
            new Item(new FabricItemSettings().group(group)));
    public static final Item RAW_CLAY_JAR = registerItem("raw_clay_jar",
            new Item(new FabricItemSettings().group(group)));
    public static final Item BREATH_OF_THE_GODDESS = registerItem("breath_of_the_goddess",
            new Item(new FabricItemSettings().group(group)));
    public static final Item EXHALE_OF_THE_HORNED_ONE = registerItem("exhale_of_the_horned_one",
            new Item(new FabricItemSettings().group(group)));
    public static final Item HINT_OF_REBIRTH = registerItem("hint_of_rebirth",
            new Item(new FabricItemSettings().group(group)));
    public static final Item FOUL_FUME = registerItem("foul_fume",
            new Item(new FabricItemSettings().group(group)));

    public static final Item MUTANDIS = registerItem("mutandis",
            new MutandisItem(new FabricItemSettings().group(group)));
    public static final Item MUTANDIS_EXTREMIS = registerItem("mutandis_extremis",
            new MutandisExtremisItem(new FabricItemSettings().group(group)));
    public static final Item WOOD_ASH = registerItem("wood_ash",
            new MutandisItem(new FabricItemSettings().group(group)));
    public static final Item BONE_NEEDLE = registerItem("bone_needle",
            new MutandisExtremisItem(new FabricItemSettings().group(group)));
    public static final Item ATTUNED_STONE = registerItem("attuned_stone",
            new MutandisExtremisItem(new FabricItemSettings().group(group)));
    public static final Item CHARGED_ATTUNED_STONE = registerItem("charged_attuned_stone",
            new MutandisExtremisItem(new FabricItemSettings().group(group)));

    public static final Item TAGLOCK_KIT = registerItem("taglock_kit",
            new TaglockItem(new FabricItemSettings().group(group)));
    public static final Item KEY = registerItem("key",
            new KeyItem(new FabricItemSettings().group(group)));
    public static final Item KEY_RING = registerItem("key_ring",
            new KeyRingItem(new FabricItemSettings().group(group)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ToilAndTroubleCoreMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ToilAndTroubleCoreMod.LOGGER.info("Registering Items for " + ToilAndTroubleCoreMod.MOD_ID + "!");
    }
}
