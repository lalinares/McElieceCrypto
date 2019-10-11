/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.uninorte.mceliececrypto;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 *
 * @author pc
 */
public class McElieceKem {

    private int n;
    private int m;
    private int t;
    private int q;
    private int k;
    private GF2mField field;
    private int fieldPoly;
    private PolynomialGF2mSmallM goppaFieldPoly;
    private int[] goppaPoly;
    private int[] L;

    private SecureRandom secureRandom;

    public McElieceKem(int n, int m, int t) {
        if (t < 2) {
            System.err.println("El parámetro t debe ser mayor o igual a 2!");
            return;
        }
        if (m * t >= n) {
            System.err.println("La condición mt < n no se cumple!");
            return;
        }
        if (n > 1 << m) {
            System.err.println("El parámetro n no cumple la condición n <= 2^m");
            return;
        }

        this.secureRandom = new SecureRandom();

        this.m = m;
        this.n = n;
        this.t = t;
        this.q = 1 << this.m;
        this.k = this.n - this.m * this.t;

        this.field = new GF2mField(this.m, this.secureRandom);

        this.fieldPoly = this.field.getPolynomial();

        this.goppaFieldPoly = new PolynomialGF2mSmallM(field, t, PolynomialGF2mSmallM.RANDOM_IRREDUCIBLE_POLYNOMIAL, this.secureRandom);

        this.goppaPoly = this.goppaFieldPoly.getCoefficients();

        this.L = generateRandomL();

    }

    public McElieceKem(int[] L, int fieldPoly, int[] goppaPoly) {
        this.L = L;
        this.fieldPoly = fieldPoly;
        this.goppaPoly = goppaPoly;

        this.secureRandom = new SecureRandom();

        this.m = PolynomialRingGF2.degree(fieldPoly);

        this.field = new GF2mField(this.m, fieldPoly, this.secureRandom);

        this.goppaFieldPoly = new PolynomialGF2mSmallM(field, goppaPoly);

        this.n = this.L.length;
        this.t = this.goppaPoly.length - 1;
        this.q = 1 << this.m;
        this.k = this.n - this.m * this.t;

        if (this.t < 2) {
            System.err.println("El parámetro t debe ser mayor o igual a 2!");
            return;
        }
        if (this.m * this.t >= this.n) {
            System.err.println("La condición mt < n no se cumple!");
            return;
        }
        if (this.n > this.q) {
            System.err.println("El parámetro n no cumple la condición n <= 2^m");
            return;
        }

    }

    public int[] generateRandomL() {
        boolean[] flags = new boolean[this.q];
        int[] randomL = new int[this.n];
        int count = 0;
        int roots = 0;
        while (count < this.n && count + roots < this.q) {
            int element = field.getRandomElement();
            if (flags[element]) {
                continue;
            }
            if (this.goppaFieldPoly.evaluateAt(element) == 0) {
                roots++;
            } else {
                randomL[count] = element;
                count++;
            }
            flags[element] = true;
        }
        if (count == this.n) {
            return randomL;
        } else {
            return null;
        }
    }

    public int getElementOfH(int j, int i) {
        int element = PolynomialRingGF2.remainder(L[j], field.getPolynomial());
        return field.mult(field.exp(element, i), field.inverse(goppaFieldPoly.evaluateAt(element)));
    }

    public static void main(String[] args) {

//        McElieceKem mck = new McElieceKem(new int[]{
//            1<<2,
//            1<<3,
//            1<<4,
//            1<<5,
//            1<<6,
//            1<<7,
//            1<<8,
//            1<<9,
//            1<<10,
//            1<<11,
//            1<<12,
//            1<<13,
//        }, 0b10011, new int[]{1,11,1});
        McElieceKem mck = new McElieceKem(16, 4, 2);
        
        boolean good;
        do {
            good = true;
            System.out.println("m = " + mck.m);
            System.out.println("n = " + mck.n);
            System.out.println("t = " + mck.t);
            System.out.println("q = " + mck.q);
            System.out.println("k = " + mck.k);
            System.out.println("g(z) = " + Arrays.toString(mck.goppaPoly));
            System.out.println("L = " + Arrays.toString(mck.L));
            System.out.println("f(x) = " + Integer.toBinaryString(mck.fieldPoly));

            int mt = mck.m * mck.t;

            int[][] h = new int[mt][mck.n];

            for (int i = 0; i < mck.t; i++) {
                for (int j = 0; j < mck.n; j++) {
                    int hij = mck.getElementOfH(j, i);
                    for (int k = 0; k < mck.m; k++) {
                        h[i * mck.m + k][j] = (hij & (1 << k)) >>> k;
                    }
                }
            }

            System.out.println("");
            System.out.println("H=");
            for (int i = 0; i < mt; i++) {
                System.out.println(Arrays.toString(h[i]));
            }
            System.out.println("");

            for (int i = 0; i < mt; i++) {
                if (h[i][i] == 0) {
                    for (int j = i + 1; j < mt; j++) {
                        if (h[j][i] == 1) {
                            for (int z = 0; z < mck.n; z++) {
                                h[i][z] = h[i][z] ^ h[j][z];
                            }
                            break;
                        }
                    }
                }

                for (int j = i + 1; j < mt; j++) {
                    if (h[j][i] == 1) {
                        for (int z = i; z < mck.n; z++) {
                            h[j][z] = h[j][z] ^ h[i][z];
                        }
                    }
                }

            }

            for (int i = mt - 1; i >= 1; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    if(h[i][i] == 0){
                        mck.L = mck.generateRandomL();
                        mck.goppaFieldPoly = new PolynomialGF2mSmallM(mck.field, mck.t, PolynomialGF2mSmallM.RANDOM_IRREDUCIBLE_POLYNOMIAL, mck.secureRandom);
                        good = false;
                        continue;
                    }
                    if (h[j][i] == 1) {
                        for (int z = i; z < mck.n; z++) {
                            h[j][z] = h[j][z] ^ h[i][z];
                        }
                    }
                }
            }

            int[][] g = new int[mck.k][mck.n];
            for (int i = mt, f = 0; i < mck.n; i++, f++) {
                for (int j = 0; j < mt; j++) {
                    g[f][j] = h[j][i];
                }
            }
            for (int i = 0; i < mck.k; i++) {
                g[i][mt + i] = 1;
            }

            System.out.println("H^=");
            for (int i = 0; i < mt; i++) {
                System.out.println(Arrays.toString(h[i]));
            }

            System.out.println("");
            System.out.println("G=");
            for (int i = 0; i < mck.k; i++) {
                System.out.println(Arrays.toString(g[i]));
            }
        } while (!good);
    }
}
