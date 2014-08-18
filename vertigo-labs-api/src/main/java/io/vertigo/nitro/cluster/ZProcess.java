package io.vertigo.nitro.cluster;

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
				ioe.printStackTrace();
			}
		}
	}

	private final Process process;
	private final StreamGobbler errorGobbler;
	private final StreamGobbler outputGobbler;

	ZProcess(final String command) throws Exception {
		process = Runtime.getRuntime().exec(command);
		errorGobbler = new StreamGobbler(process.getErrorStream(), System.err);
		outputGobbler = new StreamGobbler(process.getInputStream(), System.out);
		errorGobbler.start();
		outputGobbler.start();
		//	process.waitFor();
	}

	public void close() {
		//kill process
		process.destroy();
	}
}
