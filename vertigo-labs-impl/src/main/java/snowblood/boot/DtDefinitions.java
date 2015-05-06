package snowblood.boot;

import java.util.Arrays;
import java.util.Iterator;

import snowblood.gen.domain.tourdecontrole.JobDeltaComplet;
import snowblood.gen.domain.tourdecontrole.JobEtat;
import snowblood.gen.domain.tourdecontrole.JobMode;
import snowblood.gen.domain.tourdecontrole.JobRejet;
import snowblood.gen.domain.tourdecontrole.JobSens;
import snowblood.gen.domain.tourdecontrole.Jobdefinition;
import snowblood.gen.domain.tourdecontrole.Jobexecution;

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
