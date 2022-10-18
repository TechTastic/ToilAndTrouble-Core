package net.techtastic.tatcore.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<CastIronOvenScreenHandler> CAST_IRON_OVEN_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        CAST_IRON_OVEN_SCREEN_HANDLER = new ScreenHandlerType<>(CastIronOvenScreenHandler::new);
    }
}
