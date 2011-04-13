/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.schemafit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import de.topicmapslab.majortom.model.namespace.Namespaces;
import de.topicmapslab.majortom.util.FeatureStrings;
import de.topicmapslab.tmclvalidator.TMCLValidator;
import de.topicmapslab.tmclvalidator.TMCLValidatorException;
import de.topicmapslab.tmclvalidator.ValidationResult;
import de.topicmapslab.tmql4j.components.processor.runtime.ITMQLRuntime;
import de.topicmapslab.tmql4j.components.processor.runtime.TMQLRuntimeFactory;
import de.topicmapslab.tmql4j.util.XmlSchemeDatatypes;


/**
 * @author <a href="mailto:arnim.bleier+fit@gmail.com">Arnim Bleier</a>
 */
public class FitTest {

	static Fit _fitt;
	TopicMap _tm;
	TopicMapSystem _tmSys;
	private TopicMap _tmSchema;
	private Topic john;
	private Topic hans;
	private Name hansName;
	private Association _a;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ITMQLRuntime tmql = TMQLRuntimeFactory.newFactory().newRuntime();
		ITMQLRunner tmqlRunner = new FittTMQLRunner(tmql);
		_fitt = new Fit(tmqlRunner);
	}

	@Before
	public void setUp() throws Exception {
		TopicMapSystemFactory tmSysFac = TopicMapSystemFactory.newInstance();
		tmSysFac.setFeature(FeatureStrings.TOPIC_MAPS_SUPERTYPE_SUBTYPE_ASSOCIATION, false);
		tmSysFac.setFeature(FeatureStrings.TOPIC_MAPS_TYPE_INSTANCE_ASSOCIATION, false);		
	
		_tmSys = tmSysFac.newTopicMapSystem();
		_tm = _tmSys.createTopicMap("http://www.example.org/map1");
		_tmSchema = _tmSys.createTopicMap("http://www.example.org/map1_schema");
		
		hans = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/hans"));
		hans.addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Person")));
		hansName = hans.createName("hansi");
		
		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccType")), "some text");
		
		Topic assoType = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/AssoType"));
		_a = _tm.createAssociation(assoType);
		john = _tm.createTopicBySubjectIdentifier(_tm
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
		
		_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addType(_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/Unicorn")));
	}

	@Test
	public void testTopicTypeDeclaration() throws Exception {		
		_fitt.doTopicType(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(6, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.TOPIC_TYPE)));
		Topic unicorn = _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/Unicorn"));
		assertNotNull(unicorn);
		Set<Topic> unicornTypes = unicorn.getTypes();
		assertEquals(1, unicornTypes.size());
		assertEquals(1, unicornTypes.iterator().next().getSubjectIdentifiers().size());
		assertEquals(Namespaces.TMCL.TOPIC_TYPE, unicornTypes.iterator().next().getSubjectIdentifiers().iterator().next().toExternalForm());
	}
	
	
	@Test
	public void testNameTypeDeclaration() throws Exception {
		_fitt.doNameType(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(2, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.NAME_TYPE)));
		
		_tm.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/hans")).createName(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/funNameType")), "funny");
		
		_fitt.doNameType(_tm, _tmSchema);
		topics = _tmSchema.getTopics();
		assertEquals(3, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/funNameType")));

	}
	
	
	@Test
	public void testOccurrenceTypeDeclaration() throws Exception {
		_fitt.doOccurrenceType(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(2, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.OCCURRENCE_TYPE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/OccType")));
	}
	
	
	@Test
	public void testAssociationTypeDeclaration() throws Exception {
		_fitt.doAssociationType(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(2, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ASSOCIATION_TYPE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/AssoType")));		
	}
	
	
	@Test
	public void testRoleTypeDeclaration() throws Exception {
		_fitt.doRoleType(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(2, topics.size());
		assertEquals(null , _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ASSOCIATION_TYPE)));
		assertEquals(null , _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/AssoType")));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ROLE_TYPE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator("http://foo.net/RoleType")));
	}
	
	
	@Test
	public void testOverlapDeclaration() throws Exception {
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(0, topics.size());
		_fitt.doOverlapTypes(_tm, _tmSchema);
		topics = _tmSchema.getTopics();
//		assertEquals(13, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ALLOWS)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMDM.TYPE_INSTANCE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.OVERLAP_DECLARATION)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMDM.TYPE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMDM.INSTANCE)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ALLOWED)));
	}
	
	
	// 7.3 Subject Identifier Constraint
	@Test
	public void testSubjectIdentifierConstraint() throws Exception {		
		
		john.addSubjectIdentifier(_tm
				.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addSubjectIdentifier(_tm
						.createLocator("http://foo.net/charly_si_2"));
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addSubjectIdentifier(_tm
						.createLocator("http://foo.net/charly_si_3"));
		_tm.createTopicBySubjectLocator(_tm
				.createLocator("http://foo.net/bello")).addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Dog")));
		_fitt.doIndicatorConstraint(_tm, _tmSchema, Fit.AS_SUBJECTIDENTIFEIER_CONSTRAINT );
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(22, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MAX)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MIN)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CONSTRAINED)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.REGEXP)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.SUBJECT_IDENTIFIER_CONSTRAINT)));
	}

	
	
	// 7.4 Subject Locator Constraint
	@Test
	public void testLocatoConstraint() throws Exception {		
		
		john.addSubjectIdentifier(_tm
				.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addSubjectIdentifier(_tm
						.createLocator("http://foo.net/charly_si_2"));
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addSubjectIdentifier(_tm
						.createLocator("http://foo.net/charly_si_3"));
		_tm.createTopicBySubjectLocator(_tm
				.createLocator("http://foo.net/bello")).addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Dog")));
		
		_fitt.doIndicatorConstraint(_tm, _tmSchema, Fit.AS_SUBJECTLOCATOR_CONSTRAINT );

		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(22, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MAX)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MIN)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CONSTRAINED)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.REGEXP)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.SUBJECT_LOCATOR_CONSTRAINT)));
		assertEquals(null, _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.SUBJECT_IDENTIFIER_CONSTRAINT)));
		
	}
	
	
	
	// 7.5 Item Identifier Constraint
	@Test
	public void testItemIdentifierConstraint() throws Exception {		
		
		john.addSubjectIdentifier(_tm
				.createLocator("http://foo.net/john_si_2"));
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).addSubjectIdentifier(_tm
						.createLocator("http://foo.net/charly_si_2"));

		_tm.createTopicByItemIdentifier(_tm
				.createLocator("http://foo.net/bello")).addType(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Dog")));
		_tm.getConstructByItemIdentifier(_tm
				.createLocator("http://foo.net/bello")).addItemIdentifier(_tm
				.createLocator("http://foo.net/bello_ii_1"));
		
		_fitt.doIndicatorConstraint(_tm, _tmSchema, Fit.AS_ITEMIDENTIFEIER_CONSTRAINT );

		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(22, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MAX)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MIN)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CONSTRAINED)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.REGEXP)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ITEM_IDENTIFIER_CONSTRAINT)));
		assertEquals(null, _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.SUBJECT_IDENTIFIER_CONSTRAINT)));
	}
	
	
	// 7.6 Topic Name Constraint
	@Test
	public void testTopicNameConstraint(){
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).createName("Charlie the Unicorn");
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).createName("Charlie");
		_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly")).createName(_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/nic")) ,"Charlie AKA The Unicorn");
		
		
		
		Topic someOne = _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/someOne"));
		
		someOne.addType(_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Employee")));
		someOne.addType(_tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Friend")));
		someOne.createName("Some One");
		someOne.createName("Some One else");

		
		
		
		_fitt.doTopicCharacteristicsConstraint(_tm, _tmSchema, Namespaces.TMCL.TOPIC_NAME_CONSTRAINT);
		
		Set<Topic> topics = _tmSchema.getTopics();
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MAX)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CARD_MIN)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.TOPIC_NAME_CONSTRAINT)));
		assertEquals(21, topics.size());
	}
	
	
	// 7.7 Topic Variant Constraint
	@Test
	public void testTopicVariantConstraint(){

		
		hansName.createVariant("HŠnschen ", _tm.createTopicBySubjectIdentifier(_tm
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
		
		jonName2.createVariant("Jonny Jo ", _tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/Scope")));
		_fitt.doTopicVariantNameConstraint(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();

		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.VARIANT_NAME_CONSTRAINT)));
		assertEquals(32, topics.size());
	}
	
	
	// 7.8 Topic Occurrence Constraint
	@Test
	public void testTopicOccurrenceConstraint(){
		Topic charly = _tm.getTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/charly"));
				
				
		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
						.createLocator("http://foo.net/description")) ,"Charlie is a Unicorn");

		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/description")) ,"Charlie is a funny Unicorn");
		
		charly.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/beschreibung")) ,"Charlie ist ein Einhorn");
		
		
		
		_fitt.doTopicCharacteristicsConstraint(_tm, _tmSchema, Namespaces.TMCL.TOPIC_OCCURRENCE_CONSTRAINT);
		
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.TOPIC_OCCURRENCE_CONSTRAINT)));
		assertEquals(null, _tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.TOPIC_NAME_CONSTRAINT)));
		
		
		Set<Topic> topics = _tmSchema.getTopics();

		assertEquals(20, topics.size());



	}

	
	// 7.9 Topic Role Constraint
	@Test
	public void testTopicRoleConstraint(){
		
		Topic bello = _tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/bello"));
		bello.addType(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Dog")));
		Association a1 = _a = _tm.createAssociation(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Family")));
		a1.createRole(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Owner")), john);
		a1.createRole(_tm.createTopicBySubjectIdentifier(_tm.createLocator("http://foo.net/Owned")), bello);

		_fitt.doTopicRoleConstraint(_tm, _tmSchema);
		
		Set<Topic> topics = _tmSchema.getTopics();
		assertEquals(29, topics.size());
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.TOPIC_ROLE_CONSTRAINT)));
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.CONSTRAINED_ROLE)));
	}

	
	
	// 7.10 Scope Constraint
