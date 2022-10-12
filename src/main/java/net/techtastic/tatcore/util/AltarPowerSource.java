package net.techtastic.tatcore.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface AltarPowerSource {
    default int getMaxPower() {
        return 1;
    }
    default int getCurrentPower() {
        return 0;
    }
    default void setCurrentPower(int power) {}

    default int getRange() {
        return 16;
    }
    default int getRate() {
        return 1;
    }

    default List<Integer> getAllAugments(World world, BlockPos pos) {
        return null;
    }

    default boolean isValid() {
        return false;
    }

    default boolean isMasterBlock() {
        return false;
    }
    default BlockPos getMasterBlock() {
        return null;
    }
}
