package vvsvintsitsky.testing.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Result.class)
public abstract class Result_ extends vvsvintsitsky.testing.datamodel.AbstractModel_ {

	public static volatile SingularAttribute<Result, AccountProfile> accountProfile;
	public static volatile SingularAttribute<Result, Examination> examination;
	public static volatile ListAttribute<Result, Answer> answers;
	public static volatile SingularAttribute<Result, Integer> points;

}

