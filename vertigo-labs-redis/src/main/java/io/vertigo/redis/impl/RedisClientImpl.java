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

	private final RespClient tcpClient;

	public RedisClientImpl(final String host, final int port) {
		tcpClient = new RespClient(host, port);
	}

	private long execLong(final Command command, final String... args) {
		return tcpClient.execLong(command.name(), args);
	}

	private boolean execBoolean(final Command command, final String... args) {
		return execLong(command, args) == 1;
	}

	private String execString(final Command command, final String... args) {
		return tcpClient.execString(command.name(), args);
	}

	private String execBulk(final Command command, final String... args) {
		return tcpClient.execBulk(command.name(), args);
	}

	//-------------------------------------------------------------------------
	//------------------------------list---------------------------------------
	//-------------------------------------------------------------------------
	//	-LINSERT
	//	 -LSET, -LTRIM, RPOP, -RPOPLPUSH
	//-------------------------------------------------------------------------
	@Override
	public List<String> blpop(final long timeout, final String key, final String... keys) {
		return tcpClient.execArray(Command.blpop.name(), args(key, keys, String.valueOf(timeout)));
	}

	@Override
	public List<String> brpop(final long timeout, final String key, final String... keys) {
		return tcpClient.execArray(Command.brpop.name(), args(key, keys, String.valueOf(timeout)));
	}

	@Override
	public String brpoplpush(final String source, final String destination, final long timeout) {
		return execBulk(Command.brpoplpush, source, destination, String.valueOf(timeout));
	}

	@Override
	public String lindex(final String key, final int index) {
		return execBulk(Command.lindex, key, String.valueOf(index));
	}

	@Override
	public long linsert(final String key, final Position position, final String pivot, final String value) {
		return execLong(Command.linsert, key, position.name(), pivot, value);
	}

	@Override
	public long llen(final String key) {
		return execLong(Command.llen, key);
	}

	@Override
	public String lpop(final String key) {
		return execBulk(Command.lpop, key);
	}

	@Override
	public long lpush(final String key, final String value, final String... values) {
		return execLong(Command.lpush, args(key, value, values));
	}

	@Override
	public long lpushx(final String key, final String value) {
		return execLong(Command.lpushx, key, value);
	}

	@Override
	public List<String> lrange(final String key, final long start, final long stop) {
		return tcpClient.execArray(Command.lrange.name(), key, String.valueOf(start), String.valueOf(stop));
	}

	@Override
	public long lrem(final String key, final long count, final String value) {
		return execLong(Command.lrem, key, String.valueOf(count), value);
	}

	@Override
	public void lset(final String key, final long index, final String value) {
		execString(Command.lset, key, String.valueOf(index), value);
	}

	@Override
	public void ltrim(final String key, final long start, final long stop) {
		execString(Command.ltrim, key, String.valueOf(start), String.valueOf(stop));
	}

	@Override
	public String rpop(final String key) {
		return execBulk(Command.rpop, key);
	}

	@Override
	public long rpush(final String key, final String value, final String... values) {
		return execLong(Command.rpush, args(key, value, values));
	}

	@Override
	public long rpushx(final String key, final String value) {
		return execLong(Command.rpushx, key, value);
	}

	//-------------------------------------------------------------------------
	//-----------------------------/list---------------------------------------
	//------------------------------hyperLogLog--------------------------------
	//-------------------------------------------------------------------------
	// PFADD, PFCOUNT, PFMERGE
	//-------------------------------------------------------------------------
	@Override
	public long pfadd(final String key, final String... elements) {
		return execLong(Command.pfadd, args(key, elements));
	}

	@Override
	public long pfcount(final String... keys) {
		return execLong(Command.pfcount, keys);
	}

	@Override
	public void pfmerge(final String destkey, final String... sourcekeys) {
		execString(Command.pfmerge, args(destkey, sourcekeys));
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
		return execLong(Command.hdel, args(key, fields));
	}

	@Override
	public boolean hexists(final String key, final String field) {
		return execBoolean(Command.hexists, key, field);
	}

	@Override
	public String hget(final String key, final String field) {
		return execBulk(Command.hget, key, field);
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
		return execLong(Command.hincrby, key, field, String.valueOf(increment));
	}

	@Override
	public Set<String> hkeys(final String key) {
		return new HashSet<>(tcpClient.execArray(Command.hkeys.name(), key));
	}

	@Override
	public long hlen(final String key) {
		return execLong(Command.hlen, key);
	}

	@Override
	public boolean hset(final String key, final String field, final String value) {
		return execBoolean(Command.hset, key, field, value);
	}

	@Override
	public boolean hsetnx(final String key, final String field, final String value) {
		return execBoolean(Command.hsetnx, key, field, value);
	}

	@Override
	public void hmset(final String key, final Map<String, String> map) {
		final String[] args = args(key, map);
		execString(Command.hmset, args);
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
		return execLong(Command.append, key, value);
	}

	@Override
	public String get(final String key) {
		return execBulk(Command.get, key);
	}

	@Override
	public String set(final String key, final String value) {
		return execString(Command.set, key, value);
	}

	@Override
	public boolean exists(final String key) {
		return execBoolean(Command.exists, key);
	}

	@Override
	public boolean expire(final String key, final long seconds) {
		return execBoolean(Command.expire, key, String.valueOf(seconds));
	}

	@Override
	public long del(final String... keys) {
		return execLong(Command.del, keys);
	}

	@Override
	public void flushAll() {
		execString(Command.flushall);
	}

	@Override
	public String ping() {
		return execString(Command.ping);
	}

	@Override
	public String echo(final String message) {
		return execBulk(Command.echo, message);
	}

	@Override
	public String auth(final String password) {
		return execString(Command.auth, password);
	}

	@Override
	public long sadd(final String key, final String... members) {
		return execLong(Command.sadd, args(key, members));
	}

	@Override
	public String spop(final String key) {
		return execBulk(Command.pop, key);
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

	private static String[] args(final String key, final String value, final String[] values) {
		final String[] args = new String[values.length + 2];
		args[0] = key;
		args[1] = value;
		for (int i = 0; i < values.length; i++) {
			args[i + 2] = values[i];
		}
		return args;
	}

	private static String[] args(final String key, final String[] values, final String value) {
		final String[] args = new String[values.length + 2];
		args[0] = key;
		for (int i = 0; i < values.length; i++) {
			args[i + 1] = values[i];
		}
		args[values.length + 1] = value;
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
}
