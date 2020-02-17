package com.biao.shop.stock.util;

import com.biao.shop.common.utils.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @ClassName LettuceUtil
 * @Description: 1.redis的工具类，使用线程池方式进行异步提交和获取，
 *               2，并自定义了redis连接池，减少获取connection的时间
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
@Component
public class LettuceUtil<R> {
    private final Logger logger = LoggerFactory.getLogger(LettuceUtil.class);

    @Value("${redis.server.addr}")
    private String redisSvrAddr;
    @Value("${redis.server.db}")
    private String redisSvrDb;

    //自建的 Redis lettuce 连接池

    // 线程池配置，用于执行redis操作
        int corePoolSize = 5;
        int maximumPoolSize = 20;
        long keepAliveTime = 5000L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(16);
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory());

    // 因数据库操作是非常频繁的，故使用线程池+同步
    public  void syncTest() {
        threadPool.submit(()->{
            RedisClient client = RedisClient.create("redis://" + redisSvrAddr + "/" + redisSvrDb);
            StatefulRedisConnection<String, String> connection = client.connect();
            //配置为同步方式
            RedisStringCommands<String,String> strOperator = connection.sync();
            String value = strOperator.get("xiao");
            logger.debug("lettuce client gets value >>> {}",value);
            connection.close();
            client.shutdown();
        });
    }

    // 因数据库操作是非常频繁的，故使用线程池+异步
    public <R> void asyncSet(R object) {
        // Syntax: redis://[password@]host[:port]/db,eg:"redis://test123@192.168.1.204:6379/0"
        threadPool.submit(()->{
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
            RedisFuture<String> set = async.set(String.valueOf(object.hashCode()), jsonStr);
            connection.close();
            redisClient.shutdown();
        });
    }

    // 因数据库操作是非常频繁的，故使用线程池+异步
    public <R> R asyncGet(String key, Class<R> clazz) throws ExecutionException, InterruptedException, JsonProcessingException {
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
    }

    // redis cluster
    public void redisCluster(){
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:6379");
        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to RedisCluster");
        connection.close();
        redisClient.shutdown();
    }

}
