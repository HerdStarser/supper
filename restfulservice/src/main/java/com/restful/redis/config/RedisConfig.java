package com.restful.redis.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.redis.RedisClusterUtil;
import com.restful.redis.RedisUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
@ConditionalOnProperty(
        prefix = "my.config.redis",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class RedisConfig {

    @Resource
    RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory getFactory(){
        Boolean isCluster = redisProperties.getIsCluster();
        JedisConnectionFactory factory = null;
        if(isCluster){
            //集群配置
            RedisClusterConfiguration configuration = new RedisClusterConfiguration();
            HashSet<RedisNode> hashSet =new HashSet<>();
            for (String host:redisProperties.getHosts().split(",")){
                String[] ip_port = host.split(":");
                hashSet.add(new RedisNode(ip_port[0],Integer.valueOf(ip_port[1])));
            }
            configuration.setClusterNodes(hashSet);
            configuration.setMaxRedirects(redisProperties.getMaxRedirects());
            factory = new JedisConnectionFactory(configuration);
            //factory.setUsePool(true);
            if(redisProperties.getPassword() != null){
                factory.setPassword(redisProperties.getPassword());
            }
            factory.afterPropertiesSet();
        }else{
            //单机配置
            factory = new JedisConnectionFactory();
            factory.setHostName(redisProperties.getHost());
            factory.setPort(redisProperties.getPort());
            factory.setTimeout(redisProperties.getTimeOut());
            factory.setUsePool(true);
            if(redisProperties.getPassword() != null){
                factory.setPassword(redisProperties.getPassword());
            }

        }
        return factory;
    }

    @Bean
    @ConditionalOnProperty( prefix = "my.config.redis",
            name = {"isCluster"},
            havingValue = "false",
            matchIfMissing = true)
    public RedisUtil redisUtil(){
        JedisPool jedisPool = new JedisPool(redisProperties.getHost(),redisProperties.getPort());
        Jedis jedis = jedisPool.getResource();
        if(redisProperties.getPassword() != null){
            jedis.auth(redisProperties.getPassword());
        }
        return new RedisUtil(jedis);
    }

    @Bean
    @ConditionalOnProperty( prefix = "my.config.redis",
            name = {"isCluster"},
            havingValue = "true",
            matchIfMissing = true)
    public RedisClusterUtil redisShardUtil(){
        Set<HostAndPort> host_ports = new HashSet<>();
        for (String host:redisProperties.getHosts().split(",")){
            String[] ip_port = host.split(":");
            host_ports.add(new HostAndPort(ip_port[0],Integer.valueOf(ip_port[1])));
        }
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(redisProperties.getMaxTotal());
        config.setMinIdle(redisProperties.getMinIdle());
        config.setMaxIdle(redisProperties.getMaxIdle());
        config.setTestOnBorrow(false);
        config.setTestOnCreate(true);
        JedisCluster jedisCluster = new JedisCluster(host_ports,redisProperties.getConnectionTimeout(),redisProperties.getTimeOut(),redisProperties.getMaxAttempts(),redisProperties.getPassword(),config);
        return new RedisClusterUtil(jedisCluster);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer());
        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> serializer(){
        final Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public CacheManager redisCacheManager(RedisTemplate redisTemplate){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        DefaultRedisCachePrefix defaultRedisCachePrefix = new DefaultRedisCachePrefix();
        redisCacheManager.setCachePrefix(defaultRedisCachePrefix);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setDefaultExpiration(redisProperties.getDefaultExpiration());
        return redisCacheManager;
    }
}
