package net.techtastic.tatcore.recipe;

import com.google.gson.JsonObject;
import net.minecraft.client.render.Shader;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.awt.*;

public class CastIronOvenRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final ItemStack secondaryOutput;
    private final Ingredient input;

    public CastIronOvenRecipe(Identifier id, ItemStack output, ItemStack secondaryOutput, Ingredient input) {
        this.id = id;
        this.output = output;
        this.secondaryOutput = secondaryOutput;
        this.input = input;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }

        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    public ItemStack getSecondaryOutput() {
        return secondaryOutput.copy();
    }

    /*public ItemStack getInput() {
        return input.copy();
    }*/

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CastIronOvenRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "cast_iron_oven";
    }
    public static class Serializer implements RecipeSerializer<CastIronOvenRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "cast_iron_oven";

        @Override
        public CastIronOvenRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            ItemStack secondaryOutput = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "jar"));
            Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));

            return new CastIronOvenRecipe(id, output, secondaryOutput, input);
        }

        @Override
        public CastIronOvenRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack secondaryOutput = buf.readItemStack();
            ItemStack output = buf.readItemStack();

            return new CastIronOvenRecipe(id, output, secondaryOutput, input);
        }

        @Override
        public void write(PacketByteBuf buf, CastIronOvenRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.getSecondaryOutput());
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