//	@Test
//	public void testScopeConstraint(){
//		LinkedList<Topic> scope1 = new LinkedList<Topic>();
//		scope1.add(_tm.createTopicBySubjectIdentifier(_tm
//				.createLocator("http://foo.net/scope1")));
//		scope1.get(0).addType(_tm.createTopicBySubjectIdentifier(_tm
//				.createLocator("http://foo.net/Scope1Type")));
//		hansName = hans.createName("Hansus",  scope1 );
//
//		_fitt.doScopeConstraint(_tm, _tmSchema);
//
//	}
	
	
	// 7.11 Scope Required Constraint
	@Test
	public void testScopeRequiredConstraint(){
		
	}
	
	
	// 7.12 Reifier Constraint
	@Test
	public void testReifierConstraint() throws TMCLValidatorException{
		
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
		
		_fitt.doReifierConstraint(_tm, _tmSchema);

		
		Set<Topic> topics = _tmSchema.getTopics();
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.REIFIER_CONSTRAINT)));
		assertEquals(21, topics.size());

	
	}
	
	
	// 7.14 Association Role Constraint
	@Test
	public void testAssociationRoleConstraint(){
		
		
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
		
		_fitt.doAssociationRoleConstraintt(_tm, _tmSchema);
		Set<Topic> topics = _tmSchema.getTopics();
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.ASSOCIATION_ROLE_CONSTRAINT)));
		assertEquals(20, topics.size());
		
		
	}
	
	
	
	// 7.16 Occurrence Data Type Constraint
	@Test
	public void testOccurrenceDataTypeConstraint(){
		
		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeExternal")), _tm
				.createLocator("http://foo.net/externalOcc"));
		hans.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeExternal")), _tm
				.createLocator("http://foo.net/externalOcc1"));
		john.createOccurrence(_tm.createTopicBySubjectIdentifier(_tm
				.createLocator("http://foo.net/OccTypeFloat")),"5.3", _tm
				.createLocator(XmlSchemeDatatypes.XSD_FLOAT));
		
		_fitt.doOccurrenceDataTypeConstraint(_tm, _tmSchema);	
		
	
		Set<Topic> topics = _tmSchema.getTopics();
		assertNotNull(_tmSchema.getTopicBySubjectIdentifier(_tmSchema.createLocator(Namespaces.TMCL.OCCURRENCE_DATATYPE_CONSTRAINT)));
		assertEquals(14, topics.size());


	}
	

	 void validdate(TopicMap instances, TopicMap schema) throws TMCLValidatorException{
		TMCLValidator validator = new TMCLValidator();
		Map<Construct, Set<ValidationResult>> invalidConstructs = validator.validate(_tm, _tmSchema);
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
