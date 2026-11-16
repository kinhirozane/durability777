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
package com.kinhiro.durability777;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

@Mod(Durability777.NAMESPACE)
public final class Durability777 {
    public static final String NAMESPACE = "durability777";
    public static final String NAME = "Durability777";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DurabilityConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    public static final Lazy<KeyMapping> KEY_MAPPING = Lazy.of(() -> new KeyMapping(
        "key." + NAMESPACE + ".toggle_unit_system",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F7,
        KeyMapping.Category.DEBUG
    ));

    static {
        final var pair = new ModConfigSpec.Builder().configure(DurabilityConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    public static Identifier id(final String id) {
        return Identifier.fromNamespaceAndPath(NAMESPACE, id);
    }

    public Durability777(final ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, CONFIG_SPEC);
    }
}
