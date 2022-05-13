import java.util.ArrayList;
import java.util.List;

class Employer{
	private int id;
	private String email;
	private String password;
	private String name;
	private String location;
	private List<Job> listings = new ArrayList<Job>();
	private float rating;

	public Employer(int id, String email, String password, String name, String location, float rating){
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.location = location;
		this.listings = listings;
		this.rating = rating;
	}

	public int getID(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	public String getName(){
		return name;
	}

	public String getLocation(){
		return location;
	}
	public List<Job> getListings(){
		return listings;
	}
	public float getRating(){
		return rating;
	}
}