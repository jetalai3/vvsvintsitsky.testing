package vvsvintsitsky.testing.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subject.class)

public class Subject_ extends AbstractModel_ {
	public static volatile SingularAttribute<Subject, String> name;
	public static volatile ListAttribute<Subject, Examination> examintations;
	public static volatile ListAttribute<Subject, Question> questions;
}
