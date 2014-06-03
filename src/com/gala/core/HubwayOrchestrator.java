package com.gala.core;

import java.util.List;

import org.apache.log4j.Logger;

import com.gala.dataLoader.IDataLoader;
import com.gala.ui.IHubwayDataPresenter;

/**
 * @author Roberto
 *
 */
public class HubwayOrchestrator {

	protected final Logger _logger = Logger.getLogger(HubwayOrchestrator.class);
	protected List<IDataLoader> _dataLoaders;
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
	public HubwayOrchestrator(List<IDataLoader> dataLoaders_, IHubwayDataPresenter uiPresenter_) {
		_dataLoaders = dataLoaders_;
		_uiPresenter = uiPresenter_;
	}


	public void init() {
		for (IDataLoader dataLoader : _dataLoaders){
			if (!dataLoader.loadData()) {		
				_logger.info("Data load failed.  Returning.");		
				return;
			} 
		}
		_logger.info("Data load successful.  Launching UI");
		_uiPresenter.run();
	}
}
