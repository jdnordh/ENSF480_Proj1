package server;

import java.util.ArrayList;
import java.util.Date;

import structures.*;

public class TimeStrat2 implements FindMeetingTimeStrategyInterface{

	public TimeStrat2() {}
	
	@Override
	public void FindMeetingTimes() {
		
		MeetingList ml = MeetingList.getMeetingList();
		ArrayList<Meeting> list = ml.getMeetings();
		
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).getmeetingState() == Meeting.waitingForFinalized){
				
				Meeting check = list.get(i);
				
				ArrayList<Date> importantDates = new ArrayList<Date>();
				
				//find important participants dates
				for (int j = 0; j < check.getpreferedDateParticipant().size(); i++){
					if (check.getpreferedDateParticipant().get(j).getparticipant().isImportant()){
						
					}
				}
				
				
			}
		}
	}

}
