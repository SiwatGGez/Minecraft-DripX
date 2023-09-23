package net.fryc.imbleeding.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.items.custom.BandageItem;
import net.fryc.imbleeding.items.custom.BalmItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    //Item Group
    public static final RegistryKey<ItemGroup> HAEMORRHAGE = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ImBleeding.MOD_ID, "haemorrhage_item_group"));

    public static final Item BANDAGE = registerItem("bandage" ,
            new BandageItem(new FabricItemSettings().maxCount(16)));
    public static final Item HONEY_BANDAGE = registerItem("honey_bandage" ,
            new BandageItem(new FabricItemSettings().maxCount(16)));
    public static final Item STICKY_BANDAGE = registerItem("sticky_bandage" ,
            new BandageItem(new FabricItemSettings().maxCount(16)));

    public static final Item HERBAL_BALM = registerItem("herbal_balm" ,
            new BalmItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(ImBleeding.MOD_ID, name), item);
    }
    public static void registerModItems(){
        //Item Group
        Registry.register(Registries.ITEM_GROUP, HAEMORRHAGE, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.BANDAGE))
                .displayName(Text.literal("Haemorrhage"))
                .entries((enabledFeatures, entries) -> {
                    entries.add(ModItems.BANDAGE);
                    entries.add(ModItems.HONEY_BANDAGE);
                    entries.add(ModItems.STICKY_BANDAGE);
                    entries.add(ModItems.HERBAL_BALM);
                })
                .build());

    }
}
