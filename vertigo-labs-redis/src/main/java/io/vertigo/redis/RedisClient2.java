package io.vertigo.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

import io.vertigo.redis.resp.RespClient;

public final class RedisClient2 implements AutoCloseable {
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
		//---list
		blpop,
		brpop,
		brpoplpush,
		lindex,
		linsert,
		llen,
		lpop,
		lpush,
		lpushx,
		lrange,
		lrem,
		lset,
		ltrim,
		rpop,
		rpoplpush,
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

	public enum Position {
		BEFORE,
		AFTER
	}

	private final RespClient tcpClient;

	public RedisClient2(final String host, final int port) {
		tcpClient = new RespClient(host, port);
	}

	private Future<Long> execLong(final Command command, final String... args) {
		return tcpClient.execLong(command.name(), args);
	}

	private Future<Boolean> execBoolean(final Command command, final String... args) {
		return execLong(command, args) == 1;
	}

	private RunnableFuture exec(final Command command, final String... args) {
		execString(command, args);
	}

	private Future<String> execString(final Command command, final String... args) {
		return tcpClient.execString(command.name(), args);
	}

	private Future<String> execBulk(final Command command, final String... args) {
		return tcpClient.execBulk(command.name(), args);
	}

	//-------------------------------------------------------------------------
	//------------------------------list---------------------------------------
	//-------------------------------------------------------------------------
	public Future<List<String>> blpop(final long timeout, final String key, final String... keys) {
		return tcpClient.execArray(Command.blpop.name(), args(key, keys, String.valueOf(timeout)));
	}

	public List<String> brpop(final long timeout, final String key, final String... keys) {
		//timeout must be placed at the end
		return tcpClient.execArray(Command.brpop.name(), args(key, keys, String.valueOf(timeout)));
	}

	public Future<String> brpoplpush(final String source, final String destination, final long timeout) {
		return execBulk(Command.brpoplpush, source, destination, String.valueOf(timeout));
	}

	public Future<String> lindex(final String key, final int index) {
		return execBulk(Command.lindex, key, String.valueOf(index));
	}

	public Future<Long> linsert(final String key, final Position position, final String pivot, final String value) {
		return execLong(Command.linsert, key, position.name(), pivot, value);
	}

	public Future<Long> llen(final String key) {
		return execLong(Command.llen, key);
	}

	public Future<String> lpop(final String key) {
		return execBulk(Command.lpop, key);
	}

	public Future<Long> lpush(final String key, final String value, final String... values) {
		return execLong(Command.lpush, args(key, value, values));
	}

	public Future<Long> lpushx(final String key, final String value) {
		return execLong(Command.lpushx, key, value);
	}

	public List<String> lrange(final String key, final long start, final long stop) {
		return tcpClient.execArray(Command.lrange.name(), key, String.valueOf(start), String.valueOf(stop));
	}

	public Future<Long> lrem(final String key, final long count, final String value) {
		return execLong(Command.lrem, key, String.valueOf(count), value);
	}

	public void lset(final String key, final long index, final String value) {
		exec(Command.lset, key, String.valueOf(index), value);
	}

	public void ltrim(final String key, final long start, final long stop) {
		exec(Command.ltrim, key, String.valueOf(start), String.valueOf(stop));
	}

	public Future<String> rpop(final String key) {
		return execBulk(Command.rpop, key);
	}

	public Future<String> rpoplpush(final String source, final String destination) {
		return execBulk(Command.rpoplpush, source, destination);
	}

	public Future<Long> rpush(final String key, final String value, final String... values) {
		return execLong(Command.rpush, args(key, value, values));
	}

	public Future<Long> rpushx(final String key, final String value) {
		return execLong(Command.rpushx, key, value);
	}

	//-------------------------------------------------------------------------
	//------------------------------hyperLogLog--------------------------------
	//-------------------------------------------------------------------------
	// PFADD, PFCOUNT, PFMERGE
	//-------------------------------------------------------------------------
	public Future<Long> pfadd(final String key, final String element, final String... elements) {
		return execLong(Command.pfadd, args(key, element, elements));
	}

	public Future<Long> pfcount(final String key, final String... keys) {
		return execLong(Command.pfcount, args(key, keys));
	}

