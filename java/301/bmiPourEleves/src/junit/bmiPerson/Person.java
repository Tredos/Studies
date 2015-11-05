package junit.bmiPerson;

import java.util.Calendar;


public class Person {
	
	
	public static final int MAX_ERSON_AGE = 150; 
	public static final float MAX_HEIGHT = (float)200.75;
	public static final float MIN_HEIGHT = 35;
	public static final float MAX_WEIGHT = 720;
	public static final float MIN_WEIGHT = 1;
	
	private String firstName;
	private String lastName;
	private Calendar birthDay;
	private float height;
	private float weight;
	
	public Person(String firstName, String lastName, Calendar birthDay, float height, float weight) throws Exception{
		
		this.firstName = firstName;
		this.lastName = lastName;
		
		Calendar rightNow = Calendar.getInstance();
		if (birthDay.after(rightNow) || height >= MAX_HEIGHT || height<=MIN_HEIGHT || weight >= MAX_WEIGHT || weight<=MIN_WEIGHT ){
			throw new BadParametersException("BadParametersException");
		}
		else{
			this.birthDay = birthDay;
			this.height = height;
			this.weight = weight;
		}
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	public String getLastName(){
		return this.lastName;
	}
	public Calendar getBirthDay(){
		return this.birthDay;
	}
	public Person(String firstName,String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public float getBMI() throws Exception{
		
		if(this.height == 0.0 || this.weight == 0.0){
			throw new Exception("MissingParametersException");
			}
		
		float bmi = this.weight/(this.height*this.height);
		return bmi;
	}
	public Corpulence getCorpulence() throws Exception{
		
		float bmi = this.getBMI();
		
		if (bmi< 16.5 ){return Corpulence.LEANNESS;}		
		else if (bmi <18.5){return Corpulence.MALNUTRITION;}
		else if (bmi <25){return Corpulence.MODERATEOBESITY ;}
		else if (bmi <30){return Corpulence.MORBIDOBESITY;}
		else if (bmi <35){return Corpulence.NORMAL;}
		else if (bmi <40){return Corpulence.OVERWEIGHT;}
		else {return Corpulence.SEVEREOBESITY ;}
		
	}
		public String getAdvice() throws Exception{
			Corpulence taille = getCorpulence();
			String advice = taille.getAdvice(); 
			return advice;
		}
		
		
		public static void main(String args[]) throws Exception{
			Person jan = new Person("jan", "kowalski");
			
				float p = jan.getBMI();
				System.out.println(Float.toString(p));

			
			
		}
}
