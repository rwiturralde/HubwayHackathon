package com.gala.ui;

import org.apache.log4j.Logger;

public class HubwayDataPresenter implements IHubwayDataPresenter {

	protected final Logger _logger = Logger.getLogger(HubwayDataPresenter.class);
	protected IHubwayUI _view;
	protected Object _logicEngine;
	
	/**
	 * Hidden constructor to not allow construction without parameters
	 */
	protected HubwayDataPresenter() {
	}
	
	/**
	 * 
	 * @param view_
	 * @param logicEngine_
	 */
	public HubwayDataPresenter(IHubwayUI view_, Object logicEngine_) {
		_view = view_;
		_logicEngine = logicEngine_;
	}
	
	public void run() {
		_logger.debug("Running Hubway Data UI...");
		_view.launch();
		
		Object parameters = _view.getUserParameters(); 
		while(parameters != null) {
			_logger.info("Parameters are " + parameters);
			
			parameters = _view.getUserParameters();
		}
		
		_logger.debug("Run complete.");
	}
}
