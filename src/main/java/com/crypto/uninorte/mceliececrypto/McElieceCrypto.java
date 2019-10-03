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
//        int[][] h = {
//            {1,0,0,0,1,1,1,1,1},
//            {0,1,1,0,0,1,1,1,1},
//            {1,0,1,1,0,1,0,0,1},
//            {0,0,0,0,0,0,0,0,0},
//            {1,1,0,1,1,1,1,0,0},
//            {1,0,0,0,1,1,1,1,1},
//            {0,0,1,1,1,0,1,1,0},
//            {1,1,0,1,0,0,1,1,0}
//        };
        
        int h[][] = {
            {0,1,0,1,0,0,1,1,0,1,1,0},
            {1,1,1,0,0,0,1,0,0,1,0,0},
            {0,1,0,0,1,0,1,1,1,0,0,1},
            {1,0,1,1,1,0,0,0,0,1,1,1},
            {1,0,0,0,0,1,1,1,0,1,1,0},
            {1,0,0,1,1,0,0,0,1,0,1,1},
            {1,1,1,0,1,0,0,1,1,0,1,0},
            {1,1,1,0,1,0,1,0,1,1,1,0}
        };
        
//        int h[][] = {
//            {0,0,0,0,0,1,0,1,1,0,1,0,1,0,0,0},
//            {0,0,1,0,1,0,1,0,0,1,0,1,0,0,0,1},
//            {1,0,0,0,0,1,1,0,1,1,1,1,1,1,0,1},
//            {1,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1},
//            {0,0,0,1,1,1,0,1,1,1,1,0,1,0,1,1},
//            {0,0,1,0,1,1,0,1,0,1,0,0,1,1,0,1},
//            {0,0,0,1,1,1,0,1,1,1,1,0,1,0,1,1},
//            {1,1,0,0,1,1,1,0,1,0,0,1,0,0,1,0}
//        };
        
        int n = h.length;
        int m = h[0].length;
        for(int i = m-n; i < m; i++){
            if(h[i-m+n][i] == 0){
                for(int j = i-m+n+1; j<n; j++){
                    if(h[j][i] == 1){
                       for(int k = 0; k < m; k++){
                            h[i-m+n][k] = h[i-m+n][k]^h[j][k];
                        }
                       break;
                    }
                }
            }
            
            for(int j = i-m+n+1; j < n; j++){
                if(h[j][i] == 1){
                    for(int k = i; k < m; k++){
                        h[j][k] = h[j][k]^h[i-m+n][k];
                    }
                }
            }
            
        }
        
//        for(int i = n-1; i>=1; i--){
//            for(int j = i-1; j>=0; j--){
//                if(h[j][i] == 1){
//                    for(int k = i; k<m;k++){
//                        h[j][k] = h[j][k]^h[i][k];
//                    }
//                }
//            }
//        }
        
        
        for(int i = 0; i<n; i++){
            System.out.println(Arrays.toString(h[i]));
        }
    }
}
