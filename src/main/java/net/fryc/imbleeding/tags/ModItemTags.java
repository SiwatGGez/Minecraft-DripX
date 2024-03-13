package net.fryc.imbleeding.tags;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModItemTags {

    public static TagKey<Item> TULIPS = TagKey.of(RegistryKeys.ITEM, new Identifier(ImBleeding.MOD_ID, "tulips"));
    public static TagKey<Item> BANDAGES = TagKey.of(RegistryKeys.ITEM, new Identifier(ImBleeding.MOD_ID, "bandages"));


    private ModItemTags(){
    }
    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ImBleeding.MOD_ID, id));
    }
}
