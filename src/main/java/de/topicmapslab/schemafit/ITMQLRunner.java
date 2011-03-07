package de.topicmapslab.schemafit;

import org.tmapi.core.TopicMap;



public interface ITMQLRunner {
	
	public Iterable<?>  run(TopicMap tm, String q);
	
}
