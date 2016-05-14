package vvsvintsitsky.testing.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Question.class)
public abstract class Question_ extends vvsvintsitsky.testing.datamodel.AbstractModel_ {

	public static volatile SingularAttribute<Question, String> subject;
	public static volatile ListAttribute<Question, Answer> answers;
	public static volatile SingularAttribute<Question, String> text;

}

