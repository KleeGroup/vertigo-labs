package snowblood.boot;

import java.util.Arrays;
import java.util.Iterator;

import snowblood.gen.domain.JobDeltaComplet;
import snowblood.gen.domain.JobEtat;
import snowblood.gen.domain.JobMode;
import snowblood.gen.domain.JobRejet;
import snowblood.gen.domain.JobSens;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;

public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] {
				Jobdefinition.class,
				JobDeltaComplet.class,
				JobEtat.class,
				Jobexecution.class,
				JobMode.class,
				JobRejet.class,
				JobSens.class,
		}).iterator();
	}
}
