package server;

import java.util.*;

import structures.*;

/**
 * 
 */
public class FindMeetingTimeStrategy1 implements FindMeetingTimeStrategyInterface {
	private MeetingList MeetingL;
    /**
     * 
     * Default constructor
     */
    public FindMeetingTimeStrategy1() {
    	MeetingL = MeetingList.getMeetingList();
    }
    
	@Override
	public void FindMeetingTimes() {
		Meeting m = new Meeting();
		for(int i = 0 ; i< MeetingL.getMeetings().size(); i++){
			if (MeetingL.getMeetings().get(i).getmeetingState() == Meeting.waitingForFinalized)
				 m = MeetingL.getMeetings().get(i);
		}
		ArrayList<DatePref>  strategyMeeting = m.getpreferedDateParticipant();
		ArrayList<Date> commonDates = strategyMeeting.get(0).getprefDates();
		for (int i = 1; i < strategyMeeting.size(); i ++){
			//check  if all common dates to be shown in their prefDates
			for(int j = 0 ; j< commonDates.size(); j++ ){
				Boolean isCopy =  false;
				for(int k = 0; k< strategyMeeting.get(i).getprefDates().size(); k ++){
					if(commonDates.get(j) == strategyMeeting.get(i).getprefDates().get(k))
						isCopy = true;
				}
				if(isCopy == false){
					commonDates.remove(j);
				}
			}
		}
		//if any remain set date and exist
		if(commonDates.get(0) == null){
				m.setfinalizedDate(commonDates.get(0));
				return;
		}
		
		
		
		ArrayList<DatePref> importantPeopleTimes = new ArrayList<DatePref>();
		//find all important participants
		for(int i = 0; i< strategyMeeting.size(); i ++){
			//for every 
			if(strategyMeeting.get(i).getparticipant().getImportant()  == true){
				importantPeopleTimes.add(strategyMeeting.get(i));
			}
		}
		//scan to see if all have the same
		commonDates = importantPeopleTimes.get(0).getprefDates();
		//first dates are scanned into commonDates
		//for all important people
		for(int i =1; i< importantPeopleTimes.size();i++){
			
			//check  if all common dates to be shown in their prefDates
			for(int j = 0 ; j< commonDates.size(); j++ ){
				Boolean isCopy =  false;
				for(int k =0; k< importantPeopleTimes.get(i).getprefDates().size(); k++){
					
					if(commonDates.get(j) == importantPeopleTimes.get(i).getprefDates().get(k))
						isCopy = true;
				}
				//if not remove the date
				if(isCopy == false){
					commonDates.remove(j);
				}
			}
		}
		//if any remain set date and exist
		if(commonDates.get(0) == null){
			m.setfinalizedDate(commonDates.get(0));
			return;
		}
	}
}

