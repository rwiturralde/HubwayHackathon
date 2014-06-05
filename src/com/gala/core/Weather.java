package com.gala.core;

public class Weather {
	protected Temperature _temperature;
	protected boolean _gonRain;
	
	public Weather() {
		_temperature = null;
		_gonRain = false;
	}
	
	public Weather(Temperature temperature_, boolean gonRain_) {
		_temperature = temperature_;
		_gonRain = gonRain_;
	}
	
	public Temperature getTemperature() {
		return _temperature;
	}
	public void setTemperature(Temperature temperature) {
		this._temperature = temperature;
	}
	public boolean isGonRain() {
		return _gonRain;
	}
	public void setGonRain(boolean gonRain) {
		this._gonRain = gonRain;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Weather [_temperature=");
		builder.append(_temperature);
		builder.append(", _gonRain=");
		builder.append(_gonRain);
		builder.append("]");
		return builder.toString();
	}
	
	public String printSummary() {
		StringBuilder builder = new StringBuilder();
		builder.append("Temperature=");
		builder.append(_temperature);
		builder.append("Precipitation=");
		builder.append(_gonRain);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_gonRain ? 1231 : 1237);
		result = prime * result
				+ ((_temperature == null) ? 0 : _temperature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weather other = (Weather) obj;
		if (_gonRain != other._gonRain)
			return false;
		if (_temperature != other._temperature)
			return false;
		return true;
	}

}
