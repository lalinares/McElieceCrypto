/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.uninorte.mceliececrypto;

import java.util.Arrays;

/**
 *
 * @author pc
 */
public class McElieceCrypto {
    
    public static void main(String[] args) {
        boolean[][] h = {
            {true,false,false,false,true,true,true,true,true},
            {false,true,true,false,false,true,true,true,true},
            {true,false,true,true,false,true,false,false,true},
            {false,false,false,false,false,false,false,false,false},
            {true,true,false,true,true,true,true,false,false},
            {true,false,false,false,true,true,true,true,true},
            {false,false,true,true,true,false,true,true,false},
            {true,true,false,true,false,false,true,true,false}
        };
        
//        boolean h[][] = {
//            {false,true,false,true,false,false,true,true,false,true,true,false},
//            {true,true,true,false,false,false,true,false,false,true,false,false},
//            {false,true,false,false,true,false,true,true,true,false,false,true},
//            {true,false,true,true,true,false,false,false,false,true,true,true},
//            {true,false,false,false,false,true,true,true,false,true,true,false},
//            {true,false,false,true,true,false,false,false,true,false,true,true},
//            {true,true,true,false,true,false,false,true,true,false,true,false},
//            {true,true,true,false,true,false,true,false,true,true,true,false}
//        };
        
        
        int n = h.length;
        int m = h[0].length;
        for(int i = 0; i < n-1; i++){
            if(h[i][i] == false){
                for(int j = i+1; j<n; j++){
                    if(h[j][i] == true){
                       for(int k = i; k < m; k++){
                            h[i][k] = h[i][k]^h[j][k];
                        }
                       break;
                    }
                }
            }
            
            for(int j = i+1; j < n; j++){
                if(h[j][i] == true){
                    for(int k = i; k < m; k++){
                        h[j][k] = h[j][k]^h[i][k];
                    }
                }
            }
            
        }
        
        for(int i = n-1; i>=1; i--){
            for(int j = i-1; j>=0; j--){
                if(h[j][i] == true){
                    for(int k = i; k<m;k++){
                        h[j][k] = h[j][k]^h[i][k];
                    }
                }
            }
        }
        
        int H[][] = new int[h.length][h[0].length];
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < h[0].length; j++) {
                H[i][j] = h[i][j]?1:0;
            }
        }
        for(int i = 0; i<n; i++){
            System.out.println(Arrays.toString(H[i]));
        }
    }
}
