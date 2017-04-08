package io.vertigo.redis.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.vertigo.redis.RedisClient;
import io.vertigo.redis.impl.resp.RespClient;

public final class RedisClientImpl implements RedisClient {
	enum Command {
		//--- Keys
		del,
		//		dump,
		exists,
		expire,
		//		expireat,
		//		keys,
		//		migrate
		//		move,
		//		object,
		//		persist,
		//		pexpire
		//		pexpireat
		//		pttl,
		//		randomkey
		//		rename
		//		renamenx
		//		restore
		//		sort
		//		touch
		//		ttl
		//		type
		//		unlink
		//		wait
		//		scan
		//---
		blpop,
		brpop,
		brpoplpush,
		lindex,
		llen,
		lpop,
		lpush,
		lpushx,
		lrange,
		lrem,
		rpop,
		rpush,
		rpushx,
		//---
		pfadd,
		pfcount,
		pfmerge,
		//---
		hdel,
		hexists,
		hget,
		hgetall,
		hincrby,
		hkeys,
		hlen,
		hset,
		hsetnx,
		hmset,
		hvals,
		//---connection
		auth,
		echo,
		ping,
		//		quit, 
		//		select
		//		swapdb
		//---
		append,
		get,
		set,
		flushall,
		sadd,
		pop,
		eval
	}

	private final RespClient tcpClient;

	public RedisClientImpl(final String host, final int port) {
		tcpClient = new RespClient(host, port);
	}

	//-------------------------------------------------------------------------
	//------------------------------list---------------------------------------
	//-------------------------------------------------------------------------
	//	-LINSERT
	//	 -LSET, -LTRIM, RPOP, -RPOPLPUSH
	//-------------------------------------------------------------------------
	@Override
	public List<String> blpop(final long timeout, final String... keys) {
		return tcpClient.execArray(Command.blpop.name(), args(timeout, keys));
	}

	@Override
	public List<String> brpop(final long timeout, final String... keys) {
		return tcpClient.execArray(Command.brpop.name(), args(timeout, keys));
	}

	@Override
	public String brpoplpush(final String source, final String destination, final long timeout) {
		return tcpClient.execBulk(Command.brpoplpush.name(), source, destination, String.valueOf(timeout));
	}

	@Override
	public String lindex(final String key, final int index) {
		return tcpClient.execBulk(Command.lindex.name(), key, String.valueOf(index));
	}

	@Override
	public long llen(final String key) {
		return tcpClient.execLong(Command.llen.name(), key);
	}

	@Override
	public String lpop(final String key) {
		return tcpClient.execBulk(Command.lpop.name(), key);
	}

	@Override
	public long lpush(final String key, final String value) {
		return tcpClient.execLong(Command.lpush.name(), key, value);
	}

	@Override
	public long lpushx(final String key, final String value) {
		return tcpClient.execLong(Command.lpushx.name(), key, value);
	}

	@Override
	public List<String> lrange(final String key, final long start, final long stop) {
		return tcpClient.execArray(Command.lrange.name(), key, String.valueOf(start), String.valueOf(stop));
	}

	@Override
	public long lrem(final String key, final long count, final String value) {
		return tcpClient.execLong(Command.lrem.name(), key, String.valueOf(count), value);
	}

	@Override
	public String rpop(final String key) {
		return tcpClient.execBulk(Command.rpop.name(), key);
	}

	@Override
	public long rpush(final String key, final String value) {
		return tcpClient.execLong(Command.rpush.name(), key, value);
	}

	@Override
	public long rpushx(final String key, final String value) {
		return tcpClient.execLong(Command.rpushx.name(), key, value);
	}

	//-------------------------------------------------------------------------
	//-----------------------------/list---------------------------------------
	//------------------------------hyperLogLog--------------------------------
	//-------------------------------------------------------------------------
	// PFADD, PFCOUNT, PFMERGE
	//-------------------------------------------------------------------------
	@Override
	public long pfadd(final String key, final String... elements) {
		return tcpClient.execLong(Command.pfadd.name(), args(key, elements));
	}

	@Override
	public long pfcount(final String... keys) {
		return tcpClient.execLong(Command.pfcount.name(), keys);
	}

	@Override
	public void pfmerge(final String destkey, final String... sourcekeys) {
		tcpClient.execString(Command.pfmerge.name(), args(destkey, sourcekeys));
	}

