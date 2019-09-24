package com.lhuang.client.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService<K,V> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24L;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;

    /**
     * 对hash类型的数据操作
     *
     * @param
     * @return
     */
    public void hmSet(K key,K hashKey,V value) {
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public V hmGet(K key,K hashKey){
        return (V) redisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * 添加字符型数据
     *
     * @param
     * @return
     */
    public void setValue(K key,V value) {
        redisTemplate.opsForValue().set(key,value);
    }


    /**
     * 添加字符型数据并设置缓存时长
     *
     * @param
     * @return
     */
    public boolean setValue(K key,V value,Long time,TimeUnit timeUnit) {
        setValue(key,value);
        return expireKey(key,time,timeUnit);

    }
    /**
     * 读取字符型数据
     *
     * @param key
     * @return
     */
    public V getValue(final K key){
        return (V) redisTemplate.opsForValue().get(key);
    }

    /**
     * 对列表数据添加操作
     *
     * @param
     * @return
     */
    public void setList(K key,V value){
        redisTemplate.opsForList().rightPush(key,value);
    }

    /**
     * 对列表数据批量添加操作
     *
     * @param
     * @return
     */
    public void setList(K key,Collection<Object> collection) {
        redisTemplate.opsForList().leftPushAll(key,collection);
    }

    /**
     * 列表获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<V> getList(K key, long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }


    /**
     * 添加无序集合
     *
     * @param
     * @return
     */
    public void addSet(K key,V value) {
        redisTemplate.opsForSet().add(key,value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<V> setMembers(K key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(K key, V value, double score) {
         redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoreStart
     * @param scoreEnd
     * @return
     */
    public Set<V> rangeByScore(K key, double scoreStart, double scoreEnd) {
        return redisTemplate.opsForZSet().rangeByScore(key, scoreStart, scoreEnd);
    }

    /*判断key是否存在*/
    public boolean existsKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 通过前缀头，获取所有的redis缓存信息
     *
     * @param keyHeader
     * @return
     */
    public Set<K> getKeys(K keyHeader) {
        return redisTemplate.keys(keyHeader + "*");
    }

    /**
     *  通过前缀，删除所有带有该前缀的redis缓存
     * @param keyHeader
     */
    public void removeKeys(K keyHeader) {
        Set<K> keySet = this.getKeys(keyHeader);
        if (keySet != null && !keySet.isEmpty()) {
            for (K key : keySet) {
                this.deleteKey(key);
            }
        }
    }


    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }
    /**
     * 删除key
     *
     * @param key
     * @return 删除成功返回true
     */
    public Boolean deleteKey(K key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void deleteKeysByPattern(final K pattern) {
        Set<K> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            deleteKeys(keys);

        }
    }

    /**
     * 批量删除key
     *
     * @param keys
     * @return 返回删除的数量
     */
    public Long deleteKeys(Set<K> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public Boolean expireKey(K key, long time, TimeUnit timeUnit) {
        return redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 压栈
     *
     * @param key
     * @param value
     * @return
     */
    public Long push(K key, V value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 出栈
     *
     * @param key
     * @return
     */
    public V pop(K key) {
        return (V) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    public Long inQueue(K key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    public V outQueue(K key) {
        return (V)redisTemplate.opsForList().leftPop(key);
    }


}

