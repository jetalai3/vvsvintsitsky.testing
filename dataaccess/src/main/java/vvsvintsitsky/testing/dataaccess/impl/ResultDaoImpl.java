package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.ResultDao;
import vvsvintsitsky.testing.datamodel.Result;

@Repository
public class ResultDaoImpl extends AbstractDaoImpl<Result, Long> implements ResultDao {

	protected ResultDaoImpl() {
		super(Result.class);
	}
}
