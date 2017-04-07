package io.vertigo.cluster.util;

import io.vertigo.lang.Assertion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * A process is a simple command executed by the OS.
 *
 * @author pchretien
 */
final class ZProcess implements AutoCloseable {
	private final Process process;

	ZProcess(final String command) throws Exception {
		Assertion.checkArgNotEmpty(command);
		//-----
		process = Runtime.getRuntime().exec(command);
		//redirect error to System.err
		new StreamGobbler(process.getErrorStream(), System.err).start();
		//redirect out to System.out
		new StreamGobbler(process.getInputStream(), System.out).start();
	}

	@Override
	public void close() {
		//kill process
		process.destroy();
	}

	//-----
	//to redirect output and err streams
	final static class StreamGobbler extends Thread {
		private final InputStream is;
		private final PrintStream out;

		private StreamGobbler(final InputStream is, final PrintStream out) {
			Assertion.checkNotNull(is);
			Assertion.checkNotNull(out);
			//-----
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
}
