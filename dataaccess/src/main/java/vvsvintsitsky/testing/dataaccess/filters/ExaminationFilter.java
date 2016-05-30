package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;

public class ExaminationFilter extends AbstractFilter<Examination> {
	private Long id;
	private Date beginDate;
	private Date endDate;
	private String name;
	private boolean isFetchQuestions;
	private boolean isFetchAccountProfile;
	private boolean isFetchResults;
	private boolean isfetchSubject;

	public Boolean getIsFetchQuestions() {
		return isFetchQuestions;
	}

	public void setIsFetchQuestions(Boolean isFetchQuestions) {
		this.isFetchQuestions = isFetchQuestions;
	}

	public boolean getIsFetchAccountProfile() {
		return isFetchAccountProfile;
	}

	public void setIsFetchAccountProfile(boolean isFetchAccountProfile) {
		this.isFetchAccountProfile = isFetchAccountProfile;
	}

	public boolean getIsFetchResults() {
		return isFetchResults;
	}

	public void setIsFetchResults(boolean isFetchResults) {
		this.isFetchResults = isFetchResults;
	}

	public boolean getIsfetchSubject() {
		return isfetchSubject;
	}

	public void setIsfetchSubject(boolean isfetchSubject) {
		this.isfetchSubject = isfetchSubject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Examination> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (beginDate != null) {
			predicateList.add(beginDatePredicate(cb, from));
		}
		if (endDate != null) {
			predicateList.add(endDatePredicate(cb, from));
		}
		if (name != null) {
			predicateList.add(namePredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.id), getId());
	}

	private Predicate beginDatePredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.beginDate), getId());
	}

	private Predicate endDatePredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.endDate), getId());
	}

	private Predicate namePredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.name), getId());
	}

	public void setFetching(Root<Examination> from) {
		if (isFetchQuestions) {
			from.fetch(Examination_.questions, JoinType.LEFT);
		}
		if (isFetchAccountProfile) {
			from.fetch(Examination_.accountProfile, JoinType.LEFT);
		}
		if (isFetchResults) {
			from.fetch(Examination_.results, JoinType.LEFT);
		}
		if (isfetchSubject) {
			from.fetch(Examination_.subject, JoinType.LEFT);
		}
	}

}
