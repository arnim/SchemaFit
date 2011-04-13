SchemaFitt <a href="http://www.topicmapslab.de"><img src="http://code.google.com/p/sesametm/logo" alt="W3Schools.com"  align="right" align="top" /></a>
=========

Easily generating a [TMCL schema](http://www.isotopicmaps.org/tmcl/tmcl.html) for a given [topic map](http://topicmaps.org/).

Usage:
----------

	ITMQLRuntime tmql = TMQLRuntimeFactory.newFactory().newRuntime();
	ITMQLRunner tmqlRunner = new FittTMQLRunner(tmql);
	Fit fitt = new Fit(tmqlRunner);
	fitt.run(topicMapWithData, topicMapSchemaToBeGenerated)



Maven:
----------
	<dependencies>
		...
		<dependency>
			<groupId>de.topicmapslab</groupId>
			<artifactId>schemafit</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
		...
	</dependencies>
	
	<repositories>
		...
		<repository>
			<id>tmlab</id>
			<url>http://maven.topicmapslab.de/public/</url>
		</repository>
		...
	</repositories>

## License & Copyright

Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html

© 2009-2011 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de
