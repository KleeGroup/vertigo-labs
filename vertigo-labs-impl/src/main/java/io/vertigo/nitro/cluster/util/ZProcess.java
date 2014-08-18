package io.vertigo.nitro.cluster.util;

import io.vertigo.kernel.lang.Assertion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

//execute a new process with a class and args, and redirect output and err streams to console.
final class ZProcess implements AutoCloseable {
	private final Process process;

	ZProcess(final String command) throws Exception {
		Assertion.checkArgNotEmpty(command);
		//---------------------------------------------------------------------
		process = Runtime.getRuntime().exec(command);
		//redirect error to System.err
		new StreamGobbler(process.getErrorStream(), System.err).start();
		//redirect out to System.out
		new StreamGobbler(process.getInputStream(), System.out).start();
	}

	public void close() {
		//kill process
		process.destroy();
	}

	//------------------------------------------------------------------------_
	//to redirect output and err streams
	final static class StreamGobbler extends Thread {
		private final InputStream is;
		private final PrintStream out;

		private StreamGobbler(final InputStream is, final PrintStream out) {
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
}
