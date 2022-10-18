package net.techtastic.tatcore;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.techtastic.tatcore.item.ModItems;
import net.techtastic.tatcore.screen.CastIronOvenScreen;
import net.techtastic.tatcore.screen.ModScreenHandlers;

public class ToilAndTroubleCoreModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ////////////////////////////////////////////////////
        // REGISTERING CUSTOM MODEL PREDICATE FOR TAGLOCK //
        ////////////////////////////////////////////////////
        ModelPredicateProviderRegistry.register(ModItems.TAGLOCK_KIT, new Identifier("full"), (itemStack, clientWorld, livingEntity, i) -> {
            ItemStack stack = itemStack.copy();
            NbtCompound nbt = stack.getOrCreateSubNbt("TATCore$tagged");
            if (nbt.contains("TATCore$full")) {
                return nbt.getFloat("TATCore$full");
            } else {
                return 0.0f;
            }
        });

        /////////////////////////
        // REGISTERING SCREENS //
        /////////////////////////
        HandledScreens.register(ModScreenHandlers.CAST_IRON_OVEN_SCREEN_HANDLER, CastIronOvenScreen::new);
    }
}
