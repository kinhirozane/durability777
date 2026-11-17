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
package com.kinhiro.durability777.util.unit;

import net.neoforged.fml.common.asm.enumextension.ExtensionInfo;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;
import net.neoforged.fml.common.asm.enumextension.NamedEnum;

import java.util.function.Supplier;

@NamedEnum
public enum UnitSystem implements IExtensibleEnum {
    CHINESE_MYRIAD("chinese_myriad", ChineseMyriad::new),
    METRIC_PREFIX("metric_prefix", MetricPrefix::new),
    PERCENTAGE("percentage", Percentage::new),
    SHORT_SCALE("short_scale", ShortScale::new);

    public static ExtensionInfo getExtensionInfo() {
        return ExtensionInfo.nonExtended(UnitSystem.class);
    }

    private final String identifier;
    private final Supplier<NumericalUnit> unitSupplier;

    UnitSystem(final String identifier, final Supplier<NumericalUnit> unitSupplier) {
        this.identifier = identifier;
        this.unitSupplier = unitSupplier;
    }

    public String identifier() {
        return identifier;
    }

    public DecimalUnit get(final int index) {
        return units()[index];
    }

    public DecimalUnit reversed(final int index) {
        return units()[units().length - 1 - index];
    }

    public UnitSystem clockwise() {
        return values()[(ordinal() + 1) % values().length];
    }

    public DecimalUnit[] units() {
        return unitSupplier.get().units();
    }

    public DecimalUnit smallest() {
        return unitSupplier.get().smallest();
    }

    public DecimalUnit max() {
        return unitSupplier.get().max();
    }

    public DecimalUnit min() {
        return unitSupplier.get().min();
    }

    public DecimalUnit fallback() {
        return unitSupplier.get().fallback();
    }
}
