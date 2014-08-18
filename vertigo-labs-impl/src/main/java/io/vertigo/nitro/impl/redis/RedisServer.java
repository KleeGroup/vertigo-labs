package io.vertigo.nitro.impl.redis;

import io.vertigo.nitro.impl.redis.resp.RespCommand;
import io.vertigo.nitro.impl.redis.resp.RespCommandHandler;
import io.vertigo.nitro.impl.redis.resp.RespProtocol;
import io.vertigo.nitro.impl.redis.resp.RespServer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RedisServer implements RespCommandHandler {
	private final RespServer respServer;
	private final Map<String, String> map = new HashMap<>();
	private final Map<String, List<String>> lists = new HashMap<>();

	RedisServer(final int port) {
		respServer = new RespServer(port, this);
		new Thread(respServer).start();
	}

	public void onCommand(final OutputStream out, final RespCommand command) throws IOException {
		switch (command.getName().toLowerCase()) {
			case "ping":
				RespProtocol.writeSimpleString(out, "PONG");
				break;
			case "set": {
				final String key = command.args()[0];
				map.put(key, command.args()[1]);
				RespProtocol.writeSimpleString(out, "OK");
			}
				break;
			case "get": {
				final String key = command.args()[0];
				final String value = map.get(key);
				RespProtocol.writeBulkString(out, value);
			}
				break;
			case "exists": {
				final String key = command.args()[0];
				final boolean exists = map.containsKey(key) || lists.containsKey(key);
				RespProtocol.writeLong(out, exists ? 1L : 0L);
			}
				break;
			case "brpoplpush": {
				final String key = command.args()[0];
				final String key2 = command.args()[1];
				final List<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					RespProtocol.writeBulkString(out, null);
				} else {
					final String element = list.remove(0);
					List<String> list2 = lists.get(key2);
					if (list2 == null) {
						list2 = new ArrayList<>();
						lists.put(key2, list2);
					}
					list2.add(0, element);
					RespProtocol.writeBulkString(out, element);
				}
			}
				break;
			case "llen": {
				final String key = command.args()[0];
				final List<String> list = lists.get(key);
				RespProtocol.writeLong(out, list == null ? 0L : list.size());
			}
				break;
			case "lpop": {
				final String key = command.args()[0];
				final List<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					RespProtocol.writeBulkString(out, null);
				} else {
					RespProtocol.writeBulkString(out, list.remove(0));
				}
			}
				break;
			case "rpop": {
				final String key = command.args()[0];
				final List<String> list = lists.get(key);
				if (list == null || list.size() == 0) {
					RespProtocol.writeBulkString(out, null);
				} else {
					RespProtocol.writeBulkString(out, list.remove(list.size() - 1));
				}
			}
				break;
			case "lpush": {
				final String key = command.args()[0];
				List<String> list = lists.get(key);
				if (list == null) {
					list = new ArrayList<>();
					lists.put(key, list);
				}
				for (int i = 1; i < command.args().length; i++) {
					list.add(0, command.args()[i]);
				}
				RespProtocol.writeLong(out, 1L * list.size());
			}
				break;
			case "rpush": {
				final String key = command.args()[0];
				List<String> list = lists.get(key);
				if (list == null) {
					list = new ArrayList<>();
					lists.put(key, list);
				}
				for (int i = 1; i < command.args().length; i++) {
					list.add(command.args()[i]);
				}
				RespProtocol.writeLong(out, 1L * list.size());
			}
				break;
			case "lpushx": {
				final String key = command.args()[0];
				final List<String> list = lists.get(key);
				if (list != null) {
					for (int i = 1; i < command.args().length; i++) {
						list.add(0, command.args()[i]);
					}
				}
				RespProtocol.writeLong(out, list == null ? 0L : list.size());
			}
				break;
			case "rpushx": {
				final String key = command.args()[0];
				final List<String> list = lists.get(key);
				if (list != null) {
					for (int i = 1; i < command.args().length; i++) {
						list.add(command.args()[i]);
					}
				}
				System.out.println(">>rpushx key:" + key + " , list:" + list);
				RespProtocol.writeLong(out, list == null ? 0L : list.size());
			}
				break;
			case "flushall":
				map.clear();
				lists.clear();
				RespProtocol.writeSimpleString(out, "OK");
				break;
			default:
				RespProtocol.writeError(out, "RESP Command unknown : " + command.getName());
		}
	}

}
