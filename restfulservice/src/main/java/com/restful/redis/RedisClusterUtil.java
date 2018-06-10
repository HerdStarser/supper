package com.restful.redis;

import redis.clients.jedis.JedisCluster;

import java.util.Set;

public class RedisClusterUtil {

    JedisCluster cluster;

    public RedisClusterUtil(JedisCluster cluster) {
        this.cluster = cluster;
    }

    public Long srem(String key, String... members) {
        return cluster.srem(key, members);
    }

    public Set<String> smembers(String key) {
        return cluster.smembers(key);
    }

    public Long sadd(String key, String... members) {
        return cluster.sadd(key, members);
    }

    public String get(String key) {
        return cluster.get(key);
    }

    public String set(String key, String value) {
        return cluster.set(key, value);
    }

    public String setex(String key, int seconds, String value) {
        return cluster.setex(key, seconds, value);
    }

    public Long setnx(String key, String value) {
        return cluster.setnx(key, value);
    }

    public String getSet(String key, String value) {
        return cluster.getSet(key, value);
    }

    public Long del(String key) {
        return cluster.del(key);
    }

    public Long expire(String key, int seconds) {
        return cluster.expire(key, seconds);
    }

    public Long zrem(String key,
                     String... members) {
        return cluster.zrem(key, members);
    }

    public Long zadd(String key, String member){
        return cluster.zadd(key, 1, member);
    }

    public Long zadd(String key, double score, String member){
        return cluster.zadd(key, score, member);
    }

    public Long zcard(String key){
        return cluster.zcard(key);
    }

    public Set<String> zrange(String key, long start, long end){
        return cluster.zrange(key, start, end);
    }

    public Set<String> zrevrange(String key, long start, long end){
        return cluster.zrevrange(key, start, end);
    }

    public Boolean exists(String key){
        return cluster.exists(key);
    }

    public Long ttl(String key) {
        return cluster.ttl(key);
    }

    public Boolean sismember(String key, String member){
        return cluster.sismember(key, member);
    }

    public Long incr(String key){
        return cluster.incr(key);
    }

    public Long decr(String key){
        return cluster.decr(key);
    }
}
