package snowblood.test;

import io.vertigo.commons.analytics.AnalyticsManager;
import io.vertigo.commons.cache.CacheManager;
import io.vertigo.commons.codec.CodecManager;
import io.vertigo.commons.impl.analytics.AnalyticsManagerImpl;
import io.vertigo.commons.impl.cache.CacheManagerImpl;
import io.vertigo.commons.impl.codec.CodecManagerImpl;
import io.vertigo.commons.impl.locale.LocaleManagerImpl;
import io.vertigo.commons.impl.resource.ResourceManagerImpl;
import io.vertigo.commons.impl.script.ScriptManagerImpl;
import io.vertigo.commons.locale.LocaleManager;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.commons.plugins.resource.java.ClassPathResourceResolverPlugin;
import io.vertigo.commons.plugins.script.janino.JaninoExpressionEvaluatorPlugin;
import io.vertigo.commons.resource.ResourceManager;
import io.vertigo.commons.script.ScriptManager;
import io.vertigo.core.Home;
import io.vertigo.core.Home.App;
import io.vertigo.core.config.AppConfig;
import io.vertigo.core.config.AppConfigBuilder;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.database.SqlDataBaseManager;
import io.vertigo.dynamo.environment.EnvironmentManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.impl.collections.CollectionsManagerImpl;
import io.vertigo.dynamo.impl.database.SqlDataBaseManagerImpl;
import io.vertigo.dynamo.impl.environment.EnvironmentManagerImpl;
import io.vertigo.dynamo.impl.file.FileManagerImpl;
import io.vertigo.dynamo.impl.persistence.PersistenceManagerImpl;
import io.vertigo.dynamo.impl.task.TaskManagerImpl;
import io.vertigo.dynamo.impl.transaction.VTransactionAspect;
import io.vertigo.dynamo.impl.transaction.VTransactionManagerImpl;
import io.vertigo.dynamo.persistence.PersistenceManager;
import io.vertigo.dynamo.plugins.database.connection.mock.MockConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.persistence.datastore.postgresql.PostgreSqlDataStorePlugin;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.tempo.impl.job.JobManagerImpl;
import io.vertigo.tempo.impl.scheduler.SchedulerManagerImpl;
import io.vertigo.tempo.job.JobManager;
import io.vertigo.tempo.plugins.scheduler.basic.BasicSchedulerPlugin;
import io.vertigo.tempo.scheduler.SchedulerManager;

import org.junit.Before;
import org.junit.Test;

import snowblood.boot.DtDefinitions;
import snowblood.gen.dao.JobdefinitionDAO;
import snowblood.gen.dao.JobexecutionDAO;
import snowblood.gen.domain.ActivityDataMode;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.services.TourdecontrolePAO;
import snowblood.services.file.FileServices;
import snowblood.services.file.FileServicesImpl;
import snowblood.services.job.JobServices;
import snowblood.services.job.JobServicesImpl;
import snowblood.services.tourdecontrole.TourDeControleServices;
import snowblood.services.tourdecontrole.TourDeControleServicesImpl;

public class TestSnowblood {
	private AppConfig appConfig;

