package io.vertigo.ethereum;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.ledger.services.LedgerManager;

public class LedgerManagerEthereumTest {

	private static final Logger LOGGER = LogManager.getLogger(Listener.class);

	private static AutoCloseableApp app;

	@Inject
	private LedgerManager ledgerManager;

	@BeforeClass
	public static void setUp() {
		app = new AutoCloseableApp(MyAppConfig.config());
	}

	@Before
	public void doBefore() {
		DIInjector.injectMembers(this, app.getComponentSpace());
	}

	@AfterClass
	public static void tearDown() {
		if (app != null) {
			app.close();
		}
	}

	@Test
	public void writeDataTest() {

		String messageToAlice = "Hello world";

		LOGGER.info("My ETH Balance before : " + ledgerManager.getMyBalance());

		ledgerManager.sendData(messageToAlice);
		ledgerManager.flush();
		//Thread.sleep(120_000);

		LOGGER.info("My ETH Balance after: " + ledgerManager.getMyBalance());
	}

}
