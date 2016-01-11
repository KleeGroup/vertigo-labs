package io.vertigo.nitro.impl.redis.resp;

import io.vertigo.lang.Assertion;

import java.io.IOException;
import java.io.OutputStream;

public final class RespWriter {
	private final OutputStream out;

	RespWriter(final OutputStream out) {
		Assertion.checkNotNull(out);
		//-----
		this.out = out;
	}

	public RespWriter writeError(final String msg) throws IOException {
		return write("-").writeLN(msg);
	}

	public RespWriter writeSimpleString(final String value) throws IOException {
		return write("+").writeLN(value);
	}

	public RespWriter writeLong(final Long value) throws IOException {
		return write(":").writeLN(String.valueOf(value));
	}

	//	public static void writeArray(final OutputStream out, final List<String> array) throws IOException {
	//		out.write("*".getBytes(CHARSET));
	//		out.write(String.valueOf(array.size()).getBytes("CHARSET"));
	//	}

	public RespWriter writeBulkString(final String bulk) throws IOException {
		//System.out.println("bulk:" + bulk);
		//--- cas du nom de la commande
		if (bulk == null) {
			return writeLN("$-1");
		}

		final byte[] bytes = bulk.getBytes(RespProtocol.CHARSET);
		return write("$").write(bytes.length).writeLN()
				.writeLN(bulk);
	}

	public void writeCommand(final RespWriter writer, final String command, final String args[]) throws IOException {
		//--- *Nb d'infos
		write("*").write(args.length + 1).writeLN()
				//--- cas du nom de la commande
				.writeBulkString(command);
		//--- cas des args
		for (final String arg : args) {
			writer.writeBulkString(arg);
		}
		flush();
	}

	private RespWriter write(final String s) throws IOException {
		out.write(s.getBytes(RespProtocol.CHARSET));
		return this;
	}

	private RespWriter write(final int i) throws IOException {
		return write(String.valueOf(i));
	}

	private RespWriter writeLN() throws IOException {
		return write(RespProtocol.LN);
	}

	private RespWriter writeLN(final String s) throws IOException {
		return write(s).writeLN();
	}

	private void flush() throws IOException {
		out.flush();
	}

	void close() throws IOException {
		out.close();
	}
}
