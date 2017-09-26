package solution.movielens;
import java.io.*;
import java.util.*;
public class ReadRatings {
	
	
	public static Map<Integer,Map<Integer,Rating>> readRatingData() {
		//HashMap which is used User Id as Key and HashMap as Value in which Movie Id is key and Rating object is value.
		Map<Integer,Map<Integer,Rating>> list = new TreeMap<Integer,Map<Integer,Rating>>(); 
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\abc\\eclipse-workspace\\movieslens.m1\\src\\solution\\movielens\\ratings.dat"))) {
			String [] temp;
			String line = br.readLine();
			String delims = "::";
		//Store file line(Rating Data) In Rating HashMap
			   while ((line = br.readLine()) != null) {
				   temp = line.split(delims);
			       Rating ratingTemp = new Rating();
			       int userIdtemp = Integer.parseInt(temp[0]);
			       int movieIdtemp = Integer.parseInt(temp[1]);
			       ratingTemp.userId =userIdtemp;
			       ratingTemp.movieId = movieIdtemp;
			       ratingTemp.rating = Integer.parseInt(temp[2]);
			       ratingTemp.timeStamp = Integer.parseInt(temp[3]);
			       if(list.containsKey(userIdtemp)) {
			    	   
			    	   list.get(userIdtemp).put(movieIdtemp, ratingTemp);
			       }else {
			    	   Map<Integer,Rating> userMovies = new TreeMap<Integer,Rating>();
			    	   userMovies.put(movieIdtemp, ratingTemp);
			       list.put(userIdtemp,userMovies);
			       }
			       
			   }
			   
			   
			}catch (IOException e) {
				e.printStackTrace();
			}
		return list;
	}
	
}
//Class for Rating Object which is contain Rating data 
class Rating{
	
	int userId;
	int movieId;
	double rating;
	int timeStamp;
	void getrating() {
		System.out.println(userId+" "+movieId+" "+rating+" "+timeStamp+" ");
	}
}