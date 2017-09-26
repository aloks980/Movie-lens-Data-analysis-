package solution.movielens;
import java.io.*;
import java.util.*;
public class ReadMovies {
	public static Map<Integer, Movies> readMovieList() {
		Map<Integer, Movies> list = new TreeMap<Integer,Movies>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\abc\\eclipse-workspace\\movieslens.m1\\src\\solution\\movielens\\movies.dat"))) {
			String [] temp;
			String line = br.readLine();
			String delims = "::";
			
			//int rowCounter = 0;   
			
			   while ((line = br.readLine()) != null) {
				   if (!line.equals("")) {
				   line = line.trim();
				   temp = line.split(delims);
			       Movies movieTemp = new Movies();
			       int movieId = Integer.parseInt(temp[0]);
			       movieTemp.movieId = movieId;
			       movieTemp.movieTitle = temp[1];
			       String dlim = "\\|";
			       String[] genTemp = temp[2].split(dlim);
			       for (int i = 0; i <genTemp.length; i++)
			    	   movieTemp.genres.add(genTemp[i]);   
			    
			       list.put(movieId,movieTemp);
			   }
			   }
			}catch (IOException e) {
				e.printStackTrace();
			}
		return(list);
	}
	
}


class Movies{
	int movieId;
	String movieTitle;
	ArrayList<String> genres = new ArrayList<String>();
	double averageRate;
	int viewers;
	
	int getviewers() {
		return this.viewers;
	}
	
	double getRate() {
		return this.averageRate;
	}
	
	
	
	 /*Comparator Overwrite for sorting the Movie list by Number of Viewer*/
    public static Comparator<Movies> viewersComparator = new Comparator<Movies>() {

	public int compare(Movies m1, Movies m2) {

	   int viewers1 = m1.getviewers();
	   int viewers2 = m2.getviewers();

	   
	   /*For descending order*/
	   return viewers2-viewers1;
   }};
   
   /*Comparator Overwrite for sorting the Movie list by Average Rate */
   public static Comparator<Movies> ratingComparator = new Comparator<Movies>() {

		public int compare(Movies m1, Movies m2) {

		   double rate1 = m1.getRate();
		   double rate2 = m2.getRate();
		   
		   /*For descending order*/
		   return Double.compare(rate2,rate1);
	   }};
}