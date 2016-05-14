package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.QuestionDao;
import vvsvintsitsky.testing.datamodel.Question;

@Repository
public class QuestionDaoImpl extends AbstractDaoImpl<Question, Long> implements QuestionDao {

	protected QuestionDaoImpl() {
		super(Question.class);
	}
}
