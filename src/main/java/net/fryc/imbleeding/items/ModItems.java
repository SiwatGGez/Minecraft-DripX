package net.fryc.imbleeding.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryc.imbleeding.ImBleeding;
import net.fryc.imbleeding.items.custom.BalmItem;
import net.fryc.imbleeding.items.custom.BandageItem;
import net.fryc.imbleeding.items.custom.SoakedBandageItem;
import net.fryc.imbleeding.items.custom.SplintItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.*;
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

    public static final Item HERBAL_BALM = registerItem("herbal_balm" ,// todo tekstury, modele i t p
            new BalmItem(new FabricItemSettings().maxCount(1)));

    public static final Item SPLINT = registerItem("splint" ,
            new SplintItem(new FabricItemSettings().maxCount(16)));

    public static final Item SOAKED_BANDAGE = registerItem("soaked_bandage" , // todo receptury, lang, config
            new SoakedBandageItem(new FabricItemSettings().maxCount(64)));

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
                    entries.add(ModItems.SPLINT);
                    enabledFeatures.lookup().getOptionalWrapper(RegistryKeys.POTION).ifPresent((wrapper) -> {
                        addPotions(entries, wrapper, ModItems.SOAKED_BANDAGE, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
                    });
                })
                .build());

    }

    private static void addPotions(ItemGroup.Entries entries, RegistryWrapper<Potion> registryWrapper, Item item, ItemGroup.StackVisibility visibility) {
        registryWrapper.streamEntries().filter((entry) -> {
            return !entry.matchesKey(Potions.EMPTY_KEY);
        }).map((entry) -> {
            return PotionUtil.setPotion(new ItemStack(item), (Potion)entry.value());
        }).forEach((stack) -> {
            entries.add(stack, visibility);
        });
    }
}
