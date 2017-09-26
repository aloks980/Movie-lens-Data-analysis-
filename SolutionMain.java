package solution.movielens;
import java.io.*;
import java.util.*;
public class SolutionMain{
	public static void main(String []arg) {
		Map<Integer, Movies> moviesList = ReadMovies.readMovieList();
		Map<Integer,Map<Integer,Rating>> ratingList = ReadRatings.readRatingData();
		Map<Integer, Users> usersList = ReadUsers.readUserData();
		
		
		
		calculate1_2 ( moviesList, ratingList);
		Map<Integer, Map<String, Map<String, AgeGroupGener>>> occupationTable = calculate3(moviesList, ratingList, usersList);
		
		DisplayResult1_2(moviesList);
		DispalyResult3(occupationTable);
		
		}
	


	public static void calculate1_2 (Map<Integer, Movies> moviesList, Map<Integer,Map<Integer,Rating>> ratingList) {
		double ratingSum = 0;
		
		//for (movies m : moviesList) {
		for(Map.Entry<Integer,Map<Integer,Rating>> usertemp : ratingList.entrySet()) {
			for(Map.Entry<Integer, Rating> movieTemp : usertemp.getValue().entrySet()) {
			int movieId =  movieTemp.getKey();
				Rating r = movieTemp.getValue();
				if(moviesList.containsKey(movieId)) {
				Movies m = moviesList.get(movieId);
				//System.out.println(r.movieId);
				ratingSum = m.averageRate * m.viewers;
				m.viewers++;
				ratingSum += r.rating;
				m.averageRate =   (ratingSum/m.viewers);
				ratingSum = 0;
				 }else {
					 Movies newMovieTemp = new Movies();
				       
				       newMovieTemp.movieId = movieId;
				       newMovieTemp.movieTitle = "UnKnown";
				       newMovieTemp.averageRate = r.rating;
				       newMovieTemp.viewers++;
				       moviesList.put(movieId,newMovieTemp);
				 }
		}}
	}
	
	public static Map<Integer, Map<String, Map<String, AgeGroupGener>>> calculate3 (Map<Integer, Movies> moviesList, Map<Integer,Map<Integer,Rating>> ratingList, Map<Integer, Users> usersList) {
		
		
		Map<Integer, Map<String, Map<String, AgeGroupGener>>> occupation =new TreeMap<Integer, Map<String, Map<String, AgeGroupGener>>>();
		for(Map.Entry<Integer,Users> usertemp : usersList.entrySet()) {
			int userId = usertemp.getKey(); 
			String ageGroupId = findAgeGroupId(usersList.get(userId));
			if(ageGroupId == "0")
				continue;
			int occupationTemp = usersList.get(userId).occupation;
			
			//if(usersList.containsKey(userId)) {
				
			if(occupation.containsKey(occupationTemp)) {
				if(occupation.get(occupationTemp).containsKey(ageGroupId)) {
					for(Map.Entry<Integer, Rating> movieTemp : ratingList.get(userId).entrySet()) {
						int movieIdtemp = movieTemp.getKey();
						for(String gener : moviesList.get(movieIdtemp).genres) {
							if(occupation.get(occupationTemp).get(ageGroupId).containsKey(gener)) {
								AgeGroupGener oldGener =occupation.get(occupationTemp).get(ageGroupId).get(gener);
								double sumGenerRating =  oldGener.averageRating * oldGener.numberOfUsers;
								sumGenerRating += movieTemp.getValue().rating;
								oldGener.numberOfUsers++;
								oldGener.averageRating = sumGenerRating/oldGener.numberOfUsers;
							}else {
								
								AgeGroupGener newGener = new AgeGroupGener();
								newGener.gener = gener;
								newGener.averageRating = movieTemp.getValue().rating;
								newGener.numberOfUsers++;
								occupation.get(occupationTemp).get(ageGroupId).put(gener, newGener);
								
							}
						}		
				}
			}else {
				 Map<String, AgeGroupGener> ageGroupTemp = new TreeMap<String, AgeGroupGener>();
				 occupation.get(occupationTemp).put(ageGroupId, ageGroupTemp);

					for(Map.Entry<Integer, Rating> movieTemp : ratingList.get(userId).entrySet()) {
						int movieIdtemp = movieTemp.getKey();
						for(String gener : moviesList.get(movieIdtemp).genres) {
							if(occupation.get(occupationTemp).get(ageGroupId).containsKey(gener)) {
								AgeGroupGener oldGener =occupation.get(occupationTemp).get(ageGroupId).get(gener);
								double sumGenerRating =  oldGener.averageRating * oldGener.numberOfUsers;
								sumGenerRating += movieTemp.getValue().rating;
								oldGener.numberOfUsers++;
								oldGener.averageRating = sumGenerRating/oldGener.numberOfUsers;
							}else {
								
								AgeGroupGener newGener = new AgeGroupGener();
								newGener.gener = gener;
								newGener.averageRating = movieTemp.getValue().rating;
								occupation.get(occupationTemp).get(ageGroupId).put(gener, newGener);
								
							}
						}		
				}	
			}
		}else {
			
			 Map<String, Map<String, AgeGroupGener>> newOccupation = new TreeMap<String, Map<String, AgeGroupGener>>();
			 Map<String, AgeGroupGener> ageGroupTemp = new TreeMap<String, AgeGroupGener>();
			 newOccupation.put(ageGroupId, ageGroupTemp);
			 occupation.put(occupationTemp, newOccupation);
			 for(Map.Entry<Integer, Rating> movieTemp : ratingList.get(userId).entrySet()) {
					int movieIdtemp = movieTemp.getKey();
					for(String gener : moviesList.get(movieIdtemp).genres) {
						if(occupation.get(occupationTemp).get(ageGroupId).containsKey(gener)) {
							AgeGroupGener oldGener =occupation.get(occupationTemp).get(ageGroupId).get(gener);
							double sumGenerRating =  oldGener.averageRating * oldGener.numberOfUsers;
							sumGenerRating += movieTemp.getValue().rating;
							oldGener.numberOfUsers++;
							oldGener.averageRating = sumGenerRating/oldGener.numberOfUsers;
						}else {
							
							AgeGroupGener newGener = new AgeGroupGener();
							newGener.gener = gener;
							newGener.averageRating = movieTemp.getValue().rating;
							occupation.get(occupationTemp).get(ageGroupId).put(gener, newGener);
							
						}
					}		
			}	
	}
	}
		return occupation;
	}
	
