package net.techtastic.tatcore.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.techtastic.tatcore.ToilAndTroubleCoreMod;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ToilAndTroubleCoreMod.MOD_ID, CastIronOvenRecipe.Serializer.ID),
                CastIronOvenRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(ToilAndTroubleCoreMod.MOD_ID, CastIronOvenRecipe.Type.ID),
                CastIronOvenRecipe.Type.INSTANCE);
    }
}
