package vvsvintsitsky.testing.dataaccess.filters;


	import javax.persistence.criteria.CriteriaBuilder;
	import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
	import javax.persistence.metamodel.SingularAttribute;

	import vvsvintsitsky.testing.datamodel.AbstractModel;

	public abstract class AbstractFilter<T> {

		private SingularAttribute sortProperty;
		private boolean sortOrder;
		private Integer offset;
		private Integer limit;
		public abstract Predicate getQueryPredicate(CriteriaBuilder cb, Root<T> from);
		public SingularAttribute getSortProperty() {
			return sortProperty;
		}

		public void setSortProperty(SingularAttribute sortProperty) {
			this.sortProperty = sortProperty;
		}

		public boolean isSortOrder() {
			return sortOrder;
		}

		public void setSortOrder(boolean sortOrder) {
			this.sortOrder = sortOrder;
		}

		public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

	}

