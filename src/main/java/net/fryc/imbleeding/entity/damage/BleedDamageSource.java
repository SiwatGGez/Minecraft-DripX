package net.fryc.imbleeding.entity.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;

public class BleedDamageSource extends DamageSource {
    protected BleedDamageSource() {
        super("bleed");
        this.setBypassesArmor();
        this.setUnblockable();
    }

    public static DamageSource bleed() {
        return new BleedDamageSource();
    }

    public Text getDeathMessage(LivingEntity entity) {
        String text = entity.getEntityName();
        return Text.translatable(text+" bled out");
    }
}
