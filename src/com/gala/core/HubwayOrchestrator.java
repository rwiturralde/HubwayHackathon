package com.gala.core;

import com.gala.ui.HubwayDataPresenter;

/**
 * @author Roberto
 *
 */
public class HubwayOrchestrator {

	protected Object _dataLoader;
	protected HubwayDataPresenter _uiPresenter;
	
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
	public HubwayOrchestrator(Object dataLoader_, HubwayDataPresenter uiPresenter_) {
		_dataLoader = dataLoader_;
		_uiPresenter = uiPresenter_;
	}

	public void init() {
		// Tell loader to load data

		_uiPresenter.run();
	}
}
