package net.techtastic.tatcore.block.custom;

import net.minecraft.block.Block;
import net.techtastic.tatcore.util.OvenFilter;

public class FilteredFumeFunnelBlock extends Block implements OvenFilter {
    public FilteredFumeFunnelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float getChance() {
        return 0.30f;
    }
}
