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

import com.kinhiro.durability777.DurabilityConfig;
import com.kinhiro.durability777.util.unit.UnitSystem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class UnitDurabilityFormatter {
    private final DecimalFormat formatter;

    public UnitDurabilityFormatter(final DurabilityConfig.DecimalFormat config) {
        formatter = new DecimalFormat();
        formatter.setRoundingMode(config.roundingMode());
        formatter.applyPattern(pattern(config.decimalPlaces()));
        formatter.setGroupingUsed(config.useGrouping());
        formatter.setPositivePrefix(config.positivePrefix());
        formatter.setPositiveSuffix(config.positiveSuffix());
        formatter.setNegativePrefix(config.negativePrefix());
        formatter.setNegativeSuffix(config.negativeSuffix());
        final var symbols = new DecimalFormatSymbols(Locale.ROOT);
        symbols.setGroupingSeparator(config.groupingSeparater());
        symbols.setDecimalSeparator(config.decimalSeparater());
        symbols.setExponentSeparator(config.exponentSeparater());
        formatter.setDecimalFormatSymbols(symbols);
    }

    public String format(final BigDecimal damage, final UnitSystem unitSystem) {
        for (final var unit : unitSystem.units())
            if (damage.compareTo(unit.size()) >= 0) return unit.format(damage, formatter);

        return unitSystem.fallback().format(damage, formatter);
    }

    private String pattern(final int points) {
        return points <= 0 ? "#,##0" : "#,##0." + "#".repeat(points);
    }
}
