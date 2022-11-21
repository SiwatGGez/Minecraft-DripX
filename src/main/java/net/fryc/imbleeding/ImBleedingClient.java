package net.fryc.imbleeding;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.imbleeding.items.custom.BalmItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ImBleedingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(new Identifier("balm"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof BalmItem ? 1.0F : 0.0F;
        });
    }
}
