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
			if(MeetingL.getMeetings().get(i).getParticipants().size() == MeetingL.getMeetings().get(i).getPreferedDates().size()){
			//if (MeetingL.getMeetings().get(i).getmeetingState() == Meeting.waitingForFinalized)
				m = MeetingL.getMeetings().get(i);
				FinalizeTime(m);
			}
		}
		for(int i = 0;  i < MeetingL.getMeetings().size(); i++){
			if (MeetingL.getMeetings().get(i).getmeetingState() == Meeting.serverPickLocationPref ){
				m = MeetingL.getMeetings().get(i);
				FinalizedLocation(m);
		}
		}
	}

	private void FinalizedLocation(Meeting m) {
		ArrayList<Location> Locations = m.getLocations();
		//int x to see which location is selected the most
		int x[] = new int[Locations.size()];
		for( int i = 0; i< Locations.size() ; i ++){
			for(int j = 0 ; j < Locations.size() ; i++){
				if(Locations.get(i) == Locations.get(j)){
					//increase x to see the location was chosen again
					x[i]++; 
				}
			}
		}
		int max = 0;
		for( int i = 0; i< Locations.size() ; i ++){
			if(x[i]>max)
				max = x[i];
		}
		m.setLocation(Locations.get(max));
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
			if(strategyMeeting.get(i).getparticipant().isImportant()  == true){
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
	public static void main(String[] args) {
		Participant P = new Participant("Jacob", "Jacob",true);
		Participant P2 = new Participant("Jacob2", "Jacob2",true);
		ArrayList<Participant> P3 = new ArrayList<Participant>();
		P3.add(P);
		P3.add(P2);
		Location L = new Location("L","l","l");
		User p4 = new User("Jacob3", "Jacob3","Jacob3");
		//Date
		//participants,Location, Description, MeetingIniatorPrefDates , MeetingIniator
		//eeting M = new Meeting(P3,"this",);
		
		
	}
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
}

