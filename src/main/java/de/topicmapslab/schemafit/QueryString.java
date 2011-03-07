package de.topicmapslab.schemafit;

public class QueryString {

	// Declarations

	public final static String GETALLTOPICS = "// tm:subject >> indicators";
	public final static String GETTOPICSTYPES = "// tm:subject >> types >> indicators >> atomify";
	public final static String GETNAMETYPES = "// tm:subject >> characteristics tm:name >>  types >> indicators >> atomify";
	public final static String GETOCCURENCETYPES = "// tm:subject >> characteristics tm:occurrence >>  types >> indicators >> atomify";
	public final static String GETASSOCIATIONTYPES = "// tm:subject << players << roles >> types >> indicators >> atomify";
	public final static String GETROLETYPES = "// tm:subject >> roles >> types  >> indicators >> atomify";
	public final static String OVERLAPTYPES = "FOR $t IN tm:subject >> instances [. >> types] GROUP BY $0 RETURN $t, fn:best-identifier($t >> types , \"false\")";

	
	// Constraint

	public final static String SUBJECTIDENTIFIER = "FOR $t IN tm:subject >> instances [. >> types] " +
			"GROUP BY $0 RETURN $t , $t >> types >> indicators >> atomify , fn:count( $t >> indicators >> atomify)";
	public final static String SUBJECTLOCATOR = "FOR $t IN tm:subject >> instances [. >> types] " +
			"GROUP BY $0 RETURN $t , $t >> types >> indicators >> atomify , fn:count( $t >> locators >> atomify)";
	public final static String ITEMIDENTIFIER = "FOR $t IN tm:subject >> instances [. >> types] " +
			"GROUP BY $0 RETURN $t , $t >> types >> indicators >> atomify , fn:count( $t >> item >> atomify)";

	public final static String TOPICNAME = " tm:name ";

	public final static String TOPICOCCURRENCE = " tm:occurrence  ";

	public final static String TOPICNAMEVARIANTS = "FOR $t IN tm:subject >> instances [. >> characteristics tm:name >> variants] GROUP BY $0 "
			+ "RETURN {FOR $nt in  $t >> characteristics tm:name [. >> variants] >> types "
			+ "RETURN $t,  $t >> types >> indicators >> atomify, $nt >> indicators >> atomify, "
			+ "$t >> characteristics $nt >> variants >> scope >> indicators >> atomify , fn:count($t >> characteristics $nt >> variants )} ";

	public final static String TOPICROLE = "FOR $topicType IN fn:uniq ( // tm:subject >> types ) "
			+ "GROUP BY $0, $1 "
			+ "RETURN { "
			+ "FOR $topic IN $topicType >> instances "
			+ "FOR $roleType IN $topicType >> instances << players << typed "
			+ "RETURN $topicType >> indicators >> atomify, "
			+ "$roleType >> indicators >> atomify, "
			+ "$topic << players $roleType << roles >> types >> indicators >> atomify, "
			+ "fn:count( $topic << players $roleType ) } ";

	public final static String SCOPE = "FOR $scopedType " +
			"IN fn:uniq ( // tm:subject << players << roles << typed ) "
			+ "UNION fn:uniq ( // tm:subject >> characteristics << typed ) "
			+ "GROUP BY $0, $1 "
			+ "RETURN { FOR $themeType IN $scopeType >> typed >> scope >> types "
			+ "RETURN $scopedType >> indicators >> atomify, $themeType >> indicators >> atomify, "
			+ "fn:count ( $scopeType >> typed >> scope >> types INTERSECT $themeType >> instances ) }";
	

	public final static String REIFIER = "FOR $reifiable " +
			"IN fn:uniq ( // tm:subject << players UNION // tm:subject << players << roles " +
			"UNION // tm:subject >> characteristics ) " +
			"GROUP BY $0,$1 " +
			"RETURN $reifiable >> id, fn:best-identifier($reifiable << typed , \"false\"), " +
			"fn:best-identifier($reifiable << reifier >> types, \"false\")";
	
	
	
	
	
	
	

	public final static String ASSOCIATIONROLE = "FOR $assoc IN fn:uniq ( // tm:subject << players << roles) "
			+ "RETURN { FOR $role IN $assoc >> roles"
			+ " RETURN $ass >> id, fn:best-identifier( $assoc << typed , \"false\"), " +
					"fn:best-identifier( $role << typed, \"false\") }";
	
	
	
	
	public final static String ROLECOMBINATION = "FOR $ass IN // tm:subject << players << roles "
			+ "GROUP BY $0, $1, $2 RETURN { FOR $rt IN $ass >> roletypes " +
			"RETURN $ass >> id, fn:best-identifier( $ass << typed, \"false\"), " +
			"fn:best-identifier( $rt  , \"false\"), fn:best-identifier( $ass >> roles $rt >> players >> types , \"false\")}";

	public final static String OCCURENCEDATATYPE = "FOR $occ IN // tm:subject >> characteristics tm:occurrence "
			+ "RETURN { FOR $dt IN fn:has-datatype ( $occ ) RETURN fn:best-identifier( $occ << typed , \"false\"), $dt >> atomify }";

}