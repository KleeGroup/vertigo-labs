package io.vertigo.nitro.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisClient extends AutoCloseable {
	//-------------------------------------------------------------------------
	//------------------------------list---------------------------------------
	//-------------------------------------------------------------------------
	//	BLPOP, BRPOP, BRPOPLPUSH, LINDEX, -LINSERT, LLEN, LPOP
	//	LPUSH, LPUSHX, LRANGE, LREM, -LSET, -LTRIM, RPOP, -RPOPLPUSH, RPUSH, RPUSHX
	//-------------------------------------------------------------------------
	List<String> blpop(long timeout, String... keys);

	List<String> brpop(long timeout, String... keys);

	String brpoplpush(String source, String destination, long timeout);

	String lindex(String key, int index);

	long llen(String key);

	String lpop(String key);

	long lpush(String key, String value);

	long lpushx(String key, String value);

	List<String> lrange(String key, long start, long stop);

	long lrem(String key, long count, String value);

	String rpop(String key);

	long rpush(String key, String value);

	long rpushx(String key, String value);

	//-------------------------------------------------------------------------
	//-----------------------------/list---------------------------------------
	//------------------------------hyperLogLog--------------------------------
	//-------------------------------------------------------------------------
	// PFADD, PFCOUNT, PFMERGE
	//-------------------------------------------------------------------------
	long pfadd(String key, String... elements);

	long pfcount(String... keys);

	void pfmerge(String destkey, String... sourcekeys);

	//-------------------------------------------------------------------------
	//-----------------------------/hyperLogLog--------------------------------
	//------------------------------hash---------------------------------------
	//-------------------------------------------------------------------------
	// HDEL, HEXISTS, HGET, HGETALL, HINCRBY, -HINCRBYFLOAT, HKEYS, HLEN
	// -HMGET -HMSET, -HSCAN, HSET, HSETNX,  HVALS
	//-------------------------------------------------------------------------
	long hdel(String key, String... fields);

	boolean hexists(String key, String field);

	String hget(String key, String field);

	Map<String, String> hgetAll(String key);

	long hincrBy(String key, String field, long increment);

	Set<String> hkeys(String key);

	long hlen(String key);

	boolean hset(String key, String field, String value);

	boolean hsetnx(String key, String field, String value);

	void hmset(String key, Map<String, String> map);

	List<String> hvals(String key);

	long append(String key, String value);

	String get(String key);

	String set(String key, String value);

	boolean exists(String key);

	boolean expire(String key, long seconds);

	long del(String... keys);

	void flushAll();

	String ping();

	String echo(String message);

	String auth(String password);

	long sadd(String key, String... members);

	String spop(String key);

	Object eval(String script);

	//-------------------------------------------------------------------------
	//close is overrided to avoid exception
	void close();
}
