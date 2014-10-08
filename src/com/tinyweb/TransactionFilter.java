package com.tinyweb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionFilter implements Filter{

	private static Logger logger = LoggerFactory.getLogger(TransactionFilter.class);
	
	@Override
	public void destroy() {
		Repertory.close();
		logger.info("==========TransactionFilter destroy==========");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		boolean needRollback = false;
		Repertory.beginTransaction();
		try{
			chain.doFilter(req, res);
		}catch(Exception e){
			needRollback = true;
			logger.error("There is a exception.", e);
		}
		
		if(needRollback){
			Repertory.rollbackTransaction();
		}else{
			Repertory.commitTransaction();
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("==========TransactionFilter init==========");
	}
	
}