	public void pfmerge(final String destkey, final String sourcekey, final String... sourcekeys) {
		exec(Command.pfmerge, args(destkey, sourcekey, sourcekeys));
	}

	//-------------------------------------------------------------------------
	//-----------------------------/hyperLogLog--------------------------------
	//------------------------------hash---------------------------------------
	//-------------------------------------------------------------------------
	// HDEL, HEXISTS, HGET, HGETALL, HINCRBY, -HINCRBYFLOAT, HKEYS, HLEN
	// -HMGET -HMSET, -HSCAN, HSET, HSETNX,  HVALS
	//-------------------------------------------------------------------------
	public Future<Long> hdel(final String key, final String... fields) {
		return execLong(Command.hdel, args(key, fields));
	}

	public Future<Boolean> hexists(final String key, final String field) {
		return execBoolean(Command.hexists, key, field);
	}

	public Future<String> hget(final String key, final String field) {
		return execBulk(Command.hget, key, field);
	}

	public Map<String, String> hgetAll(final String key) {
		final List<String> values = tcpClient.execArray(Command.hgetall.name(), key);

		final Map<String, String> map = new HashMap<>();
		for (int i = 0; i < (values.size() / 2); i++) {
			map.put(values.get(2 * i), values.get(2 * i + 1));
		}
		return map;
	}

	public Future<Long> hincrBy(final String key, final String field, final long increment) {
		return execLong(Command.hincrby, key, field, String.valueOf(increment));
	}

	public Set<String> hkeys(final String key) {
		return new HashSet<>(tcpClient.execArray(Command.hkeys.name(), key));
	}

	public Future<Long> hlen(final String key) {
		return execLong(Command.hlen, key);
	}

	public Future<Boolean> hset(final String key, final String field, final String value) {
		return execBoolean(Command.hset, key, field, value);
	}

	public Future<Boolean> hsetnx(final String key, final String field, final String value) {
		return execBoolean(Command.hsetnx, key, field, value);
	}

	public void hmset(final String key, final Map<String, String> map) {
		execString(Command.hmset, args(key, map));
	}

	public List<String> hvals(final String key) {
		return tcpClient.execArray(Command.hvals.name(), key);
	}

	//-------------------------------------------------------------------------
	//-----------------------------/hash---------------------------------------
	//-------------------------------------------------------------------------

	public Future<Long> append(final String key, final String value) {
		return execLong(Command.append, key, value);
	}

	public Future<String> get(final String key) {
		return execBulk(Command.get, key);
	}

	public Future<String> set(final String key, final String value) {
		return execString(Command.set, key, value);
	}

	public Future<Boolean> exists(final String key) {
		return execBoolean(Command.exists, key);
	}

	public Future<Boolean> expire(final String key, final long seconds) {
		return execBoolean(Command.expire, key, String.valueOf(seconds));
	}

	public Future<Long> del(final String... keys) {
		return execLong(Command.del, keys);
	}

	public void flushAll() {
		exec(Command.flushall);
	}

	public Future<String> ping() {
		return execString(Command.ping);
	}

	public Future<String> echo(final String message) {
		return execBulk(Command.echo, message);
	}

	public Future<String> auth(final String password) {
		return execString(Command.auth, password);
	}

	public Future<Long> sadd(final String key, final String... members) {
		return execLong(Command.sadd, args(key, members));
	}

	public Future<String> spop(final String key) {
		return execBulk(Command.pop, key);
	}

	public Object eval(final String script) {
		return tcpClient.execEval(Command.eval.name(), script, String.valueOf(0));
	}

	//-------------------------------------------------------------------------
	//close is overrided to avoid exception
	@Override
	public void close() {
		tcpClient.close();
	}

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

	private static String[] args(final String key, final String value, final String[] values) {
		final String[] args = new String[values.length + 2];
		args[0] = key;
		args[1] = value;
		System.arraycopy(values, 0, args, 2, values.length);
		return args;
	}

	private static String[] args(final String key, final String[] values, final String value) {
		final String[] args = new String[values.length + 2];
		args[0] = key;
		System.arraycopy(values, 0, args, 1, values.length);
		args[values.length + 1] = value;
		return args;
	}

	private static String[] args(final String key, final String[] values) {
		final String[] args = new String[values.length + 1];
		args[0] = key;
		System.arraycopy(values, 0, args, 1, values.length);
		return args;
	}
}
