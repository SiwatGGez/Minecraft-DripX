package net.fryc.imbleeding.tags;

import net.fryc.imbleeding.ImBleeding;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModDamageTypeTags {

    public static TagKey<DamageType> DAMAGE_APPLY_BLEED = register("damage_apply_bleed");


    private ModDamageTypeTags(){
    }
    private static TagKey<DamageType> register(String id) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ImBleeding.MOD_ID, id));
    }
}
