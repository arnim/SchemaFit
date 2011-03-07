package de.topicmapslab.schemafit;

import org.tmapi.core.TopicMap;

import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime;



public class FittTMQLRunner implements ITMQLRunner {

	private ITMQLRuntime tmql;

	public FittTMQLRunner(ITMQLRuntime tmql) {
		this.tmql = tmql;
	}

	public Iterable<?> run(TopicMap tm, String q) {
		return tmql.run( tm, q).getResults();
	}

}
