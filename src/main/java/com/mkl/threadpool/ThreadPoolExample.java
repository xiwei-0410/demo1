package com.mkl.threadpool;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.apache.batik.util.CleanerThread;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池创建
 *    1. Executors.newFixedThreadPool：创建⼀个固定⼤⼩的线程池，可控制并发的线程数，超出的线程会在队列中等待；
 *    2. Executors.newCachedThreadPool：创建⼀个可缓存的线程池，若线程数超过处理所需，缓存⼀段时间后会回收，若线程数不够，则新建线程；
 *    3. Executors.newSingleThreadExecutor：创建单个线程数的线程池，它可以保证先进先出的执⾏顺序；
 *    4. Executors.newScheduledThreadPool：创建⼀个可以执⾏延迟任务的线程池；
 *    5. Executors.newSingleThreadScheduledExecutor：创建⼀个单线程的可以执⾏延迟任务的线程池；
 *    6. Executors.newWorkStealingPool：创建⼀个抢占式执⾏的线程池（任务执⾏顺序不确定）【JDK1.8 添加】。
 *    7. ThreadPoolExecutor：最原始的创建线程池的⽅式，它包含了 7 个参数可供设置，。
 *
 *    线程池中的线程数量应该等于处理器数量加一
 *
 * @author wxw
 */
public class ThreadPoolExample {

    public static void main(String[] args) {
        createThreadPoolExecutor();
    }

    /**
     * 线程工厂
     */
    public static void factory(){
        /**
         * 自定义线程池名称或优先级（有问题）
         *      MAX_PRIORITY 最高优先级 使用数字10表示
         *      MIN_PRIORITY 最低优先级 使用数字1表示优先级
         *      NOR_PRIORITY 默认优先级 使用数字5 表示默认的优先级
         **/
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                //设置线程的命名规则
                thread.setName("线程"+r.hashCode());
                //设置线程的优先级
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        };

