package server;

import java.util.*;

import structures.*;

/**
 * 
 */
public class FindMeetingTimeStrategy1 implements FindMeetingTimeStrategyInterface {

    /**
     * Default constructor
     */
    public FindMeetingTimeStrategy1() {
    	
    }

	@Override
	public void FindMeetingTimes(Meeting m) {
		ArrayList<DatePref>  strategyMeeting = m.getpreferedDateParticipant();
		ArrayList<DatePref> importantPeopleTimes = new ArrayList<DatePref>();
		//find all important participants
		for(int i = 0; i< strategyMeeting.size(); i ++){
			//for every 
			if(strategyMeeting.get(i).getparticipant().getImportant()  == true){
				importantPeopleTimes.add(strategyMeeting.get(i));
			}
		}
		//scan to see if all have the same
		ArrayList<Date> commonDates = importantPeopleTimes.get(1).getprefDates();
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

