package model;

import java.util.List;

public class Activity {
	String startTimeStamp;
	String endTimeStamp;
	List<AccelerometerData> data;
	String activeStatus;
	
	
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(String startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public String getEndTimeStamp() {
		return endTimeStamp;
	}
	public void setEndTimeStamp(String endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}
	public List<AccelerometerData> getData() {
		return data;
	}
	public void setData(List<AccelerometerData> data) {
		this.data = data;
	}
	
	
}
