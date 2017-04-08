package io.vertigo.nitro.redis.benchmark;

import io.vertigo.nitro.redis.RedisClient;
import io.vertigo.nitro.redis.impl.RedisClientImpl;
import io.vertigo.nitro.redis.impl.alphaserver.RedisServer;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public final class GetSetBenchmark {
	private static String HOST = "localhost";
	//private static int PORT = 6380;
	//	private static String HOST = "kasper-redis";
	private static int PORT = 6379;
	private static final int TOTAL_OPERATIONS = 5000;

	@Before
	public void setUp() {
		new RedisServer(PORT).start();
	}

	@Test
	public void playJedis() {
		try (final Jedis jedis = new Jedis(HOST, PORT)) {
			jedis.connect();
			jedis.flushAll();

			final long begin = Calendar.getInstance().getTimeInMillis();

			for (int n = 0; n < TOTAL_OPERATIONS; n++) {
				final String key = "foo" + n;
				jedis.set(key, "bar" + n);
				jedis.get(key);
			}

			final long elapsed = Calendar.getInstance().getTimeInMillis() - begin;
			System.out.println("Jedis : " + ((1000 * 2 * TOTAL_OPERATIONS) / elapsed) + " ops");
		}
	}

	@Test
	public void playVedis() {
		try (RedisClient redis = new RedisClientImpl(HOST, PORT)) {
			redis.flushAll();

			final long begin = Calendar.getInstance().getTimeInMillis();

			for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
				final String key = "foo" + n;
				redis.set(key, "bar" + n);
				redis.get(key);
			}
			final long elapsed = Calendar.getInstance().getTimeInMillis() - begin;
			System.out.println("Vertigo-redis : " + ((1000 * 2 * TOTAL_OPERATIONS) / elapsed) + " ops");
		}
	}
}
