package net.fryc.imbleeding.items.custom;

import net.minecraft.item.ItemStack;

public class StickyBandageItem extends BandageItem{
    public StickyBandageItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 15;
    }
}
