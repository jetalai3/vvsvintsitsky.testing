package vvsvintsitsky.testing.webapp.page.result.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import vvsvintsitsky.testing.dataaccess.filters.ResultFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Result_;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.page.question.QuestionsPage;
import vvsvintsitsky.testing.webapp.page.result.ResultViewPage;
import vvsvintsitsky.testing.webapp.page.result.ResultsPage;

public class ResultsListPanel extends Panel {

	@Inject
	private ResultService resultService;

	private ResultFilter resultFilter;

	public ResultsListPanel(String id) {
		super(id);
			if(AuthorizedSession.get().getLoggedUser().getAccount().getRole() == UserRole.USER){
				resultFilter = new ResultFilter();
				resultFilter.setAccountProfileId(AuthorizedSession.get().getLoggedUser().getId());
			} else {
				resultFilter = new ResultFilter();
			}
		}

	protected void onInitialize(){
		super.onInitialize();
		
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		ResultsDataProvider resultsDataProvider = new ResultsDataProvider();
		DataView<Result> dataView = new DataView<Result>("rows", resultsDataProvider, 5) {

			private static final long serialVersionUID = -5461684826840940846L;

			@Override
			protected void populateItem(Item<Result> item) {
				Result result = item.getModelObject();

				item.add(new Label("result-id", result.getId()));
				item.add(new Label("examination-name", result.getExamination().getName()));
				item.add(new Label("account", result.getAccountProfile().getLastName()));
				item.add(new Label("points", result.getPoints()));
				
				item.add(new Link<Void>("view-link") {
					@Override
					public void onClick() {
						setResponsePage(new ResultViewPage(result));
					}
				});

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							resultService.delete(result.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new ResultsPage());
					}
				});

			}
		};
		rowsContainer.add(dataView);
		rowsContainer.add(new AjaxPagingNavigator("paging", dataView) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		});

		

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderId = new AjaxFallbackOrderByBorder("sort-id", Result_.id,
				resultsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		rowsContainer.add(ajaxFallbackOrderByBorderId);

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderExamination = new AjaxFallbackOrderByBorder("sort-examination",
				Examination_.name, resultsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		rowsContainer.add(ajaxFallbackOrderByBorderExamination);
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderAccount = new AjaxFallbackOrderByBorder("sort-account",
				AccountProfile_.lastName, resultsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		rowsContainer.add(ajaxFallbackOrderByBorderAccount);
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderPoints = new AjaxFallbackOrderByBorder("sort-points",
				Result_.points, resultsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		rowsContainer.add(ajaxFallbackOrderByBorderPoints);
		
		add(rowsContainer);
	
	}
	private class ResultsDataProvider extends SortableDataProvider<Result, Serializable> {

		public ResultsDataProvider() {
			super();
			resultFilter.setIsFetchAccountProfile(true);
			resultFilter.setIsFetchExaminations(true);
			
			setSort((Serializable) Result_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Result> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			resultFilter.setSortProperty((SingularAttribute) property);
			resultFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			resultFilter.setLimit((int) count);
			resultFilter.setOffset((int) first);
			return resultService.find(resultFilter).iterator();
		}

		@Override
		public long size() {
			return resultService.count(resultFilter);
		}

		@Override
		public IModel<Result> model(Result object) {
			return new Model(object);
		}

	}

}