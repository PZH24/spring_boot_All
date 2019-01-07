package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import utils.RedisUtil;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:config/redis.properties")
public class RedisConfig {
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

    /**
     *jedisPoolConfig 连接池
     **/
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //连接最小空闲时间 默认18000000毫秒（30分钟）
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //每次逐出检查时，最大数目。 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        //逐出扫描时间间隔(毫秒)如果为负数则不运行逐出线程 ，默认-1
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }
    /**
     * 默认配置（单机版）
     *
     * */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        //链接池
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);;
        //ip地址
        jedisConnectionFactory.setHostName(hostName);
        //端口号
        jedisConnectionFactory.setPort(port);
        //密码
        //jedisConnectionFactory.setPassword(password);
        //客户端超时时间，单位为毫秒
        jedisConnectionFactory.setTimeout(5000);
        return  jedisConnectionFactory;
    }
    /**
     * 实例化 redis Template对象
     */
    @Bean
    public  RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String ,Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate,factory);
        return redisTemplate;
    }
    /**
     * redis集群
     * */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String [] serverArray =  clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for(String ipPort:serverArray){
            String [] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0],Integer.parseInt(ipAndPort[1])));
        }

        redisClusterConfiguration.setClusterNodes(nodes);
        //最大集群数
        redisClusterConfiguration.setMaxRedirects(mmaxRedirectsac);
        return  redisClusterConfiguration;
    }
    /**
     *配置集群工厂
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig,RedisClusterConfiguration redisClusterConfiguration){
        return  new JedisConnectionFactory(redisClusterConfiguration,poolConfig);
    }
    /**
     * redis哨兵模式
     * */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置master的名称
        RedisNode redisNode = new RedisNode(hostName,port);
        redisNode.setName("myMaster");
        //配置主节点
        redisSentinelConfiguration.master(redisNode);
        //配置redis的哨兵sentinel
        //设置node节点集合
        RedisNode senRedisNode = new RedisNode(senHost1,senPort1);
        Set<RedisNode> redisNodeSet = new HashSet<>();
        redisNodeSet.add(senRedisNode);
        //设置哨兵
        redisSentinelConfiguration.setSentinels(redisNodeSet);
        return  redisSentinelConfiguration;
    }
    /**
     * 配置哨兵工厂
     * */
    @Bean
    public  JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig ,RedisSentinelConfiguration redisSentinelConfiguration){
        return  new JedisConnectionFactory(redisSentinelConfiguration,jedisPoolConfig);
    }
    /**
     * redis 设置数据存入redis的序列化方式，并开启事务
     * @param  redisTemplate redis模板
     *                       @param redisConnectionFactory  连接工厂
     * */
    private  void initDomainRedisTemplate(RedisTemplate<String,Object> redisTemplate, RedisConnectionFactory redisConnectionFactory){
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
    }
    /**
     * 注入封装RedisTemplate
     * */
    @Bean (name = "redisUtil")
    public RedisUtil redisUtil (RedisTemplate <String ,Object> redisTemplate){
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

}
