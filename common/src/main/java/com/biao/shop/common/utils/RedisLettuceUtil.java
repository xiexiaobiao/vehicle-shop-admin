package com.biao.shop.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @ClassName RedisLettuceUtil
 * @Description: 使用lettuce操作redis的工具类
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
@Component
public class RedisLettuceUtil {

    @Value("${redis.server.addr}")
    private static String redisSvrAddr;
    @Value("${redis.server.db}")
    private static String redisSvrDb;

    // 线程池配置，用于执行redis操作
    private static int corePoolSize = 5;
    private static int maximumPoolSize = 20;
    private static long keepAliveTime = 5000L;
    private static TimeUnit unit = TimeUnit.MILLISECONDS;
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(16);
    private static ExecutorService  threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                                            unit, workQueue, Executors.defaultThreadFactory());

    // 因数据库操作是非常频繁的，故使用线程池+异步
    public static <R> RedisFuture<String> asyncSet(String key, R object) throws InterruptedException, ExecutionException, TimeoutException {
        // Syntax: redis://[password@]host[:port]/db,eg:"redis://test123@192.168.1.204:6379/0"
        Future<RedisFuture<String>> future = threadPool.submit(()->{
            RedisClient redisClient = RedisClient.create("redis://" + redisSvrAddr + "/" + redisSvrDb);
            StatefulRedisConnection<String,String> connection = redisClient.connect();
            String jsonStr = null;
            try {
                jsonStr = JacksonUtil.convertToJson(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            /* redis Hash 操作*/
            //RedisHashAsyncCommands<String,Object> hashAsyncCommands = connection.async();
            //配置为异步方式
            //RedisHashAsyncCommands<String,Object> hashAsyncCommands = connection.setAutoFlushCommands(true);
            //hashAsyncCommands.hsetnx("person","bb",user);
            RedisStringAsyncCommands<String, String> async = connection.async();
            // 500 milliseconds后key过期
            RedisFuture<String> setResult = async.psetex(key,500,jsonStr);
            connection.close();
            redisClient.shutdown();
            return setResult;
        });
        return future.get(2000,TimeUnit.MILLISECONDS);
    }

    //
    public static <R> R asyncGet(String key, Class<R> clazz) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        Future<R> future = threadPool.submit(() -> {
            // Syntax: redis://[password@]host[:port]/db,eg:"redis://test123@192.168.1.204:6379/0"
            RedisClient redisClient = RedisClient.create("redis://" + redisSvrAddr + "/" + redisSvrDb);
            StatefulRedisConnection<String,String> connection = redisClient.connect();
            /* redis Hash 操作*/
            //RedisHashAsyncCommands<String,Object> hashAsyncCommands = connection.async();
            //配置为异步方式
            //RedisHashAsyncCommands<String,Object> hashAsyncCommands = connection.setAutoFlushCommands(true);
            //hashAsyncCommands.hsetnx("person","bb",user);
            RedisStringAsyncCommands<String, String> async = connection.async();
            RedisFuture<String> asyncGet = async.get(key);
            connection.close();
            redisClient.shutdown();
            return JacksonUtil.convertJsonToObject(asyncGet.get(),clazz);
        });
        return (R) future.get(2000,TimeUnit.MILLISECONDS);
    }

    // 因lettuce无删除方法，这里使用Jedis客户端来进行删除
    public static boolean delete(String key){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        return redisTemplate.delete(key);
    }
}
