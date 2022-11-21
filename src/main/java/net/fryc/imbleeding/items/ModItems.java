package net.fryc.imbleeding.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.items.custom.BandageItem;
import net.fryc.imbleeding.items.custom.BalmItem;
import net.fryc.imbleeding.items.custom.StickyBandageItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item BANDAGE = registerItem("bandage" ,
            new BandageItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MISC)));
    public static final Item HONEY_BANDAGE = registerItem("honey_bandage" ,
            new BandageItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MISC)));
    public static final Item STICKY_BANDAGE = registerItem("sticky_bandage" ,
            new StickyBandageItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MISC)));

    public static final Item HERBAL_BALM = registerItem("herbal_balm" ,
            new BalmItem(new FabricItemSettings().maxCount(1).group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(ImBleeding.MOD_ID, name), item);
    }
    public static void registerModItems(){
        ImBleeding.LOGGER.info("Registering Mod Items for " + ImBleeding.MOD_ID);
    }
}
