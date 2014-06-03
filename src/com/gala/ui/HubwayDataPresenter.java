package com.gala.ui;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.logicEngine.HubwayResults;
import com.gala.logicEngine.IRequestProcessor;

public class HubwayDataPresenter implements IHubwayDataPresenter {

	protected final Logger _logger = Logger.getLogger(HubwayDataPresenter.class);
	protected IHubwayUI _view;
	protected Map<RequestType, IRequestProcessor> _logicEngineMap;
	
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
	public HubwayDataPresenter(IHubwayUI view_, Map<RequestType, IRequestProcessor> logicEngine_) {
		_view = view_;
		_logicEngineMap = logicEngine_;
	}
	
	public void run() {
		_logger.debug("Running Hubway Data UI...");
		_view.launch();
		
		HubwayRequestParameters parameters = _view.getUserParameters(); 
		while(parameters != null) {
			_logger.info("Parameters are " + parameters);
			
			HubwayResults res = _logicEngineMap.get(parameters._requestType).processRequest(parameters);
			
			parameters = _view.getUserParameters();
		}
		_view.close();
		_logger.debug("Run complete.");
	}
}
