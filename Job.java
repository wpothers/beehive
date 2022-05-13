import java.util.ArrayList;
import java.util.List;

class Job{
	private int id;
	private String startTime;
	private String endTime;
	private Float wage;
	private String location;
	private List<User> applicants = new ArrayList<User>();
	private int creatorID;
	private boolean accepted;
	private User worker;

	public Job(int id, String startTime, String endTime, Float wage, String location, int creatorID, boolean accepted){
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.wage = wage;
		this.location = location;
		this.applicants = applicants;
		this.creatorID = creatorID;
		this.accepted = accepted;
		this.worker = worker;
	}

	public int getID(){
		return id;
	}

	public String getStartTime(){
		return startTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public Float getWage(){
		return wage;
	}

	public String getLocation(){
		return location;
	}

	public List<User> getApplicants(){
		return applicants;
	}

	public int getCreatorID(){
		return creatorID;
	}
	public boolean getAccepted(){
		return accepted;
	}

	public User getWorker(){
		return worker;
	}

}