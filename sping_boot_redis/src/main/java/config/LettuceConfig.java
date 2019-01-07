package config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:config/redis.properties")
public class LettuceConfig {
    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;


    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${spring.redis.cluster.max-redirects}")
    private Integer mmaxRedirectsac;

    @Value("${redis.sentinel.host1}")
    private String senHost1;

    @Value("${redis.sentinel.port1}")
    private  Integer senPort1;

    @Value("${redis.hostName}")
    private  String hostName;

    @Value("${redis.port}")
    private  Integer port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private Integer database;
    //注入lettuce
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        //单机版
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        //集群版
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String [] serverArray =  clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for(String ipPort:serverArray){
            String [] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0],Integer.parseInt(ipAndPort[1])));
        }
        redisClusterConfiguration.setPassword(RedisPassword.of(password));
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(mmaxRedirectsac);
        redisClusterConfiguration.setClusterNodes(nodes);

        //哨兵版
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置matser的名称
        RedisNode redisNode = new RedisNode(hostName, port);
        redisNode.setName("mymaster");
        redisSentinelConfiguration.master(redisNode);
        //配置redis的哨兵sentinel
        RedisNode senRedisNode = new RedisNode(senHost1,senPort1);
        Set<RedisNode> redisNodeSet = new HashSet<>();
        redisNodeSet.add(senRedisNode);
        redisSentinelConfiguration.setSentinels(redisNodeSet);

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().
                commandTimeout(Duration.ofMillis(maxWaitMillis)).build();
        //需要使用哪个版本，则配置那个。redisClusterConfiguration、redisStandaloneConfiguration、redisSentinelConfiguration
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration,clientConfiguration);
        return lettuceConnectionFactory;
    }
    //GenericObjectPoolConfig 连接池配置
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return genericObjectPoolConfig;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //为redisTemplate配置链接信息。
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }
    @Bean
    public HashOperations hashOperations(){
        return  redisTemplate().opsForHash();
    }
}
