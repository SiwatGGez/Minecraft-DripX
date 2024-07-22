package net.fryc.imbleeding.tags;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModItemTags {

    public static TagKey<Item> TULIPS = register( "tulips");
    public static TagKey<Item> BANDAGES = register( "bandages");
    public static TagKey<Item> ITEMS_REMOVE_BLEEDING = register( "items_remove_bleeding");
    public static TagKey<Item> ITEMS_REMOVE_HEALTH_LOSS = register( "items_remove_health_loss");
    public static TagKey<Item> ITEMS_REMOVE_BROKEN = register( "items_remove_broken");
    public static TagKey<Item> ITEMS_REMOVE_BLEEDOUT = register( "items_remove_bleedout");


    private ModItemTags(){
    }
    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ImBleeding.MOD_ID, id));
    }
}
