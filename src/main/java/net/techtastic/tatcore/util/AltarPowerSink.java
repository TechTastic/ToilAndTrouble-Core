package net.techtastic.tatcore.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

public interface AltarPowerSink {
    default BlockPos getCachedSource() {
        return null;
    }

    default BlockPos findNearestSource(World world, BlockPos sink) {
        BlockPos positiveCorner = new BlockPos(sink.getX() + 32, sink.getY() + 32, sink.getZ() + 32);
        BlockPos negativeCorner = new BlockPos(sink.getX() - 32, sink.getY() - 32, sink.getZ() - 32);
        BlockPos currentAltar = positiveCorner;

        //////////////////////////////////////////////////////
        // LOOPING THROUGH ALL BLOCKS BETWEEN posCorner AND //
        // negCorner TO FIND THE CLOSEST SOURCE             //
        //////////////////////////////////////////////////////
        for (BlockPos test : BlockPos.iterate(positiveCorner, negativeCorner)) {
            BlockEntity testEntity = world.getBlockEntity(test);
            if (testEntity != null) {
                if (testEntity instanceof AltarPowerSource) {
                    if (sink.getSquaredDistance(test) < sink.getSquaredDistance(currentAltar)) {
                        currentAltar = test;
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////
        // TESTING IF THE SINK IS WITHIN THE RANGE OF THE SOURCE //
        ///////////////////////////////////////////////////////////
        AltarPowerSource altar = (AltarPowerSource) world.getBlockEntity(currentAltar);
        if (sink.getSquaredDistance(currentAltar) <= altar.getRange()) {
            return currentAltar;
        } else {
            return null;
        }
    }

    default boolean drawPower(World world, BlockPos sink, int power) {
        BlockPos source = getCachedSource();
        if (source != null) {
            AltarPowerSource altar = (AltarPowerSource) world.getBlockEntity(source);
            if (altar.isValid() && altar.getCurrentPower() >= power) {
                altar.setCurrentPower(altar.getCurrentPower() - power);
                return true;
            }
        }
        return false;
    }
}
