package io.vertigo.nitro.cluster.util;

import io.vertigo.kernel.lang.Assertion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

final class ZProcess implements AutoCloseable {
	final static class StreamGobbler extends Thread {
		private final InputStream is;
		private final PrintStream out;

		StreamGobbler(final InputStream is, final PrintStream out) {
			Assertion.checkNotNull(is);
			Assertion.checkNotNull(out);
			//---------------------------------------------------------------------
			this.is = is;
			this.out = out;
		}

		@Override
		public void run() {
			try (final InputStreamReader isr = new InputStreamReader(is)) {
				try (final BufferedReader br = new BufferedReader(isr)) {
					String line = null;
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
				}
			} catch (final IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}
	//------------------------------------------------------------------------_

	private final Process process;
	private final StreamGobbler errorGobbler;
	private final StreamGobbler outputGobbler;

	ZProcess(final String command) throws Exception {
		Assertion.checkArgNotEmpty(command);
		//---------------------------------------------------------------------
		process = Runtime.getRuntime().exec(command);
		errorGobbler = new StreamGobbler(process.getErrorStream(), System.err);
		outputGobbler = new StreamGobbler(process.getInputStream(), System.out);
		errorGobbler.start();
		outputGobbler.start();
	}

	public void close() {
		//kill process
		process.destroy();
	}
}
