/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;

/**
 *
 * @author Dame
 */
public class Result {
    private final File imageFile, dir;
    private final int status;
    
    public Result(File imageFile, File dir, int status){
        this.imageFile = imageFile;
        this.dir = dir;
        this.status = status;
    }
    
    public File getImageFile(){
        return this.imageFile;
    }
    public File getDir() {
        return this.dir;
    }
    public int getStatus(){
        return this.status;
    }
}
