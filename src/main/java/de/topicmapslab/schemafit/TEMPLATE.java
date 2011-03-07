package de.topicmapslab.schemafit;

public class TEMPLATE {

	// Declarations
	/**
	 * 6.7 Overlap Declaration templatedef has-occurrence($tt, $ot, $min, $max)
  ?c isa tmcl:topic-occurrence-constraint;
    tmcl:card-min: $min;
    tmcl:card-max: $max.
  
  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)  
  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $ot)
end
	 */
	public final static String OVERLAPTYPES = " def overlaps($tt1, $tt2) " +
			"?c isa tmcl:overlap-declaration. " +
			"tmcl:overlaps(tmcl:allows : ?c, tmcl:allowed : $tt1) " +
			"tmcl:overlaps(tmcl:allows : ?c, tmcl:allowed : $tt2) " +
		"end ";

	
	
	// Constraints
	
	/**
	 * 7.3 Subject Identifier Constraint template
	 */
	public final static String SUBJECTIDENTIFIERCONSTRAINT = "def has-subject-identifier($tt, $min, $max, $regexp)" +
			"  ?c isa tmcl:subject-identifier-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max;" +
			"    tmcl:regexp: $regexp." +
			"  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt) " +
			"end ";
	
	
	/**
	 * 7.4 Subject Locator Constraint template
	 */
	public final static String SUBJECTLOCATORCONSTRAINT = "def has-subject-locator($tt, $min, $max, $regexp)" +
			"  ?c isa tmcl:subject-locator-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max;" +
			"    tmcl:regexp: $regexp." +
			"  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt) " +
			"end ";
	
	
	/**
	 * 7.5 Item Identifier Constraint template
	 */
	public final static String ITEMIDENTIFIERCONSTRAINT = "def has-item-identifier($tt, $min, $max, $regexp)" +
			"  ?c isa tmcl:item-identifier-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max;" +
			"    tmcl:regexp: $regexp." +
			"  tmcl:constrained-construct(tmcl:constraint : ?c, tmcl:constrained : $tt) " +
			"end ";
	
	
	/**
	 * 7.6 Topic Name Constraint template
	 */
	public final static String TOPICNAMECONSTRAINT = "def has-name($tt, $nt, $min, $max)" +
			"  ?c isa tmcl:topic-name-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max. " +
			"tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)  " +
			"tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $nt) " +
			"end";
	
	
	/**
	 * 7.7 Variant Name Constraint template
	 */
	public final static String VARIANTNAMECONSTRAINT = "def has-variant($tt, $nt, $t, $min, $max)" +
			" ?c isa tmcl:variant-name-constraint;" +
			"   tmcl:card-min: $min;" +
			"   tmcl:card-max: $max." +
			" tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)" +
			" tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $nt)" +
			" tmcl:constrained-scope-topic(tmcl:constraint : ?c, tmcl:constrained : $t) " +
			"end";
	
