package net.techtastic.tatcore.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class MutandisItem extends Item {
    public MutandisItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }
}
