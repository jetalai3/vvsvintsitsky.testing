package vvsvintsitsky.testing.webapp.page.examination.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.completing.CompletingPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationEditPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;

public class ExaminationsListPanel extends Panel {

	@Inject
	private ExaminationService examinationService;
	
	private String language;

	public ExaminationsListPanel(String id) {
		super(id);
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		ExaminationsDataProvider examinationsDataProvider = new ExaminationsDataProvider();
		DataView<Examination> dataView = new DataView<Examination>("rows", examinationsDataProvider, 5) {
			@Override
			protected void populateItem(Item<Examination> item) {
				Examination examination = item.getModelObject();

				language = Session.get().getLocale().getLanguage();
				
				item.add(new Label("id", examination.getId()));
				item.add(new Label("name", examination.getExaminationNames().getText(language)));
				item.add(DateLabel.forDatePattern("beginDate", Model.of(examination.getBeginDate()), "dd-MM-yyyy"));
				item.add(DateLabel.forDatePattern("endDate", Model.of(examination.getEndDate()), "dd-MM-yyyy"));
				item.add(new Label("subject", examination.getSubject().getSubjectNames().getText(language)));
				item.add(new Label("accountId", examination.getAccountProfile().getId()));

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new ExaminationEditPage(examination));
					}
				}.setVisible(AuthorizedSession.get().isSignedIn() && AuthorizedSession.get().getLoggedUser().getAccount().getRole().equals(UserRole.ADMIN)));

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							examinationService.delete(examination.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new ExaminationsPage());
					}
				}.setVisible(AuthorizedSession.get().isSignedIn() && AuthorizedSession.get().getLoggedUser().getAccount().getRole().equals(UserRole.ADMIN)));
				
				item.add(new Link<Void>("complete-link") {
					@Override
					public void onClick() {
						setResponsePage(new CompletingPage(examination));
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

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderId = new AjaxFallbackOrderByBorder("sort-id", Examination_.id,
				examinationsDataProvider) {
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
		
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderName = new AjaxFallbackOrderByBorder("sort-name", Examination_.examinationNames,
				examinationsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderName);
		
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderBeginDate = new AjaxFallbackOrderByBorder("sort-beginDate", Examination_.beginDate,
				examinationsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderBeginDate);
		
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderEndDate = new AjaxFallbackOrderByBorder("sort-endDate", Examination_.endDate,
				examinationsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderEndDate);
		
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderSubject = new AjaxFallbackOrderByBorder("sort-subject", Examination_.subject,
				examinationsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderSubject);
		
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderAccountProfileId = new AjaxFallbackOrderByBorder("sort-accountProfileId", Examination_.accountProfile,
				examinationsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderAccountProfileId);
		add(rowsContainer);
	}

	private class ExaminationsDataProvider extends SortableDataProvider<Examination, Serializable> {

		private ExaminationFilter examinationFilter;
		

		public ExaminationsDataProvider() {
			super();
			examinationFilter = new ExaminationFilter();
			examinationFilter.setIsfetchSubject(true);
			examinationFilter.setIsFetchAccountProfile(true);
			examinationFilter.setIsFetchQuestions(true);
			setSort((Serializable) Examination_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Examination> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			String language = Session.get().getLocale().getLanguage();
			examinationFilter.setSortProperty((SingularAttribute) property);
			examinationFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			examinationFilter.setLanguage(language);
			examinationFilter.setLimit((int) count);
			examinationFilter.setOffset((int) first);
			return examinationService.find(examinationFilter).iterator();
		}

		@Override
		public long size() {
			return examinationService.count(examinationFilter);
		}

		@Override
		public IModel<Examination> model(Examination object) {
			return new Model(object);
		}

	}

}
