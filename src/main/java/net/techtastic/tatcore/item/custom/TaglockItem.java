package net.techtastic.tatcore.item.custom;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.techtastic.tatcore.item.ModItems;
import org.jetbrains.annotations.Nullable;
import oshi.jna.platform.windows.NtDll;

import java.util.List;

public class TaglockItem extends Item {
    public TaglockItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateSubNbt("TATCore$tagged");
        if (nbt.isEmpty()) {
            return super.getName(stack);
        }

        return Text.translatable("item.tatcore.taglock_kit_filled");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Text name = Text.translatable("item.tatcore.taglock_kit.none");
        int countdown = 1;

        NbtCompound nbt = stack.getOrCreateSubNbt("TATCore$tagged");
        if (nbt.contains("TATCore$name")) {
            name = Text.of(nbt.getString("TATCore$name"));
        }

        if (nbt.contains("TATCore$countdown")) {
            countdown = nbt.getInt("TATCore$countdown");
        }

        if (countdown < 0) {
            tooltip.add(Text.translatable("item.tatcore.taglock_kit.unknown").formatted(Formatting.OBFUSCATED).formatted(Formatting.DARK_PURPLE));
        } else {
            tooltip.add(name.copy().formatted(Formatting.DARK_PURPLE));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        taglockEntity(stack, user, entity);

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getOrCreateSubNbt("TATCore$tagged");
        if (nbt.contains("TATCore$countdown")) {
            int countdown = nbt.getInt("TATCore$countdown");
            if (countdown >= 0) {
                nbt.putInt("TATCore$countdown", countdown - 1);
            } else {
                nbt.putFloat("TATCore$full", 0.5f);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }



    public static void taglockEntity(ItemStack stack, PlayerEntity user, LivingEntity entity) {
        stack.decrement(1);
        ItemStack tagged = new ItemStack(ModItems.TAGLOCK_KIT);
        NbtCompound nbt;

        if (entity instanceof PlayerEntity) {
            nbt = tagged.getOrCreateSubNbt("TATCore$profile");
            NbtHelper.writeGameProfile(nbt, user.getGameProfile());
        }
        nbt = tagged.getOrCreateSubNbt("TATCore$tagged");
        nbt.putString("TATCore$name", entity.getName().getString());
        nbt.putInt("TATCore$countdown", 240);
        nbt.putFloat("TATCore$full", 1.0f);

        user.giveItemStack(tagged);
    }
}