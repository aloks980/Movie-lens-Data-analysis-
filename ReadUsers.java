package solution.movielens;
import java.io.*;
import java.util.*;
public class ReadUsers {
	
	public static Map<Integer,Users> readUserData() {
		Map<Integer,Users> list = new TreeMap<Integer,Users>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\abc\\eclipse-workspace\\movieslens.m1\\src\\solution\\movielens\\users.dat"))) {
			String [] temp;
			String line = br.readLine();
			String delims = "::";
			
			while ((line = br.readLine()) != null) {
				   //System.out.println(line);
				   temp = line.split(delims);
			       Users userTemp = new Users();
			       int uID = Integer.parseInt(temp[0]); 
			       userTemp.userId = uID;
			       userTemp.gender = temp[1];
			       userTemp.age = Integer.parseInt(temp[2]);
			       userTemp.occupation = Integer.parseInt(temp[3]);
			       userTemp.zipCode = temp[4];
			       //userTemp.getuser();
			       list.put(uID,userTemp);
			   }
			  
			}catch (IOException e) {
				e.printStackTrace();
			}
		return list;
	}
	
}
class Users {
	int userId;
	String gender;
	int age;
	int occupation;
	String zipCode;
	void getuser() {
		System.out.println(userId+" "+gender+" "+age+" "+occupation+" "+zipCode+"");
	}
	
}