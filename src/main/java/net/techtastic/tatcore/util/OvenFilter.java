package net.techtastic.tatcore.util;

public interface OvenFilter {
    default float getChance() {
        return 0.0f;
    }
}
