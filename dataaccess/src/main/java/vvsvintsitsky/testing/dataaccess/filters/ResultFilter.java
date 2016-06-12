package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Result_;

public class ResultFilter extends AbstractFilter<Result> {
	private Long id;
	private Integer points;
	private Long accountProfileId;
	private Long examinationId;
	private boolean isFetchExaminations;
	private boolean isFetchAccountProfile;
	private boolean isFetchAnswers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Long getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Long accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public Long getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}

	public boolean getIsFetchExaminations() {
		return isFetchExaminations;
	}

	public void setIsFetchExaminations(boolean isFetchExaminations) {
		this.isFetchExaminations = isFetchExaminations;
	}

	public boolean getIsFetchAccountProfile() {
		return isFetchAccountProfile;
	}

	public void setIsFetchAccountProfile(boolean isFetchAccountProfile) {
		this.isFetchAccountProfile = isFetchAccountProfile;
	}

	public boolean getIsFetchAnswers() {
		return isFetchAnswers;
	}

	public void setIsFetchAnswers(boolean isFetchAnswers) {
		this.isFetchAnswers = isFetchAnswers;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Result> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (points != null) {
			predicateList.add(pointsPredicate(cb, from));
		}
		if (accountProfileId != null) {
			predicateList.add(accountProfileIdPredicate(cb, from));
		}
		if (examinationId != null) {
			predicateList.add(examinationPredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Result> from) {
		return cb.equal(from.get(Result_.id), getId());
	}

	private Predicate pointsPredicate(CriteriaBuilder cb, Root<Result> from) {
		return cb.equal(from.get(Result_.points), getPoints());
	}
	
	private Predicate accountProfileIdPredicate(CriteriaBuilder cb, Root<Result> from) {
		return cb.equal(from.get(Result_.accountProfile).get(AccountProfile_.id), getAccountProfileId());
	}
	
	private Predicate examinationPredicate(CriteriaBuilder cb, Root<Result> from) {
		return cb.equal(from.get(Result_.examination).get(Examination_.id), getExaminationId());
	}

	public void setFetching(Root<Result> from) {
		if (isFetchExaminations) {
			from.fetch(Result_.examination, JoinType.LEFT);
		}
		if (isFetchAccountProfile) {
			from.fetch(Result_.accountProfile, JoinType.LEFT);
		}
		if (isFetchAnswers) {
			from.fetch(Result_.answers, JoinType.LEFT);
		}
	}

	@Override
	public void setSorting(CriteriaQuery<Result> query, Root<Result> from) {
		if(getSortProperty() == Result_.id || getSortProperty() == Result_.points) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if(getSortProperty() == Examination_.name) {
			query.orderBy(new OrderImpl(from.get(Result_.examination).get(Examination_.name), isSortOrder()));
			return;
		}
		if(getSortProperty() == AccountProfile_.lastName) {
			query.orderBy(new OrderImpl(from.get(Result_.accountProfile).get(AccountProfile_.lastName), isSortOrder()));
			return;
		}
	}

}
