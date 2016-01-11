package io.vertigo.nitro.impl.redis.resp;

import io.vertigo.lang.Assertion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RespProtocol {
	enum RespType {
		RESP_STRING('+'),
		RESP_ARRAY('*'),
		RESP_BULK('$'),
		RESP_INTEGER(':'),
		RESP_EVAL('?');
		private final char c;

		private RespType(final char c) {
			this.c = c;
		}

		char getChar() {
			return c;
		}
	}

	static final String CHARSET = "UTF-8";
	static final String LN = "\r\n";

	static RespCommand readCommand(final BufferedReader input) throws IOException {
		String line = input.readLine();
		if (line == null) {
			return null;
		}
		//		System.out.println("sub: " + line.substring(1));
		if (!line.startsWith("*")) {
			throw new RuntimeException("protocol must begin with *");
		}
		final int n = Integer.valueOf(line.substring(1));
		if (n < 1) {
			throw new RuntimeException("protocol must contains at least one line");
		}

		String commandName = null;
		final String[] args = new String[n - 1];
		for (int i = 0; i < n; i++) {
			line = input.readLine();
			//	System.out.println("line('" + i + "') : " + line);
			if (!line.startsWith("$")) {
				throw new RuntimeException("protocol must contains lines with $");
			}
			//n = Integer.valueOf(line.substring(1));
			//On n'exploite pas cette info
			line = input.readLine();
			if (i == 0) {
				commandName = line;
				//	System.out.println("name  : " + line);
			} else {
				args[i - 1] = line;
			}
		}
		return new RespCommand(commandName, args);
	}

	static Object pushPull(final RespType type, final BufferedReader in, final RespWriter writer, final String command, final String[] args) {
		try {
			push(writer, command, args);
			return pull(in, type.getChar());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void push(final RespWriter writer, final String command, final String[] args) throws IOException {
		//System.out.println("exec command :" + command.getName());
		writer.writeCommand(writer, command, args);
	}

	private static Object pull(final BufferedReader in, final char expected) throws IOException {
		final String response = in.readLine();
		//System.out.println(expected + ":" + response);
		//---
		final char start = response.charAt(0);
		if (start == '-') {
			throw new RuntimeException(response);
		}
		//Hack pour g�rer un mauvais retour de brpoplpush
		if (start == '*' && "*-1".equals(response)) {
			return null;
		}
		//----
		Assertion.checkArgument('?' == expected || expected == response.charAt(0), "exepected {0}, find {1}", expected, response.charAt(0));
		//----
		switch (start) {
			case ':': //number
				return Long.valueOf(response.substring(1));
			case '+': //string
				return response.substring(1);
			case '$': //bulk
				final int n = Integer.valueOf(response.substring(1));
				if (n < 0) {
					return null;
				} else if (n == 0) {
					return "";
				}
				return in.readLine();
			case '*': //array
				final int m = Integer.valueOf(response.substring(1));
				final List list = new ArrayList<>();
				for (int i = 0; i < m; i++) {
					list.add(pull(in, '?'));
				}
				return list;
			default:
				throw new IllegalArgumentException("According resp protocol, a response must start with - + : $ or *");
		}

	}

}
