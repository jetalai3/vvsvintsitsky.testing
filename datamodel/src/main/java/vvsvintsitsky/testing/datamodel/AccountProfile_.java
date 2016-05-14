package vvsvintsitsky.testing.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AccountProfile.class)
public abstract class AccountProfile_ extends vvsvintsitsky.testing.datamodel.AbstractModel_ {

	public static volatile SingularAttribute<AccountProfile, String> firstName;
	public static volatile SingularAttribute<AccountProfile, String> lastName;
	public static volatile ListAttribute<AccountProfile, Examination> examintations;
	public static volatile ListAttribute<AccountProfile, Result> results;
	public static volatile SingularAttribute<AccountProfile, Account> account;

}

