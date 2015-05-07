package snowblood.test;

import io.vertigo.commons.analytics.AnalyticsManager;
import io.vertigo.commons.cache.CacheManager;
import io.vertigo.commons.codec.CodecManager;
import io.vertigo.commons.impl.analytics.AnalyticsManagerImpl;
import io.vertigo.commons.impl.cache.CacheManagerImpl;
import io.vertigo.commons.impl.codec.CodecManagerImpl;
import io.vertigo.commons.impl.resource.ResourceManagerImpl;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.commons.plugins.resource.java.ClassPathResourceResolverPlugin;
import io.vertigo.commons.resource.ResourceManager;
import io.vertigo.core.Home;
import io.vertigo.core.Home.App;
import io.vertigo.core.config.AppConfig;
import io.vertigo.core.config.AppConfigBuilder;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.environment.EnvironmentManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.impl.collections.CollectionsManagerImpl;
import io.vertigo.dynamo.impl.environment.EnvironmentManagerImpl;
import io.vertigo.dynamo.impl.file.FileManagerImpl;
import io.vertigo.dynamo.impl.persistence.PersistenceManagerImpl;
import io.vertigo.dynamo.impl.task.TaskManagerImpl;
import io.vertigo.dynamo.persistence.PersistenceManager;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.persistence.datastore.hsql.HsqlDataStorePlugin;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.tempo.impl.job.JobManagerImpl;
import io.vertigo.tempo.impl.scheduler.SchedulerManagerImpl;
import io.vertigo.tempo.job.JobManager;
import io.vertigo.tempo.plugins.scheduler.basic.BasicSchedulerPlugin;
import io.vertigo.tempo.scheduler.SchedulerManager;

import org.junit.Test;

import snowblood.boot.DtDefinitions;
import snowblood.gen.dao.tourdecontrole.JobDeltaCompletDAO;
import snowblood.gen.dao.tourdecontrole.JobModeDAO;
import snowblood.gen.dao.tourdecontrole.JobRejetDAO;
import snowblood.gen.dao.tourdecontrole.JobSensDAO;
import snowblood.gen.dao.tourdecontrole.JobdefinitionDAO;
import snowblood.gen.dao.tourdecontrole.JobexecutionDAO;
import snowblood.gen.domain.tourdecontrole.Jobdefinition;
import snowblood.gen.services.tourdecontrole.TourdecontrolePAO;
import snowblood.services.file.FileServices;
import snowblood.services.file.FileServicesImpl;
import snowblood.services.job.JobServices;
import snowblood.services.job.JobServicesImpl;
import snowblood.services.tourdecontrole.TourDeControleServices;
import snowblood.services.tourdecontrole.TourDeControleServicesImpl;

public class TestSnowblood {
	@Test
	public void test() {
		final AppConfig appConfig = new AppConfigBuilder()
				.beginModule("vertigo")
				.beginComponent(ResourceManager.class, ResourceManagerImpl.class)
				.beginPlugin(ClassPathResourceResolverPlugin.class).endPlugin()
				.endComponent()
				.beginComponent(SchedulerManager.class, SchedulerManagerImpl.class)
				.beginPlugin(BasicSchedulerPlugin.class).endPlugin()
				.endComponent()
				.beginComponent(JobManager.class, JobManagerImpl.class).endComponent()
				.beginComponent(CollectionsManager.class, CollectionsManagerImpl.class).endComponent()
				.beginComponent(CodecManager.class, CodecManagerImpl.class).endComponent()
				.beginComponent(CacheManager.class, CacheManagerImpl.class)
				.beginPlugin(MemoryCachePlugin.class).endPlugin()
				.endComponent()
				.beginComponent(AnalyticsManager.class, AnalyticsManagerImpl.class).endComponent()
				.beginComponent(PersistenceManager.class, PersistenceManagerImpl.class)
				.beginPlugin(HsqlDataStorePlugin.class)
				.withParam("sequencePrefix", "SEQ_")
				.endPlugin()
				.endComponent()
				.beginComponent(TaskManager.class, TaskManagerImpl.class).endComponent()
				.beginComponent(FileManager.class, FileManagerImpl.class).endComponent()
				//
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
				.beginComponent(JobDeltaCompletDAO.class).endComponent()
				.beginComponent(JobexecutionDAO.class).endComponent()
				.beginComponent(JobModeDAO.class).endComponent()
				.beginComponent(JobRejetDAO.class).endComponent()
				.beginComponent(JobSensDAO.class).endComponent()
				.beginComponent(TourdecontrolePAO.class).endComponent()
				//-----
				.beginComponent(FileServices.class, FileServicesImpl.class).endComponent()
				.beginComponent(JobServices.class, JobServicesImpl.class).endComponent()
				.beginComponent(TourDeControleServices.class, TourDeControleServicesImpl.class).endComponent()

				.withResource("kpr", "snowblood/boot/application.kpr")
				.withResource("classes", DtDefinitions.class.getName())
				.endModule()
				.build();

		try (final App app = new App(appConfig)) {
			//			final JobServices jobServices = Home.getComponentSpace().resolve(JobServices.class);
			final TourDeControleServices tourDeControleServices = Home.getComponentSpace().resolve(TourDeControleServices.class);

			final Jobdefinition jobdefinition = new Jobdefinition();
			//xxx
			tourDeControleServices.saveJobdefinition(jobdefinition);
		}
	}
}
