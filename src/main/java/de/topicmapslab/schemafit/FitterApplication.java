/**
 * 
 */
package de.topicmapslab.schemafit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapix.io.CTMTopicMapReader;
import org.tmapix.io.TopicMapReader;
import org.tmapix.io.XTM2TopicMapWriter;
import org.tmapix.io.XTMTopicMapReader;
import org.tmapix.io.XTMVersion;

import de.topicmapslab.format_estimator.FormatEstimator;
import de.topicmapslab.format_estimator.FormatEstimator.Format;
import de.topicmapslab.majortom.core.TopicMapSystemFactoryImpl;
import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime;
import de.topicmapslab.tmql4j.components.processor.runtime.TMQLRuntimeFactory;
import de.topicmapslab.tmql4j.path.components.processor.runtime.TmqlRuntime2007;

/**
 * Application Entry to use the fitter on the console
 * 
 * @author Hannes Niederhausen
 *
 */
public class FitterApplication {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args.length!=2) {
			System.out.println("The application needs to params.");
			System.out.println("Use: app src target");;
			return;
		}
		
		String instanceFile = args[0];
		String schemaFile = args[1];
		
		

		Format format = FormatEstimator.guessFormat(new FileReader(instanceFile));
		
		TopicMapSystem tms = new TopicMapSystemFactoryImpl().newTopicMapSystem();
		TopicMap topicMap = tms.createTopicMap("http://tmp.de/");
		
		TopicMapReader reader = null;
		switch (format) {
		case CTM:
		case CTM_1_0:
			reader = new CTMTopicMapReader(topicMap, new File(instanceFile));
			break;
		case XTM:
		case XTM_1_0:
		case XTM_1_1:
		case XTM_2_0:
		case XTM_2_1:
			reader = new XTMTopicMapReader(topicMap, new File(instanceFile));
			break;
		}

		if (reader == null)
			throw new IllegalArgumentException("Unknown File Type");
		
		reader.read();
		
		ITMQLRuntime runtime = TMQLRuntimeFactory.newFactory().newRuntime(TmqlRuntime2007.TMQL_2007);
		
		TopicMap schemaMap = tms.createTopicMap("http://newschema.de/");
		
		ITMQLRunner tmqlRunner = new FittTMQLRunner(runtime);
		Fit fit = new Fit(tmqlRunner);
		fit.run(topicMap, schemaMap);
		
		XTM2TopicMapWriter writer = new XTM2TopicMapWriter(new FileOutputStream(new File(schemaFile)), "http://schema.de/", XTMVersion.XTM_2_1);
		writer.write(schemaMap);
	}

}
