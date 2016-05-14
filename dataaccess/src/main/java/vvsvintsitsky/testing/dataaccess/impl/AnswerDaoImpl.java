package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AnswerDao;
import vvsvintsitsky.testing.datamodel.Answer;

@Repository
public class AnswerDaoImpl extends AbstractDaoImpl<Answer, Long> implements AnswerDao {

	protected AnswerDaoImpl() {
		super(Answer.class);
	}
}
