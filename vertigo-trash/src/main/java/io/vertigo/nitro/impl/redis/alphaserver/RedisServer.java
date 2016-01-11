package io.vertigo.nitro.impl.redis.alphaserver;

import io.vertigo.nitro.impl.redis.resp.RespCommand;
import io.vertigo.nitro.impl.redis.resp.RespCommandHandler;
import io.vertigo.nitro.impl.redis.resp.RespServer;
import io.vertigo.nitro.impl.redis.resp.RespWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public final class RedisServer implements RespCommandHandler {
	private final RespServer respServer;
	private final Map<String, Map<String, String>> hashes = new HashMap<>();

	private final List<RespCommand> multi = null;
	private final Map<String, String> map = new HashMap<>();
	private final Map<String, BlockingDeque<String>> lists = new HashMap<>();

	public RedisServer(final int port) {
		respServer = new RespServer(port, this);
	}

	public void start() {
		new Thread(respServer).start();
	}

	@Override
	public void onCommand(final RespWriter writer, final RespCommand command) throws IOException {
		System.out.println("--- command:" + command.getName());
		for (final String arg : command.args()) {
			System.out.println("   |-arg :" + arg);
		}
		if (multi != null && !"exec".equals(command.getName())) {
			multi.add(command);
			return;
		}
		switch (command.getName().toLowerCase()) {
		//-------------------------Connection----------------------------------
			case "ping":
				writer.writeSimpleString("PONG");
				break;
			case "echo":
				final String message = command.args()[0];
				writer.writeBulkString(message);
				break;
			//-------------------------Hash------------------------------------
			case "hlen": {
				final String key = command.args()[0];
				final Map<String, String> hash = hashes.get(key);
				writer.writeLong(1L * (hash == null ? 0 : hash.size()));
			}
				break;
			case "hmset": {
				final String key = command.args()[0];
				final Map<String, String> newHash = new HashMap<>();
				for (int i = 1; i < command.args().length; i = i + 2) {
					newHash.put(command.args()[i], command.args()[i + 1]);
				}
				final Map<String, String> hash = hashes.get(key);
				if (hash != null) {
					hash.putAll(newHash);
				} else {
					hashes.put(key, newHash);
				}
				writer.writeSimpleString("OK");
			}
				break;
			case "hget": {
				final String key = command.args()[0];
				final String field = command.args()[1];
				final Map<String, String> hash = hashes.get(key);
				writer.writeBulkString(hash == null ? null : hash.get(field));
			}
				break;
			case "hexists": {
				final String key = command.args()[0];
				final String field = command.args()[1];
				final Map<String, String> hash = hashes.get(key);
				writer.writeLong(hash == null ? 0L : hash.containsKey(field) ? 1L : 0L);
			}
				break;
			case "hdel": {
				final String key = command.args()[0];
				final Map<String, String> hash = hashes.get(key);
				long deleted = 0L;
				if (hash != null) {
					for (int i = 1; i < command.args().length; i++) {
						final String field = command.args()[i];
						if (hash.containsKey(field)) {
							hash.remove(field);
							deleted++;
						}
					}
				}

				writer.writeLong(deleted);
			}
				break;
			case "hincrby": {
				final String key = command.args()[0];
				final String field = command.args()[1];
				final long increment = Long.valueOf(command.args()[2]);
				Map<String, String> hash = hashes.get(key);
				if (hash == null) {
					hash = new HashMap<>();
					hashes.put(key, hash);
				}
				if (!hash.containsKey(field)) {
					hash.put(field, "0");
				}
				final long value = Long.valueOf(hash.get(field)) + increment;
				hash.put(field, "" + value);

				writer.writeLong(value);
			}
				break;
			case "hsetnx": {
				final String key = command.args()[0];
				final String field = command.args()[1];
				final String value = command.args()[2];
				long added = 0;
				Map<String, String> hash = hashes.get(key);
				if (hash == null) {
					hash = new HashMap<>();
					hashes.put(key, hash);
				}
				if (!hash.containsKey(field)) {
					hash.put(field, value);
					added = 1;
				}
				writer.writeLong(added);
			}
				break;
			case "hset": {
				final String key = command.args()[0];
				final String field = command.args()[1];
				final String value = command.args()[2];
				long newField = 0;
				Map<String, String> hash = hashes.get(key);
				if (hash == null) {
					hash = new HashMap<>();
					hashes.put(key, hash);
				}
				if (!hash.containsKey(field)) {
					newField = 1;
				}
				hash.put(field, value);

				writer.writeLong(newField);
			}
				break;
			//-------------------------Hash------------------------------------
			case "set": {
				final String key = command.args()[0];
				map.put(key, command.args()[1]);
				writer.writeSimpleString("OK");
			}
				break;
			case "get": {
				final String key = command.args()[0];
				final String value = map.get(key);
				writer.writeBulkString(value);
			}
				break;
			case "exists": {
				final String key = command.args()[0];
				final boolean exists = map.containsKey(key) || lists.containsKey(key);
				writer.writeLong(exists ? 1L : 0L);
			}
				break;
			case "brpoplpush": {
				final String key = command.args()[0];
				final String key2 = command.args()[1];
				// long key2 = command.args()[2]==;

				final BlockingDeque<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					writer.writeBulkString(null);
				} else {
					String element;
					try {
						element = list.pollFirst(1L, TimeUnit.SECONDS);
					} catch (final InterruptedException e) {
						element = null;
					}
					if (element != null) {
						BlockingDeque<String> list2 = lists.get(key2);
						if (list2 == null) {
							list2 = new LinkedBlockingDeque<>();
							lists.put(key2, list2);
						}
						list2.addFirst(element);
					}
					writer.writeBulkString(element);
				}
			}
				break;
			case "llen": {
				final String key = command.args()[0];
				final BlockingDeque<String> list = lists.get(key);
				writer.writeLong(list == null ? 0L : list.size());
			}
				//			{
				//				final String key = command.args()[0];
				//				final List<String> list = lists.get(key);
				//				RespProtocol.writeLong(out, list == null ? 0L : list.size());
				//			}
				break;
			case "lpop": {
				final String key = command.args()[0];
				final BlockingDeque<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					writer.writeBulkString(null);
				} else {
					writer.writeBulkString(list.removeFirst());
				}
			}
				break;
			case "rpop": {
				final String key = command.args()[0];
				final BlockingDeque<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					writer.writeBulkString(null);
				} else {
					writer.writeBulkString(list.removeLast());
				}
			}
				break;
			case "lpush": {
				final String key = command.args()[0];
				BlockingDeque<String> list = lists.get(key);
				if (list == null) {
					list = new LinkedBlockingDeque<>();
					lists.put(key, list);
				}
				for (int i = 1; i < command.args().length; i++) {
					list.addFirst(command.args()[i]);
				}
				writer.writeLong(1L * list.size());
			}
				break;
			case "rpush": {
				final String key = command.args()[0];
				BlockingDeque<String> list = lists.get(key);
				if (list == null) {
					list = new LinkedBlockingDeque<>();
					lists.put(key, list);
				}
				for (int i = 1; i < command.args().length; i++) {
					list.add(command.args()[i]);
				}
				writer.writeLong(1L * list.size());
			}
				break;
			case "lpushx": {
				final String key = command.args()[0];
				final BlockingDeque<String> list = lists.get(key);
				if (list != null) {
					for (int i = 1; i < command.args().length; i++) {
						list.addFirst(command.args()[i]);
					}
				}
				writer.writeLong(list == null ? 0L : list.size());
			}
				break;
			case "rpushx": {
				final String key = command.args()[0];
				final BlockingDeque<String> list = lists.get(key);
				if (list != null) {
					for (int i = 1; i < command.args().length; i++) {
						list.add(command.args()[i]);
					}
				}
				System.out.println(">>rpushx key:" + key + " , list:" + list);
				writer.writeLong(list == null ? 0L : list.size());
			}
				break;
			case "flushall":
				map.clear();
				lists.clear();
				writer.writeSimpleString("OK");
				break;
			//			case "multi":
			//				multi = new ArrayList<>();
			//				RespProtocol.writeSimpleString(writer, "OK");
			//				break;
			//			case "exec":
			//				RespProtocol.writeBulkString(writer, bulk);
			//				for (final RespCommand multiCommand : multi) {
			//					onCommand(writer, multiCommand);
			//				}
			//				multi = null;
			//				RespProtocol.writeSimpleString(writer, "OK");
			//				break;
			default:
				writer.writeError("RESP Command unknown : " + command.getName());
		}
	}

	public static void main(final String[] args) throws Exception {
		System.out.println(">>redis server....");
		new RedisServer(6379).start();
	}
	//		CopyOfRedisServer server = new CopyOfRedisServer(6380);
	//		System.out.println(">>redis server démarré");
	//		new GetSetBenchmark().playVedis();
	//		new GetSetBenchmark().playJedis();
	//		server.toString();
}
