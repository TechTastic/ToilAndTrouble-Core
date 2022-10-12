package net.techtastic.tatcore.util;

public interface AltarAugment {
    default int getPowerIncrement() {
        return 0;
    }
    default int getPowerMultiplier() {
        return 1;
    }
    default int getRangeIncrement() {
        return 0;
    }
    default int getRangeMultiplier() {
        return 1;
    }
    default int getRateIncrement() {
        return 0;
    }
    default int getRateMultiplier() {
        return 1;
    }
}
