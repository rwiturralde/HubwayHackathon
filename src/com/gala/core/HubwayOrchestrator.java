package com.gala.core;

import org.apache.log4j.Logger;

import com.gala.dataLoader.IDataLoader;
import com.gala.ui.HubwayDataPresenter;
import com.gala.ui.IHubwayDataPresenter;

/**
 * @author Roberto
 *
 */
public class HubwayOrchestrator {

	protected final Logger _logger = Logger.getLogger(HubwayOrchestrator.class);
	protected IDataLoader _dataLoader;
	protected IHubwayDataPresenter _uiPresenter;
	
	/**
	 * Hidden public constructor.  
	 */
	protected HubwayOrchestrator() {
		
	}
	
	/**
	 * Constructor accepting all necessary arguments.
	 * 
	 * @param dataLoader_ Object responsible for populating data required for analysis
	 * @param userInterface_ User interface collecting input from user to drive engine
	 */
	public HubwayOrchestrator(IDataLoader dataLoader_, IHubwayDataPresenter uiPresenter_) {
		_dataLoader = dataLoader_;
		_uiPresenter = uiPresenter_;
	}


	public void init() {
		if (_dataLoader.loadData()) {
			_logger.info("Data load successful.  Launching UI");
			_uiPresenter.run();
		} else {
			_logger.info("Data load failed.  Returning.");
		}
	}
}
