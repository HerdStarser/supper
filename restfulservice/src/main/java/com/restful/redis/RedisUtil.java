package com.restful.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisUtil {


    private Jedis jedis;

    public RedisUtil(Jedis jedis) {
        this.jedis = jedis;
    }

    public Long srem(String key, String... members) {
        return jedis.srem(key, members);
    }

    public Set<String> smembers(String key) {
        return jedis.smembers(key);
    }

    public Long sadd(String key, String... members) {
        return jedis.sadd(key, members);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    public String setex(String key, int seconds, String value) {
        return jedis.setex(key, seconds, value);
    }

    public Long setnx(String key, String value) {
        return jedis.setnx(key, value);
    }

    public String getSet(String key, String value) {
        return jedis.getSet(key, value);
    }

    public Long del(String key) {
        return jedis.del(key);
    }

    public Long expire(String key, int seconds) {
        return jedis.expire(key, seconds);
    }

    public Long zrem(String key,
                     String... members) {
        return jedis.zrem(key, members);
    }

    public Long zadd(String key, String member){
        return jedis.zadd(key, 1, member);
    }

    public Long zadd(String key, double score, String member){
        return jedis.zadd(key, score, member);
    }

    public Long zcard(String key){
        return jedis.zcard(key);
    }

    public Set<String> zrange(String key, long start, long end){
        return jedis.zrange(key, start, end);
    }

    public Set<String> zrevrange(String key, long start, long end){
        return jedis.zrevrange(key, start, end);
    }

    public Boolean exists(String key){
        return jedis.exists(key);
    }

    public Long ttl(String key) {
        return jedis.ttl(key);
    }

    public Boolean sismember(String key, String member){
        return jedis.sismember(key, member);
    }

    public Long incr(String key){
        return jedis.incr(key);
    }

    public Long decr(String key){
        return jedis.decr(key);
    }

}
