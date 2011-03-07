package de.topicmapslab.schemafit;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.majortom.util.FeatureStrings;
import de.topicmapslab.tmclvalidator.TMCLValidator;
import de.topicmapslab.tmclvalidator.TMCLValidatorException;
import de.topicmapslab.tmclvalidator.ValidationResult;
import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime;
import de.topicmapslab.tmql4j.components.processor.runtime.TMQLRuntimeFactory;
import de.topicmapslab.tmql4j.util.XmlSchemeDatatypes;

public class TestWithData {

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


	@Test
	public void testRunIndividual() {

		Topic hans = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/hans"));
		hans.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Person")));
		Name hansName = hans.createName("hansi");

		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccType")), "some text");

		Topic assoType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/AssoType"));
		Association _a = _tm.createAssociation(assoType);
		Topic john = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/john"));
		Topic roleType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/RoleType"));
		_a.createRole(roleType, hans);
		_a.createRole(roleType, john);

		hans.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Employee")));
		john.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Person")));
		john.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Human")));
		john.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Friend")));

		_tm.createTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly")).addType(
				_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Unicorn")));

		john.addSubjectIdentifier(_tm.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly"))
				.addSubjectIdentifier(
						_tm.createLocator("http://foo.net/charly_si_2"));
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly"))
				.addSubjectIdentifier(
						_tm.createLocator("http://foo.net/charly_si_3"));
		_tm.createTopicBySubjectLocator(
				_tm.createLocator("http://foo.net/bello")).addType(
				_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Dog")));

		john.addSubjectIdentifier(_tm.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly"))
				.addSubjectIdentifier(
						_tm.createLocator("http://foo.net/charly_si_2"));
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly"))
				.addSubjectIdentifier(
						_tm.createLocator("http://foo.net/charly_si_3"));
		_tm.createTopicBySubjectLocator(
				_tm.createLocator("http://foo.net/bello")).addType(
				_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Dog")));

		john.addSubjectIdentifier(_tm.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly"))
				.addSubjectIdentifier(
						_tm.createLocator("http://foo.net/charly_si_2"));

		_tm.createTopicByItemIdentifier(
				_tm.createLocator("http://foo.net/bello")).addType(
				_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Dog")));
		_tm.getConstructByItemIdentifier(
				_tm.createLocator("http://foo.net/bello")).addItemIdentifier(
				_tm.createLocator("http://foo.net/bello_ii_1"));

		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly")).createName(
				"Charlie the Unicorn");
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly")).createName(
				"Charlie");
		_tm.getTopicBySubjectIdentifier(
				_tm.createLocator("http://foo.net/charly")).createName(
				_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/nic")),
				"Charlie AKA The Unicorn");

		hansName.createVariant("HŠnschen ", _tm
				.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Scope")));
		hansName.createVariant("Hansi ", _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Scope")));
		Name jonName = john.createName("Jon");
		Name jonName2 = john.createName(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/nic")), "Jon2");

		jonName.createVariant("Jonny ", _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Scope")));
		jonName.createVariant("Jonny 2", _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Scope2")));

		jonName2.createVariant("Jonny Jo ", _tm
				.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Scope")));

		
		
		Topic charly = _tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly"));
				
				
		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/description")) ,"Charlie is a Unicorn");

		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/description")) ,"Charlie is a funny Unicorn");
		
		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/beschreibung")) ,"Charlie ist ein Einhorn");
		
		
		
		Topic bello = _tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/bello"));
		bello.addType(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Dog")));
		Association a1 = _a = _tm.createAssociation(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Family")));
		a1.createRole(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Owner")), john);
		a1.createRole(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Owned")), bello);

		
		LinkedList<Topic> scope1 = new LinkedList<Topic>();
		scope1.add(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/scope1")));
		scope1.get(0).addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Scope1Type")));
		hansName = hans.createName("Hansus",  scope1 );

		
		Topic aReifier = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/associationREifier"));
		Topic aReifierType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/AssociationREifierType"));
		aReifier.addType(aReifierType);

		
		Topic nReifier = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/nameREifier"));
		Topic nReifierType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/NameREifierType"));
		nReifier.addType(nReifierType);
		
		Topic oReifier = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/occurenceREifier"));
		Topic oReifierType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccurenceREifierType"));
		oReifier.addType(oReifierType);
		
		
		hansName.setReifier(nReifier);
		_a.setReifier(aReifier);
		Occurrence o = nReifier.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeyy")), "some irrelevant text");
		o.setReifier(oReifier);
		
		
		
		Topic at = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/AT"));
		Association a = _tm.createAssociation(at);
		Topic rt1 = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/RoleType1"));
		Topic rt2 = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/RoleType2"));
		Topic rt3 = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/RoleType3"));
		a.createRole(rt1, hans);
		a.createRole(rt2, hans);
		a.createRole(rt3, hans);
		a.createRole(rt1, john);
		
		
		
		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeExternal")), _tm
				.createLocator("http://foo.net/externalOcc"));
		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeExternal")), _tm
				.createLocator("http://foo.net/externalOcc1"));
		john.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeFloat")),"5.3", _tm
				.createLocator(XmlSchemeDatatypes.XSD_FLOAT));
		
		_fitt.run(_tm, _tmSchema);
		TMCLValidator validator = new TMCLValidator();
		Map<Construct, Set<ValidationResult>> invalidConstructs = null;
		try {
			invalidConstructs = validator.validate(_tm, _tmSchema);
		} catch (TMCLValidatorException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		for (Map.Entry<Construct, Set<ValidationResult>> invalidConstruct : invalidConstructs
				.entrySet()) {

			System.out.println("Invalid construct " + invalidConstruct.getKey()
					+ ":");

			for (ValidationResult result : invalidConstruct.getValue()) {

				System.out.println("Constraint " + result.getConstraintId()
						+ " violated:");
				System.out.println(result.getMessage());

			}
		}
		assertEquals(0, invalidConstructs.size());

	}

}
