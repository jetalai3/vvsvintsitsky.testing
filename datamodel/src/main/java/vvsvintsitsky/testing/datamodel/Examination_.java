package vvsvintsitsky.testing.datamodel;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Examination.class)
public abstract class Examination_ extends vvsvintsitsky.testing.datamodel.AbstractModel_ {

	public static volatile SingularAttribute<Examination, Date> beginDate;
	public static volatile SingularAttribute<Examination, AccountProfile> accountProfile;
	public static volatile SingularAttribute<Examination, Date> endDate;
	public static volatile SingularAttribute<Examination, String> name;
	public static volatile ListAttribute<Examination, Question> questions;
	public static volatile ListAttribute<Examination, Result> results;

}

