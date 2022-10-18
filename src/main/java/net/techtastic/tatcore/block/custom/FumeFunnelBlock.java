package net.techtastic.tatcore.block.custom;

import net.minecraft.block.Block;
import net.techtastic.tatcore.util.OvenFilter;

public class FumeFunnelBlock extends Block implements OvenFilter {
    public FumeFunnelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float getChance() {
        return 0.15f;
    }
}
