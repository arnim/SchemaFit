/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.schemafit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tmapi.core.Construct;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.XTMTopicMapReader;

import de.topicmapslab.majortom.util.FeatureStrings;
import de.topicmapslab.tmclvalidator.TMCLValidator;
import de.topicmapslab.tmclvalidator.ValidationResult;
import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime;
import de.topicmapslab.tmql4j.components.processor.runtime.TMQLRuntimeFactory;

/**
 * @author <a href="mailto:arnim.bleier+fit@gmail.com">Arnim Bleier</a>
 */
public class TestFitOnMap {

	static Fit _fitt;
	private static ITMQLRuntime tmql;
	private static FittTMQLRunner _tmqlRunner;
	TopicMap _tm;
	TopicMapSystem _tmSys;
	private TopicMap _tmSchema;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tmql = TMQLRuntimeFactory.newFactory().newRuntime();
		_tmqlRunner = new FittTMQLRunner(tmql);
		_fitt = new Fit(_tmqlRunner);
	}

	@Before
	public void setUp() throws Exception {
		TopicMapSystemFactory tmSysFac = TopicMapSystemFactory.newInstance();
		tmSysFac.setFeature(
				FeatureStrings.TOPIC_MAPS_SUPERTYPE_SUBTYPE_ASSOCIATION, false);
		tmSysFac.setFeature(
				FeatureStrings.TOPIC_MAPS_TYPE_INSTANCE_ASSOCIATION, false);

		_tmSys = tmSysFac.newTopicMapSystem();
		_tm = _tmSys.createTopicMap("http://www.example.org/map1");
		_tmSchema = _tmSys.createTopicMap("http://www.example.org/map1_schema");

	}

	/**
	 * Test with cerny2
	 * 
	 * @throws Exception
	 * 
	 * 
	 * TODO
	 * Invalid construct Occurrence{Parent:Topic{si:http://www.xxx.com};Type:Topic{si:http://www.topincs.com/someType};Value:...;Datatype:http://tmwiki.org/psi/wiki-markup}:
	 * Constraint http://psi.topicmaps.org/tmcl/occurrence-datatype-constraint violated:
	 * Occurrences of type ????? must have the datatype http://www.w3.org/2001/XMLSchema#any.
	 */
	@Test
	public void testWithCerny2() throws Exception {
		assertTrue(_tm.getTopics().size() == 0);		
		new XTMTopicMapReader(_tm, getClass().getResourceAsStream("cerny2.xtm"), "www.example.com").read();
		assertTrue(_tm.getTopics().size() > 0);		
		_fitt.run(_tm, _tmSchema);
		TMCLValidator validator = new TMCLValidator();
		Map<Construct, Set<ValidationResult>> invalidConstructs = validator.validate(_tm, _tmSchema);
		for (Map.Entry<Construct, Set<ValidationResult>> invalidConstruct : invalidConstructs.entrySet()) {
			System.err.println("Invalid construct " + invalidConstruct.getKey() + ":");
			for (ValidationResult result : invalidConstruct.getValue()) {
				System.err.println("Constraint " + result.getConstraintId() + " violated:");
				System.err.println(result.getMessage());
			}
		}
		assertEquals(0, invalidConstructs.size());
	}
	
	
	/**
	 * Test with archiv-ostpreussen
	 * 
	 * @throws Exception
	 * 
	 * TODO
	 * java.net.MalformedURLException comming from tmapix.io
	 * 
	 */
	@Test
	public void testWithArchivOstpreussen() throws Exception {
		assertTrue(_tm.getTopics().size() == 0);		
		new XTMTopicMapReader(_tm, getClass().getResourceAsStream("archiv-ostpreussen.xtm"), "www.example.com").read();
		assertTrue(_tm.getTopics().size() > 0);		
		_fitt.run(_tm, _tmSchema);
		TMCLValidator validator = new TMCLValidator();
		Map<Construct, Set<ValidationResult>> invalidConstructs = validator.validate(_tm, _tmSchema);
		for (Map.Entry<Construct, Set<ValidationResult>> invalidConstruct : invalidConstructs.entrySet()) {
			System.err.println("Invalid construct " + invalidConstruct.getKey() + ":");
			for (ValidationResult result : invalidConstruct.getValue()) {
				System.err.println("Constraint " + result.getConstraintId() + " violated:");
				System.err.println(result.getMessage());
			}
		}
		assertEquals(0, invalidConstructs.size());
	}
	
	
	/**
	 * Test with ToyTM
	 * 
	 * @throws Exception
	 * 
	 * TODO
	 * de.topicmapslab.tmql4j.insert.exceptions.InsertException
	 * see http://code.google.com/p/tmql/issues/detail?id=43
	 */
	@Test
	public void testWithToyTM() throws Exception {
		assertTrue(_tm.getTopics().size() == 0);		
		new XTMTopicMapReader(_tm, getClass().getResourceAsStream("toytm.xtm"), "www.example.com").read();
		assertTrue(_tm.getTopics().size() > 0);		
		_fitt.run(_tm, _tmSchema);
		TMCLValidator validator = new TMCLValidator();
		Map<Construct, Set<ValidationResult>> invalidConstructs = validator.validate(_tm, _tmSchema);
		for (Map.Entry<Construct, Set<ValidationResult>> invalidConstruct : invalidConstructs.entrySet()) {
			System.err.println("Invalid construct " + invalidConstruct.getKey() + ":");
			for (ValidationResult result : invalidConstruct.getValue()) {
				System.err.println("Constraint " + result.getConstraintId() + " violated:");
				System.err.println(result.getMessage());
			}
		}
		assertEquals(0, invalidConstructs.size());
	}
	
	
	
}
