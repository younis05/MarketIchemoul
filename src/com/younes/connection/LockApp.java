/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author BOUKHTACHE
 */
public class LockApp {
    private String appname;
    private File file;
    private FileChannel channel;
    private FileLock lock;  
  
   public LockApp(String appname){
       this.appname=appname;
   }
   public boolean isAppActive(){
       try{
           file=new File(System.getProperty("user.home"),appname+".tmp"); 
           channel=new RandomAccessFile(file,"rw").getChannel(); 
         try{
           lock=channel.tryLock();
       }catch(Exception ex){
           closeLock();
           return true;
       }
         if(lock==null){
            closeLock();
           return true;  
         }
         Runtime.getRuntime().addShutdownHook(new Thread(){
             public void run(){
               closeLock();
               deleteFile();
             }
         });
         return false;
       }catch(Exception ex){
           closeLock();
           return true;   
       }
   }
  private void closeLock(){
         try{
           lock.release();
       }catch(Exception ex){  } 
         try{
           channel.close();
       }catch(Exception ex){  }   
     }
     private void deleteFile(){
         try{
           file.delete();
       }catch(Exception ex){
           
       } 
     } 
public  void appClosable(){
    LockApp lk=new LockApp(appname);
    if(lk.isAppActive()){
        System.out.println("Already active!");
       System.exit(1);
    }else{
    System.out.println("Not Already active!"); 
}
}
}
