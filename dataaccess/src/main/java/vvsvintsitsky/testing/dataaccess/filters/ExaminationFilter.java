package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.datamodel.VariousTexts_;

public class ExaminationFilter extends AbstractFilter<Examination> {
	private Long id;
	private Date beginDate;
	private Date endDate;
	private String name;
	private boolean isFetchQuestions;
	private boolean isFetchAccountProfile;
	private boolean isFetchResults;
	private boolean isFetchSubject;
	private Long accountProfileId;
	private Long subjectId;
	private String language;

	public boolean getIsFetchQuestions() {
		return isFetchQuestions;
	}

	public void setIsFetchQuestions(boolean isFetchQuestions) {
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
		return isFetchSubject;
	}

	public void setIsfetchSubject(boolean isfetchSubject) {
		this.isFetchSubject = isfetchSubject;
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

	public Long getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Long accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
		if (accountProfileId != null) {
			predicateList.add(accountProfilePredicate(cb, from));
		}
		if (subjectId != null) {
			predicateList.add(subjectPredicate(cb, from));
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
		return cb.equal(from.get(Examination_.beginDate), getBeginDate());
	}

	private Predicate endDatePredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.endDate), getEndDate());
	}

	private Predicate namePredicate(CriteriaBuilder cb, Root<Examination> from) {
		if (language == "ru") {
			return cb.equal(from.get(Examination_.examinationNames).get(LocalTexts_.rusText).get(VariousTexts_.txt),
					getName());
		}
		if (language == "en") {
			return cb.equal(from.get(Examination_.examinationNames).get(LocalTexts_.engText).get(VariousTexts_.txt),
					getName());
		}
		return null;
	}

	private Predicate accountProfilePredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.accountProfile).get(AccountProfile_.id), getAccountProfileId());
	}

	private Predicate subjectPredicate(CriteriaBuilder cb, Root<Examination> from) {
		return cb.equal(from.get(Examination_.subject).get(Subject_.id), getSubjectId());
	}

	public void setFetching(Root<Examination> from) {
		if (isFetchAccountProfile) {
			from.fetch(Examination_.accountProfile, JoinType.LEFT);
		}
		if (isFetchResults) {
			from.fetch(Examination_.results, JoinType.LEFT);
		}
		if (language.equals("ru")) {
			from.fetch(Examination_.examinationNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		}
		if (language.equals("en")) {
			from.fetch(Examination_.examinationNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
		}
		if (language.equals("ru")) {
			if (isFetchQuestions) {
				from.fetch(Examination_.questions, JoinType.LEFT).fetch(Question_.questionTexts, JoinType.LEFT)
						.fetch(LocalTexts_.rusText, JoinType.LEFT);
			}
			if (isFetchSubject) {
				from.fetch(Examination_.subject, JoinType.LEFT).fetch(Subject_.subjectNames, JoinType.LEFT)
						.fetch(LocalTexts_.rusText, JoinType.LEFT);
			}
		}
		if (language.equals("en")) {
			if (isFetchQuestions) {
				from.fetch(Examination_.questions, JoinType.LEFT).fetch(Question_.questionTexts, JoinType.LEFT)
						.fetch(LocalTexts_.engText, JoinType.LEFT);
			}
			if (isFetchSubject) {
				from.fetch(Examination_.subject, JoinType.LEFT).fetch(Subject_.subjectNames, JoinType.LEFT)
						.fetch(LocalTexts_.engText, JoinType.LEFT);
			}
		}
	}

	@Override
	public void setSorting(CriteriaQuery<Examination> query, Root<Examination> from) {
		if (getSortProperty() == Examination_.id) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}

		if (getSortProperty() == Examination_.beginDate) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Examination_.endDate) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (language.equals("ru")) {
			if (getSortProperty() == Examination_.examinationNames) {
				query.orderBy(new OrderImpl(
						from.get(Examination_.examinationNames).get(LocalTexts_.rusText).get(VariousTexts_.txt),
						isSortOrder()));
				return;
			}
			if (getSortProperty() == Subject_.subjectNames) {
				query.orderBy(new OrderImpl(from.get(Examination_.subject).get(Subject_.subjectNames)
						.get(LocalTexts_.rusText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
		}
		if (language.equals("en")) {
			if (getSortProperty() == Examination_.examinationNames) {
				query.orderBy(new OrderImpl(
						from.get(Examination_.examinationNames).get(LocalTexts_.engText).get(VariousTexts_.txt),
						isSortOrder()));
				return;
			}
			if (getSortProperty() == Subject_.subjectNames) {
				query.orderBy(new OrderImpl(from.get(Examination_.subject).get(Subject_.subjectNames)
						.get(LocalTexts_.engText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
		}

		if (getSortProperty() == AccountProfile_.id) {
			query.orderBy(new OrderImpl(from.get(Examination_.accountProfile).get(getSortProperty()), isSortOrder()));
			return;
		}

	}

}
