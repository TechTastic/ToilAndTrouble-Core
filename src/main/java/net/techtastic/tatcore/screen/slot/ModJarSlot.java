package net.techtastic.tatcore.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.techtastic.tatcore.item.ModItems;

public class ModJarSlot extends Slot {
    public ModJarSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem().equals(ModItems.CLAY_JAR);
    }
}
