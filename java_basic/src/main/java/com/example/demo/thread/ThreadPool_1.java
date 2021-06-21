package com.example.demo.thread;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadPool_1 {
    //获取当前机器的核数
    private static final Integer cpuNum = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newFixedThreadPool(cpuNum);
        System.out.println("cpu核心:"+cpuNum);

        CompletionService<String> completionService
                =new ExecutorCompletionService<String>(threadPool);
        for(int i=0; i< 20; i ++){
            completionService.submit(new Demothread(i+"",i));
        }
        for(int i=0; i< 20; i ++){
            Future<String> result=completionService.take();
            System.out.println(result.get()+"结束");
        }
        threadPool.shutdown();
        Thread.sleep(1000*5);
        System.out.println("最终");

    }

    static class Demothread implements Callable<String>{
        public String name;
        public Integer time;

        Demothread(String name,Integer time){
            this.name=name;
            this.time=time;
        }

        public String call() throws Exception {
            System.out.println("线程"+name+"开始");
            Random random=new Random();
            Thread.sleep(1000*random.nextInt(16));
            return name;
        }
    }
}