	//-------------------------------------------------------------------------
	//-----------------------------/hyperLogLog--------------------------------
	//------------------------------hash---------------------------------------
	//-------------------------------------------------------------------------
	// HDEL, HEXISTS, HGET, HGETALL, HINCRBY, -HINCRBYFLOAT, HKEYS, HLEN
	// -HMGET -HMSET, -HSCAN, HSET, HSETNX,  HVALS
	//-------------------------------------------------------------------------
	@Override
	public long hdel(final String key, final String... fields) {
		return tcpClient.execLong(Command.hdel.name(), args(key, fields));
	}

	@Override
	public boolean hexists(final String key, final String field) {
		return tcpClient.execBoolean(Command.hexists.name(), key, field);
	}

	@Override
	public String hget(final String key, final String field) {
		return tcpClient.execBulk(Command.hget.name(), key, field);
	}

	@Override
	public Map<String, String> hgetAll(final String key) {
		final List<String> values = tcpClient.execArray(Command.hgetall.name(), key);
		final Map<String, String> map = new HashMap<>();
		for (int i = 0; i < (values.size() / 2); i++) {
			map.put(values.get(2 * i), values.get(2 * i + 1));
		}
		return map;
	}

	@Override
	public long hincrBy(final String key, final String field, final long increment) {
		return tcpClient.execLong(Command.hincrby.name(), key, field, String.valueOf(increment));
	}

	@Override
	public Set<String> hkeys(final String key) {
		return new HashSet<>(tcpClient.execArray(Command.hkeys.name(), key));
	}

	@Override
	public long hlen(final String key) {
		return tcpClient.execLong(Command.hlen.name(), key);
	}

	@Override
	public boolean hset(final String key, final String field, final String value) {
		return tcpClient.execBoolean(Command.hset.name(), key, field, value);
	}

	@Override
	public boolean hsetnx(final String key, final String field, final String value) {
		return tcpClient.execBoolean(Command.hsetnx.name(), key, field, value);
	}

	@Override
	public void hmset(final String key, final Map<String, String> map) {
		final String[] args = args(key, map);
		tcpClient.execString(Command.hmset.name(), args);
	}

	@Override
	public List<String> hvals(final String key) {
		return tcpClient.execArray(Command.hvals.name(), key);
	}

	//-------------------------------------------------------------------------
	//-----------------------------/hash---------------------------------------
	//-------------------------------------------------------------------------

	@Override
	public long append(final String key, final String value) {
		return tcpClient.execLong(Command.append.name(), key, value);
	}

	@Override
	public String get(final String key) {
		return tcpClient.execBulk(Command.get.name(), key);
	}

	@Override
	public String set(final String key, final String value) {
		return tcpClient.execString(Command.set.name(), key, value);
	}

	@Override
	public boolean exists(final String key) {
		return tcpClient.execBoolean(Command.exists.name(), key);
	}

	@Override
	public boolean expire(final String key, final long seconds) {
		return tcpClient.execBoolean(Command.expire.name(), key, String.valueOf(seconds));
	}

	@Override
	public long del(final String... keys) {
		return tcpClient.execLong(Command.del.name(), keys);
	}

	@Override
	public void flushAll() {
		tcpClient.execString(Command.flushall.name());
	}

	@Override
	public String ping() {
		return tcpClient.execString(Command.ping.name());
	}

	@Override
	public String echo(final String message) {
		return tcpClient.execBulk(Command.echo.name(), message);
	}

	@Override
	public String auth(final String password) {
		return tcpClient.execString(Command.auth.name(), password);
	}

	@Override
	public long sadd(final String key, final String... members) {
		return tcpClient.execLong(Command.sadd.name(), args(key, members));
	}

	@Override
	public String spop(final String key) {
		return tcpClient.execBulk(Command.pop.name(), key);
	}

	@Override
	public Object eval(final String script) {
		return tcpClient.execEval(Command.eval.name(), script, String.valueOf(0));
	}

	@Override
	public void close() {
		tcpClient.close();
	}

	//------------------

	private static String[] args(final String key, final Map<String, String> map) {
		final String[] args = new String[map.size() * 2 + 1];
		int i = 0;
		args[i++] = key;
		for (final Entry<String, String> entry : map.entrySet()) {
			args[i++] = entry.getKey();
			args[i++] = entry.getValue();
		}
		return args;
	}

	private static String[] args(final String key, final String... values) {
		final String[] args = new String[values.length + 1];
		args[0] = key;
		for (int i = 0; i < values.length; i++) {
			args[i + 1] = values[i];
		}
		return args;
	}

	private static String[] args(final long timeout, final String... keys) {
		final String[] args = new String[keys.length + 1];
		for (int i = 0; i < keys.length; i++) {
			args[i] = keys[i];
		}
		args[keys.length] = String.valueOf(timeout);
		return args;
	}
}