	public static String findAgeGroupId(Users users) {
		int age = users.age;
		if(age<18)
			return "0";
		else if(18<=age && age<=35)
			return "18-35";
		else if(36<=age && age<=50)
			return "36-50";
		else return "50+";	
		}

	// for Saving the solution of Question Number 1 and 2
	public static void  DisplayResult1_2 (Map<Integer,Movies> moviesList) {
		ArrayList<Movies> moviesTemp = new ArrayList<Movies>(moviesList.values());
		Collections.sort(moviesTemp, Movies.viewersComparator);
try {
			
			File file = new File("C:\\\\Users\\\\abc\\\\Desktop\\Solution_qustion_No1.txt"); //Your file
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			}catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("S.No.\tId\tviewers \t Movie Title");
		for (int i =0;i<10;i++) {
			System.out.println((i+1)+"\t"+moviesTemp.get(i).movieId+"\t"+moviesTemp.get(i).viewers+"\t"+moviesTemp.get(i).movieTitle);
		}
		
try {
			
			File file = new File("C:\\\\Users\\\\abc\\\\Desktop\\Solution_qustion_No2.txt"); //Your file
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			}catch (IOException e) {
				e.printStackTrace();
			}

		Collections.sort(moviesTemp, Movies.ratingComparator);
		int j = 0;
		System.out.println("\nS.No.\tId\tviewers\tRating \t Movie Title");
		for (int i =0;i<20;){
			
			if(moviesTemp.get(j).viewers>40){
			System.out.println((i+1)+"\t"+moviesTemp.get(j).movieId+"\t"+moviesTemp.get(j).viewers+"\t"+String.format("%.1f",moviesTemp.get(j).averageRate)+"\t\t"+moviesTemp.get(j).movieTitle);
			i++;
			j++;
		}
			j++;
			}
		
	}	   	

//For saving the Solution for Question Number 3 
private static void DispalyResult3(Map<Integer, Map<String, Map<String, AgeGroupGener>>> occupation) {
	try {
		File file = new File("C:\\\\Users\\\\abc\\\\Desktop\\Solution_qustion_No3.txt"); //Your file
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		}catch (IOException e) {
			e.printStackTrace();
		}
	System.out.println("\n\nOccpation\tAge Group\t Gener Ranked by Avrage rating(Descending Order\n) ");
	System.out.println("\t\tRAnk1\t\tRank2\t\tRank3\t\tRank4\t\tRank5\t\tRank6\t\tRank7\t\tRank8\t\tRank9\t\tRank10");
	for(Map.Entry<Integer, Map<String, Map<String, AgeGroupGener>>> occupationTemp : occupation.entrySet()) {
		for(Map.Entry<String, Map<String, AgeGroupGener>> ageGroupTemp : occupationTemp.getValue().entrySet()) {
			System.out.print(occupationTemp.getKey()+"\t");
			System.out.print(ageGroupTemp.getKey()+"\t");
			int i=0;
			ArrayList<AgeGroupGener> GenerTemp = new ArrayList<AgeGroupGener>(ageGroupTemp.getValue().values());
			Collections.sort(GenerTemp, AgeGroupGener.ratingComparator);
			for(AgeGroupGener Gener : GenerTemp ) {
				System.out.print(String.format("%10s ",Gener.gener)+"\t");
				if(i==9) {
					break;
				}
				i++;
			}
			System.out.println("");	
		}
		
	}
	
}
}
class AgeGroupGener{
	String gener;
	double averageRating;
	int numberOfUsers;
	double getRate() {
		return averageRating;
	}
	
	 /*Comparator Overwrite for sorting the User's Gener list by Average Rate*/
	public static Comparator<AgeGroupGener> ratingComparator = new Comparator<AgeGroupGener>() {

		public int compare(AgeGroupGener R1, AgeGroupGener R2) {

		   double rate1 = R1.getRate();
		   double rate2 = R2.getRate();

		   /*For descending order*/
		   return Double.compare(rate2,rate1);
	   }};
}
