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

import com.kinhiro.durability777.util.unit.UnitSystem;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.math.RoundingMode;

public final class DurabilityConfig {
    public final ModConfigSpec.EnumValue<UnitSystem> unitSystem;
    public final ModConfigSpec.EnumValue<RoundingMode> roundingMode;
    public final ModConfigSpec.IntValue decimalPlaces;
    public final ModConfigSpec.BooleanValue useGrouping;
    public final ModConfigSpec.ConfigValue<String> groupingSeparator;
    public final ModConfigSpec.ConfigValue<String> decimalSeparater;
    public final ModConfigSpec.ConfigValue<String> exponentSeparater;
    public final ModConfigSpec.ConfigValue<String> positivePrefix;
    public final ModConfigSpec.ConfigValue<String> positiveSuffix;
    public final ModConfigSpec.ConfigValue<String> negativePrefix;
    public final ModConfigSpec.ConfigValue<String> negativeSuffix;

    DurabilityConfig(final ModConfigSpec.Builder builder) {
        unitSystem = builder.comment(
                " Number formatting system used for displaying large quantities.",
                " Default: SHORT_SCALE",
                " Allowed values:",
                " - \"CHINESE_MYRIAD\": Chinese myriad-based units (万, 亿, 兆, 京).",
                " - \"METRIC_PREFIX\": Metric prefixes (k, M, G, T).",
                " - \"SHORT_SCALE\": Short-scale abbreviations (K, M, B, T).",
                " Additional unit systems may be provided by other mods.")
            .defineEnum("unitSystem", UnitSystem.SHORT_SCALE);

        roundingMode = builder.comment(
                " Rounding mode used when formatting decimal numbers.",
                " Default: DOWN",
                " Allowed values:",
                " - \"UP\": Rounds away from zero.",
                " - \"DOWN\": Rounds towards zero.",
                " - \"CEILING\": Rounds towards positive infinity.",
                " - \"FLOOR\": Rounds towards negative infinity.",
                " - \"HALF_UP\": Rounds towards the nearest neighbor, rounding ties up.",
                " - \"HALF_DOWN\": Rounds towards the nearest neighbor, rounding ties down.",
                " - \"HALF_EVEN\": Rounds towards the nearest neighbor, rounding ties to the even neighbor.",
                " - \"UNNECESSARY\": Throws an exception if rounding is required.")
            .defineEnum("roundingMode", RoundingMode.DOWN);

        decimalPlaces = builder.comment(
                " Maximum number of decimal places to display.",
                " Range: 0–6",
                " Default: 0")
            .defineInRange("decimalPlaces", 0, 0, 6);

        useGrouping = builder.comment(
                " Whether to group integer digits using the grouping separator.",
                " Default: true"
            )
            .define("useGrouping", true);

        groupingSeparator = builder.comment(
                " Character used to separate groups of integer digits.",
                " Default: \",\""
            )
            .define("groupingSeparator", ",", DurabilityConfig::character);

        decimalSeparater = builder.comment(
                " Character used as the decimal separator.",
                " Default: \".\""
            )
            .define("decimalSeparater", ".", DurabilityConfig::character);

        exponentSeparater = builder.comment(
                " String used to separate the mantissa from the exponent.",
                " Default: \"E\""
            )
            .define("exponentSeparater", "E");

        positivePrefix = builder.comment(
                " String prepended to positive numbers.",
                " Default: \"\""
            )
            .define("positivePrefix", "");

        positiveSuffix = builder.comment(
                " String appended to positive numbers.",
                " Default: \"\""
            )
            .define("positiveSuffix", "");

        negativePrefix = builder.comment(
                " String prepended to negative numbers.",
                " Default: \"-\""
            )
            .define("negativePrefix", "-");

        negativeSuffix = builder.comment(
                " String appended to negative numbers.",
                " Default: \"\""
            )
            .define("negativeSuffix", "");
    }

    public DecimalFormat format() {
        return new DecimalFormat(
            roundingMode.get(),
            decimalPlaces.getAsInt(),
            useGrouping.getAsBoolean(),
            groupingSeparator.get().charAt(0),
            decimalSeparater.get().charAt(0),
            exponentSeparater.get(),
            positivePrefix.get(),
            positiveSuffix.get(),
            negativePrefix.get(),
            negativeSuffix.get()
        );
    }

    private static boolean character(final Object value) {
        return value instanceof final String string && string.length() == 1;
    }

    public record DecimalFormat(
        RoundingMode roundingMode,
        int decimalPlaces,
        boolean useGrouping,
        char groupingSeparater,
        char decimalSeparater,
        String exponentSeparater,
        String positivePrefix,
        String positiveSuffix,
        String negativePrefix,
        String negativeSuffix
    ) {
    }
}