	@Before
	public void init() {
		// @formatter:off
		appConfig = new AppConfigBuilder()
				.beginModule("vertigo")
					.beginComponent(ResourceManager.class, ResourceManagerImpl.class)
						.beginPlugin(ClassPathResourceResolverPlugin.class).endPlugin()
					.endComponent()
					.beginComponent(SchedulerManager.class, SchedulerManagerImpl.class)
						.beginPlugin(BasicSchedulerPlugin.class).endPlugin()
					.endComponent()
					.beginComponent(JobManager.class, JobManagerImpl.class).endComponent()
					.beginComponent(ScriptManager.class, ScriptManagerImpl.class)
						.beginPlugin(JaninoExpressionEvaluatorPlugin.class).endPlugin()
					.endComponent()
					.beginComponent(CollectionsManager.class, CollectionsManagerImpl.class).endComponent()
					.beginComponent(CodecManager.class, CodecManagerImpl.class).endComponent()
					.beginComponent(CacheManager.class, CacheManagerImpl.class)
						.beginPlugin(MemoryCachePlugin.class).endPlugin()
					.endComponent()
					.beginComponent(LocaleManager.class, LocaleManagerImpl.class)
					   .withParam("locales", "fr_FR")
					.endComponent()
					.beginComponent(AnalyticsManager.class, AnalyticsManagerImpl.class).endComponent()
					.beginComponent(PersistenceManager.class, PersistenceManagerImpl.class)
						.beginPlugin(PostgreSqlDataStorePlugin.class)
							.withParam("sequencePrefix", "SEQ_")
						.endPlugin()
					.endComponent()
					.beginComponent(VTransactionManager.class, VTransactionManagerImpl.class).endComponent()
					.beginComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
						.beginPlugin(MockConnectionProviderPlugin.class)
							.withParam("dataBaseClass", "io.vertigo.dynamo.impl.database.vendor.postgresql.PostgreSqlDataBase")
							.withParam("jdbcDriver", org.postgresql.Driver.class.getName())
							.withParam("jdbcUrl", "jdbc:postgresql://172.20.109.69:5432/tdc?user=snowblood&password=snowblood")
						.endPlugin()
					.endComponent()
					.beginComponent(TaskManager.class, TaskManagerImpl.class).endComponent()
					.beginComponent(FileManager.class, FileManagerImpl.class).endComponent()
					.withAspect(VTransactionAspect.class)
					//-----
					.beginComponent(EnvironmentManager.class, EnvironmentManagerImpl.class)
						.beginPlugin(AnnotationLoaderPlugin.class).endPlugin()
						.beginPlugin(KprLoaderPlugin.class).endPlugin()
						.beginPlugin(DomainDynamicRegistryPlugin.class).endPlugin()
					.endComponent()
				.endModule()
				.beginModule("Snowblood")
					.withNoAPI()
					.withInheritance(Object.class)
					.beginComponent(JobdefinitionDAO.class).endComponent()
					.beginComponent(JobexecutionDAO.class).endComponent()
					.beginComponent(TourdecontrolePAO.class).endComponent()
					//-----
					.beginComponent(FileServices.class, FileServicesImpl.class).endComponent()
					.beginComponent(JobServices.class, JobServicesImpl.class).endComponent()
					.beginComponent(TourDeControleServices.class, TourDeControleServicesImpl.class).endComponent()
					//-----
					.withResource("kpr", "snowblood/boot/application.kpr")
					.withResource("classes", DtDefinitions.class.getName())
				.endModule()
				.build();
		// @formatter:on
	}

	//
	//	@Test
	//	public void testB() {
	//		System.out.println("testB");
	//	}

	@Test
	public void test() {

		//	Home.getComponentSpace().resolve(VTransactionManager.class);

		try (final App app = new App(appConfig)) {
			final TourDeControleServices tourDeControleServices = Home.getComponentSpace().resolve(TourDeControleServices.class);

			// Create a definition
			final Jobdefinition jobdefinition = new Jobdefinition();
			// Mandatory fields
			jobdefinition.setMultiExecutions(false);
			jobdefinition.setManuelAutorise(true);
			jobdefinition.setActivation(false);
			jobdefinition.setInterruptible(true);
			jobdefinition.setTestable(true);
			jobdefinition.setCompletPossible(true);
			// Optional fields
			jobdefinition.setCode("TU03");
			jobdefinition.setDataMode(ActivityDataMode.DELTA);
			// Save
			tourDeControleServices.saveJobdefinition(jobdefinition);
			final Long jobId = jobdefinition.getJodId();
			System.out.println("saved with id " + jobId);

			// Load
			final Jobdefinition jobdefinition2 = tourDeControleServices.getJobdefinition(jobId);
			// Print
			System.out.println(jobdefinition2.toString());

			// Delete
			// tourDeControleServices.????(jodId);

			System.out.println("test");

		}
	}

}
