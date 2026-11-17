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

import java.math.BigDecimal;

public interface NumericalUnit {
    DecimalUnit DEFUALT = new DecimalUnit(decimal(0), "");

    static BigDecimal decimal(final int pow) {
        if (pow < 0) return BigDecimal.ONE.movePointLeft(-pow);
        return BigDecimal.TEN.pow(pow);
    }

    DecimalUnit[] units();

    DecimalUnit smallest();

    default DecimalUnit max() {
        return units()[0];
    }

    default DecimalUnit min() {
        return units()[units().length - 1];
    }

    default DecimalUnit fallback() {
        return DEFUALT;
    }
}
