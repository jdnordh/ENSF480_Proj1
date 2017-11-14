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
		//find if any Meetings need to have date finalized
		Meeting m = new Meeting();
		for(int i = 0 ; i< MeetingL.getMeetings().size(); i++){
			if (MeetingL.getMeetings().get(i).getmeetingState() == Meeting.waitingForFinalized){
				m = MeetingL.getMeetings().get(i);
				FinalizeTime(m);
			}
		}
	}

	private void FinalizeTime(Meeting m) {
		//first time to see if all people have picked one date
		ArrayList<DatePref>  strategyMeeting = m.getpreferedDateParticipant();
		ArrayList<Date> commonDates =  new ArrayList<Date>();
		while(commonDates.isEmpty()){
				int l = 0;
				commonDates = strategyMeeting.get(l).getprefDates();
				l++;
		}
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
		if(commonDates.get(0) != null){
				MeetingL.isChanged();
				m.setfinalizedDate(commonDates.get(0));
				return;
		}
		// if commonDates was empty not all participants selected a common date
		// we try to find a common Date with important people
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
		if(commonDates.get(0) != null){
			m.setmeetingState(m.DATEOUTSIDERANGE);
			MeetingL.isChanged();
			//notify participants that did not have this in their date range that is has been changed
			//they can either accept or decline
			m.setfinalizedDate(commonDates.get(0));
			return;
		}
		//no final date was found meeting inicator should be notified and should extend dates
		m.setmeetingState(m.NODATEFOUND);
		MeetingL.isChanged();
		
	}
	
}

