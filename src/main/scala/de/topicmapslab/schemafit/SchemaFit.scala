package de.topicmapslab.schemafit

import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime
import org.tmapi.core.TopicMap



class SchemaFit(tm: TopicMap, tmql: ITMQLRuntime) {
	
	
	def execute(q: String): String = {
		tmql.run(tm, q).getResults().toString
	}
	
}