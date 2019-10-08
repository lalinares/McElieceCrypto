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
    private int fieldPoly;
    private int[] goppaPoly;
    private int[] L;
    
    private boolean initialized;
    
    private SecureRandom secureRandom;
    
    public McElieceKem(int n, int m, int t){
        if(t<2){
            System.err.println("El parámetro t debe ser mayor o igual a 2!");
            return;
        }
        if(m*t >= n){
            System.err.println("La condición mt < n no se cumple!");
            return;
        }
        if(n > 1<<m){
            System.err.println("El parámetro n no cumple la condición n <= 2^m");
            return;
        }
        
        this.m = m;
        this.n = n;
        this.t = t;
        this.q = 1<<m;
        
        this.secureRandom = new SecureRandom();
        
    }
    
    public McElieceKem(int[] L, int fieldPoly, int[] goppaPoly){
        this.L = L;
        this.fieldPoly = fieldPoly;
        this.goppaPoly = goppaPoly;
        
        this.n = L.length;
        this.m = polyDegree(fieldPoly);
        this.t = goppaPoly.length-1;
        this.q = 1<<this.m;
        
        if(this.t<2){
            System.err.println("El parámetro t debe ser mayor o igual a 2!");
            return;
        }
        if(this.m*this.t >= this.n){
            System.err.println("La condición mt < n no se cumple!");
            return;
        }
        if(this.n > 1<<this.m){
            System.err.println("El parámetro n no cumple la condición n <= 2^m");
            return;
        }
        
        this.initialized = true;
    }
    
    public int generateFieldPoly(int m){
        int a = (1<<m) + 1;
        int b = 1<<(m+1);
        int poly = -1;
        for(int i = a; i<b;i++){
            if(isIrreducible(i)){
                
            }
        }
        return poly;
    }
    
    public boolean isIrreducible(int poly){
        
        
        return false;
    }
    
    public int multiply(int a, int b){
        int result = 0;
        while(b != 0){
            if((b & 0b1) == 1){
                result = result ^ a;
            }
            b >>>=1;
            a <<=1;
        }
        return result;
    }
    
    public int polyDegree(int poly){
        int count = -1;
        while(poly != 0){
            poly >>>= 1;
            count++;
        }
        
        return count;
    }
    
    public int mod(int a, int b){
        int da = polyDegree(a);
        int db = polyDegree(b);
        while(da>=db){
            a = (b<<(da-db)) ^ a;
            da = polyDegree(a);
            db = polyDegree(b);
        }
        return a;
    }
    
    public static void main(String[] args) {
        //test
        McElieceKem mck = new McElieceKem(12, 4, 2);
        System.out.println(mck.mod(mck.multiply(0b111111000111100000, 0b110000000), 0b10011));
        //test
//        int h[][] = {
//            {0,1,0,1,0,0,1,1,0,1,1,0},
//            {1,1,1,0,0,0,1,0,0,1,0,0},
//            {0,1,0,0,1,0,1,1,1,0,0,1},
//            {1,0,1,1,1,0,0,0,0,1,1,1},
//            {1,0,0,0,0,1,1,1,0,1,1,0},
//            {1,0,0,1,1,0,0,0,1,0,1,1},
//            {1,1,1,0,1,0,0,1,1,0,1,0},
//            {1,1,1,0,1,0,1,0,1,1,1,0}
//        };
//        
//        int n = h.length;
//        int m = h[0].length;
//        for(int i = m-n; i < m; i++){
//            if(h[i-m+n][i] == 0){
//                for(int j = i-m+n+1; j<n; j++){
//                    if(h[j][i] == 1){
//                       for(int k = 0; k < m; k++){
//                            h[i-m+n][k] = h[i-m+n][k]^h[j][k];
//                        }
//                       break;
//                    }
//                }
//            }
//            
//            for(int j = i-m+n+1; j < n; j++){
//                if(h[j][i] == 1){
//                    for(int k = i; k < m; k++){
//                        h[j][k] = h[j][k]^h[i-m+n][k];
//                    }
//                }
//            }
//            
//        }
//        
////        for(int i = n-1; i>=1; i--){
////            for(int j = i-1; j>=0; j--){
////                if(h[j][i] == 1){
////                    for(int k = i; k<m;k++){
////                        h[j][k] = h[j][k]^h[i][k];
////                    }
////                }
////            }
////        }
//        
//        
//        for(int i = 0; i<n; i++){
//            System.out.println(Arrays.toString(h[i]));
//        }
    }
}
