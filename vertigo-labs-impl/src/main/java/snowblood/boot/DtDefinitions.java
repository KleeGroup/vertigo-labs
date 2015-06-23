package snowblood.boot;

import java.util.Arrays;
import java.util.Iterator;

import snowblood.gen.domain.ActivityDataMode;
import snowblood.gen.domain.ActivityStatus;
import snowblood.gen.domain.ActivityTrigger;
import snowblood.gen.domain.ActivityRejectRule;
import snowblood.gen.domain.ActivityDirection;
import snowblood.gen.domain.JobFileInfo;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;

public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] {
				Jobdefinition.class,
				ActivityDataMode.class,
				ActivityStatus.class,
				Jobexecution.class,
				ActivityTrigger.class,
				ActivityRejectRule.class,
				ActivityDirection.class,
				JobFileInfo.class,
		}).iterator();
	}
}
