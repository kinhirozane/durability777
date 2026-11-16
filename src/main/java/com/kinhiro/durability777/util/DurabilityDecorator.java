/*
 * MIT License
 *
 * Copyright (c) 2025 Kinhiro Zane and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.kinhiro.durability777.util;

import com.kinhiro.durability777.Durability777;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.IItemDecorator;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public final class DurabilityDecorator implements IItemDecorator {
    private static @Nullable UnitDurabilityFormatter FORMATTER;

    @Override
    public boolean render(
        final GuiGraphicsExtractor graphics,
        final Font font,
        final ItemStack stack,
        final int xOffset,
        final int yOffset
    ) {
        if (!stack.isDamageableItem()) return false;
        if (!stack.isEmpty() && stack.isDamaged()) {
            final var level = Minecraft.getInstance().level;
            var unbreaking = 0;
            if (Minecraft.getInstance().player != null && level != null) {
                final var enchantment = level.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(Enchantments.UNBREAKING);

                unbreaking = stack.getEnchantmentLevel(enchantment);
            }

            final var maxDamage = stack.getMaxDamage();
            final var damage = stack.getDamageValue();
            final var damaged = (maxDamage - damage) * (unbreaking + 1);
            if (FORMATTER == null) FORMATTER = new UnitDurabilityFormatter(Durability777.CONFIG.format());
            final var text = FORMATTER.format(new BigDecimal(damaged));
            final var width = font.width(text);
            final var x = (xOffset + 8) * 2 + 1 + width / 2 - width;
            final var y = (yOffset * 2) + 18;
            final var color = stack.getBarColor() | 0xFF000000;
            graphics.pose().pushMatrix();
            graphics.pose().scale(0.5f, 0.5f);
            graphics.text(font, text, x, y, color, true);
            graphics.pose().popMatrix();
        }

        return true;
    }
}
