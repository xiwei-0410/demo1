package com.wxw.util;

public class MyThread{

//    @Override
//    public void run(){
//        System.out.println("1111111111111");
//    }


    public static void main(String[] args) {
        //for(int i=0;i<5;i++){
        try {
            aaa();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //}
    }

    public static void aaa() throws InterruptedException {
        for(int i =0;i<50;i++){
            Thread.sleep(500);
            System.out.println("11111111111111111111");
        }

        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("--------------------------");
            }
        });
        t1.start();
    }
}