        /**
         * 线程池的名称前缀为"pool-"、线程池的守护线程状态为true
         */
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder()
                .setNamePrefix("pool-%d")
                .setDaemon(true);
    }

    /**
     * 固定数量的线程池
     */
    public static void newFixedThreadPool() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        /** 基础用法 **/
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        /**
         * 线程池返回结果
         * 使用submit可以执行有返回值的任务或者是无返回值的任务；而execute只能执行不带返回值的任务。
         * execute()无返回值发生异常会抛出，submit()返回值为Feature,异常无感知需要通过返回的Feature获取异常信息
         **/
        Future<Integer> result = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int num = new Random().nextInt(10);
                System.out.println(Thread.currentThread().getPriority() +"随机数"+num);
                return num;
            }
        });
        System.out.println(result.get());
    }

    /**
     * 带缓存的线程池
     *   优点：线程池会根据任务数量创建线程池，并且在一定时间内可以重复使用这些线程，产生相应的线程池。
     *   缺点：适用于短时间有大量任务的场景，它的缺点是可能会占用很多的资源。
     */
    public static void newCachedThreadPool(){
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(finalI +"---"+Thread.currentThread().getName());
                }
            });
        }
    }

    /**
     * 执⾏定时任务
     *      scheduleAtFixedRate 是以上⼀次任务的结束时间，作为下次定时任务的参考时间的。
     *      scheduleWithFixedDelay 是以上⼀次任务的开始时间，作为下次定时任务的参考时间的（参考时间+延迟任务=任务执⾏）。
     */
    public static void newScheduledThreadPool(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        System.out.println("添加任务的时间"+ LocalDateTime.now());

        /**
         * 延迟执⾏⼀次  定时3秒之后执行任务
         */
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行子任务：" + LocalDateTime.now());
            }
        },3,TimeUnit.SECONDS);

        /**
         * 定时3秒之后执行任务，每隔2秒执行一次
         * 如果执行时间大于设置的定时任务执行的时间间隔(4>3)，此时，哪个值大就以哪个值作为定时任务执行的周期(此时周期为4)
         */
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("执行子任务222222：" + LocalDateTime.now());
            }
        },3,2,TimeUnit.SECONDS);

        /** 参考时间+延迟任务=任务执⾏ **/
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("执行子任务33333333333"+ LocalDateTime.now());
            }
        },3,2,TimeUnit.SECONDS);
    }

    /**
     * 定时任务单线程
     */
    public static void newSingleThreadScheduledExecutorTime(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        System.out.println("添加任务的时间:" + LocalDateTime.now());
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行子任务"+ LocalDateTime.now());
            }
        },2,TimeUnit.SECONDS);
    }

    /**
     * 单线程线程池
     *      单线程的线程池又什么意义？
     *         1. 复用线程。
     *         2. 单线程的线程池提供了任务队列和拒绝策略（当任务队列满了之后（Integer.MAX_VALUE），新来的任务就会拒绝策略）
     */
    public static void newSingleThreadScheduledExecutor(){
        ExecutorService service = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < 10; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名：" + Thread.currentThread().getName());
                }
            });
        }
    }

    /**
     * 根据当前CPU⽣成线程池
     */
    public static void newWorkStealingPool(){
        ExecutorService service = Executors.newWorkStealingPool();
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                System.out.println("线程名" + Thread.currentThread().getName());
            });

            while(!service.isTerminated()) {
            }
        }
    }

    /**
     * 创建线程池
     *  corePoolSize 核心线程数
     *  maximumPoolSize  最大线程数
     *  keepAliveTime  存活时间
     *  unit  单位
     *  workQueue  阻塞队列
     *  handler  拒绝策略
     *             java自带   AbortPolicy  默认，提示异常，拒绝执行
     *                       DiscardPolicy  忽略最新任务
     *                       CallerRunsPolicy 在任务被拒绝添加后，会在调用execute方法的的线程来执行被拒绝的任务。除非executor被关闭，否则任务不会被丢弃。
     *                       DiscardOldestPolicy  忽略队列最后任务
     *  关闭线程
     *      shutdown 平缓关闭，等待所有已添加到线程池中的任务执行完在关闭
     *      shutdownNow   立刻关闭，停止正在执行的任务，并返回队列中未执行的任务
     *
     *  线程池状态
     *      RUNNING：允许提交并处理任务
     *      SHUTDOWN： 不允许提交新的任务，但是会处理完已提交的任务
     *      STOP：不允许提交新的任务，也不会处理阻塞队列未执行的，并设置正在执行的线程的中断标志位
     *      TIDYING：所有任务执行完毕，池中工作的线程数为0，等待执行terminated()方法
     *      TERMINATED：terminated()方法执行完毕
     *
     *  线程池的shutdown()方法，将线程池由RUNNING转为SHUTDOWN状态
     *  线程池的shutdownNow()方法，将线程池由RUNNING或SHUTDOWN转为STOP状态
     *  SHUTDOWN和STOP状态最终都会变为TERMINATED
     *
     *  有界队列
     *      SynchronousQueue：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于 阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法 Executors.newCachedThreadPool 使用了这个队列。
     *      ArrayBlockingQueue：一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。
     * 无界队列
     *      LinkedBlockingQueue：基于链表结构的无界阻塞队列，它可以指定容量也可以不指定容量（实际上任何无限容量的队列/栈都是有容量的，这个容量就是Integer.MAX_VALUE）
     *      PriorityBlockingQueue：是一个按照优先级进行内部元素排序的无界阻塞队列。队列中的元素必须实现 Comparable 接口，这样才能通过实现compareTo()方法进行排序。优先级最高的元素将始终排在队列的头部；PriorityBlockingQueue 不会保证优先级一样的元素的排序。
     * ThreadPoolExecutor线程池推荐了三种等待队列，SynchronousQueue 、LinkedBlockQueue和 ArrayBlockingQueue
     *
     *
     */
    public static void createThreadPoolExecutor(){
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                return thread;
            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,5,5,TimeUnit.SECONDS,new LinkedBlockingDeque<>(1),new ThreadPoolExecutor.CallerRunsPolicy());

        /**
         * 自定义策略
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("=========自定义策略========");
            }
        });
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"执行任务"+ finalI);
                }
            });
        }
        poolExecutor.shutdown();


    }

}
