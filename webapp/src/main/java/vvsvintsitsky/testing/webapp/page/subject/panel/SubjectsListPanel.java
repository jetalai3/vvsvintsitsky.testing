package vvsvintsitsky.testing.webapp.page.subject.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;
import vvsvintsitsky.testing.webapp.page.subject.SubjectEditPanel;
import vvsvintsitsky.testing.webapp.page.subject.SubjectsPage;

public class SubjectsListPanel extends Panel {

	@Inject
	private SubjectService subjectService;

	private SubjectFilter subjectFilter;

	private String language;
	
	private Logger logger;
	
	public SubjectsListPanel(String id) {
		super(id);
		this.logger = LoggerFactory.getLogger(SubjectsListPanel.class);
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		SubjectsDataProvider subjectsDataProvider = new SubjectsDataProvider();
		DataView<Subject> dataView = new DataView<Subject>("rows", subjectsDataProvider, 5) {
			@Override
			protected void populateItem(Item<Subject> item) {
				Subject subject = item.getModelObject();
				LocalTexts subjectNames = subject.getSubjectNames();
				item.add(new Label("subject-id", subject.getId()));
				item.add(new Label("subject-name", subjectNames.getText(language)));

				ModalWindow modalWindow = new ModalWindow("modal");
				item.add(modalWindow);
				item.add(new AjaxLink<Void>("edit-link") {

					private static final long serialVersionUID = -4197818843372247766L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalWindow.setContent(new SubjectEditPanel(modalWindow, subject));
						modalWindow.show(target);

					}
				});

				item.add(new AjaxLink("delete-link") {

					private static final long serialVersionUID = -343889365476492210L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						logger.warn("User {} attepmpted delete subject", AuthorizedSession.get().getLoggedUser().getId());
						try {
							subjectService.delete(subject.getId());
						} catch (PersistenceException e) {
							System.out.println("caught PersistenceException");
							logger.error("User {} failed to delete subject", AuthorizedSession.get().getLoggedUser().getId());
						}
						target.add(rowsContainer);
					}
				});

				modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

					private static final long serialVersionUID = 8965470088247585358L;

					@Override
					public void onClose(AjaxRequestTarget target) {
						target.add(rowsContainer);
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


		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderId = new AjaxFallbackOrderByBorder("sort-id", Subject_.id,
				subjectsDataProvider) {
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

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderText = new AjaxFallbackOrderByBorder("sort-name",
				Subject_.subjectNames, subjectsDataProvider) {
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
		rowsContainer.add(ajaxFallbackOrderByBorderText);
		add(rowsContainer);
	}

	private class SubjectsDataProvider extends SortableDataProvider<Subject, Serializable> {

		private SubjectFilter subjectFilter;

		public SubjectsDataProvider() {
			super();
			subjectFilter = new SubjectFilter();
			setSort((Serializable) Question_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Subject> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			language = Session.get().getLocale().getLanguage();
			subjectFilter.setSortProperty((SingularAttribute) property);
			subjectFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			subjectFilter.setLanguage(language);
			
			subjectFilter.setLimit((int) count);
			subjectFilter.setOffset((int) first);
			return subjectService.find(subjectFilter).iterator();
		}

		@Override
		public long size() {
			return subjectService.count(subjectFilter);
		}

		@Override
		public IModel<Subject> model(Subject object) {
			return new Model(object);
		}

	}

}
