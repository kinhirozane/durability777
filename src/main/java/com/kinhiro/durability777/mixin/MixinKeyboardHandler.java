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
package com.kinhiro.durability777.mixin;

import com.kinhiro.durability777.Durability777;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class MixinKeyboardHandler {
    @Inject(method = "handleDebugKeys", at = @At("TAIL"), cancellable = true)
    private void durability777$handleDebugKeys(
        final KeyEvent event,
        final CallbackInfoReturnable<Boolean> cir
    ) {
        if (Durability777.KEY_MAPPING.get().matches(event)) {
            final var oldUnitSystem = Durability777.CONFIG.unitSystem.get();
            final var newUnitSystem = oldUnitSystem.clockwise();
            Durability777.CONFIG.unitSystem.set(newUnitSystem);
            Durability777.CONFIG.unitSystem.save();
            if (Minecraft.getInstance().player != null) {
                final var message = Component.literal("");
                message.append(Component.translatable("message." + Durability777.NAMESPACE + ".switch_unit_system")
                    .withStyle(ChatFormatting.GRAY));

                final var id = newUnitSystem.identifier().replace(":", ".");
                message.append(Component.translatable("unit_system." + Durability777.NAMESPACE + "." + id)
                    .withStyle(ChatFormatting.GOLD));

                Minecraft.getInstance().player.sendOverlayMessage(message);
            }

            cir.setReturnValue(true);
        }
    }
}
