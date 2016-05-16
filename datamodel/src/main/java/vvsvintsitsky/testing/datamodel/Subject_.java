package vvsvintsitsky.testing.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subject.class)
public abstract class Subject_ extends vvsvintsitsky.testing.datamodel.AbstractModel_ {

	public static volatile ListAttribute<Subject, Examination> examinations;
	public static volatile SingularAttribute<Subject, String> name;
	public static volatile ListAttribute<Subject, Question> questions;

}