	/**
	 * 7.8 Topic Occurrence Constraint template
	 */
	public final static String TOPICOCCURENCECONSTRAINT = "def has-occurrence($tt, $ot, $min, $max)" +
			"  ?c isa tmcl:topic-occurrence-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max." +
			"  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)  " +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $ot) " +
			"end";
	
	
	/**
	 * 7.9 Topic Role Constraint template
	 */
	public final static String TOPICROLECONSTRAINT = "def plays-role($tt, $rt, $at, $min, $max)" +
			"  ?c isa tmcl:topic-role-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max." +
			"  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)  " +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $at)" +
			"  tmcl:constrained-role(tmcl:constraint : ?c, tmcl:constrained : $rt) " +
			"end";
	
	
	/**
	 * 7.10 Scope Constraint template
	 */
	public final static String SCOPECONSTRAINT = "def has-scope($st, $tt, $min, $max)" +
			"  ?c isa tmcl:scope-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max." +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $st)" +
			"  tmcl:constrained-scope(tmcl:constraint : ?c, tmcl:constrained : $tt) " +
			"end";
		
	
	/**
	 * 7.11 Scope Required Constraint template
	 */
	public final static String SCOPEREQUIREDCONSTRAINT = "def requires-scope($tt, $st, $t, $min, $max)" +
			"  ?c isa tmcl:scope-required-constraint;" +
			"  tmcl:card-min: $min;" +
			"  tmcl:card-max: $max." +
			"  tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt) " +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $st)" +
			"  tmcl:constrained-scope-topic(tmcl:constraint : ?c, tmcl:constrained : $t) " +
			"end";
	
	
	/**
	 * 7.12 Reifier Constraint template
	 */
	public final static String REIFIERCONSTRAINT = " def must-have-reifier($st, $tt) " +
			"?c isa tmcl:reifier-constraint;" +
			"    tmcl:card-min: 1;" +
			"    tmcl:card-max: 1." +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)" +
			"  tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: $tt) " +
			"end " +
			"def cannot-have-reifier($st)" +
			"  ?c isa tmcl:reifier-constraint;" +
			"    tmcl:card-min: 0;" +
			"    tmcl:card-max: 0." +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)" +
			"  tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: tmdm:subject) " +
			"end " +
			"def may-have-reifier($st, $tt)" +
			"  ?c isa tmcl:reifier-constraint;" +
			"    tmcl:card-min: 0;" +
			"    tmcl:card-max: 1." +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)" +
			"  tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: $tt) " +
			"end ";
	
	
	
	/**
	 * 7.13 Topic Reifies Constraint template
	 */
	public final static String TOPICREIFIESCONSTRAINT = "def must-reify($tt, $st)" +
			"  ?c isa tmcl:topic-reifies-constraint;" +
			"    tmcl:card-min: 1;" +
			"    tmcl:card-max: 1." +
			"  tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)" +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st) " +
			"end " +
			"def cannot-reify($tt)" +
			"  ?c isa tmcl:topic-reifies-constraint;" +
			"    tmcl:card-min: 0;" +
			"    tmcl:card-max: 0." +
			"  tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt) " +
			"end " +
			"def may-reify($tt, $st)" +
			"  ?c isa tmcl:topic-reifies-constraint;" +
			"    tmcl:card-min: 0;" +
			"    tmcl:card-max: 1." +
			"  tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)" +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st) " +
			"end";
	
	
	/**
	 * 7.14 Association Role Constraint template
	 */
	public final static String ASSOCIATIONROLECONSTRAINT = "def has-role($at, $rt, $min, $max)" +
			"  ?c isa tmcl:association-role-constraint;" +
			"    tmcl:card-min: $min;" +
			"    tmcl:card-max: $max." +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $at)" +
			"  tmcl:constrained-role(tmcl:constraint : ?c, tmcl:constrained : $rt) " +
			"end";
	
	/**
	 * 7.15 Role Combination Constraint template
	 */
	public final static String ROLECOMBINATIONCONSTRAINT = "def role-combination($at, $rt, $tt, $ort, $ott)" +
			"  ?c isa tmcl:role-combination-constraint." +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $at)" +
			"  tmcl:constrained-role(tmcl:constraint: ?c, tmcl:constrained: $rt)" +
			"  tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)" +
			"  tmcl:other-constrained-role(tmcl:constraint: ?c, tmcl:constrained: $ort)" +
			"  tmcl:other-constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $ott) " +
			"end ";
	
	
	/**
	 * 7.16 Occurrence Data Type Constraint template
	 */
	public final static String OCCURENCEDATATYPECONSTRAINT = "def has-datatype($ot, $dt)" +
			"  ?c isa tmcl:occurrence-datatype-constraint;" +
			"    tmcl:datatype: $dt." +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $ot) " +
			"end ";
	
	
	/**
	 * 7.17 Unique Value Constraint template
	 */
	public final static String UNIQUEVALUECONSTRAINT = "def has-unique-value($st)" +
			"  ?c isa tmcl:unique-value-constraint." +
			"  tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $st) " +
			"end ";
	
	
	/**
	 * 7.18 Regular Expression Constraint template
	 */
	public final static String REGULAREXPRESSIONCONSTRAINT = "def matches-regexp($st, $regexp)" +
			"  ?c isa tmcl:regular-expression-constraint;" +
			"    tmcl:regexp: $regexp." +
			"  tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st) " +
			"end ";
}











