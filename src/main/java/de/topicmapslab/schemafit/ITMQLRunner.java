/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.schemafit;

import org.tmapi.core.TopicMap;

/**
 * @author <a href="mailto:arnim.bleier+fit@gmail.com">Arnim Bleier</a>
 */

public interface ITMQLRunner {
	
	public Iterable<?>  run(TopicMap tm, String q);
	
}
