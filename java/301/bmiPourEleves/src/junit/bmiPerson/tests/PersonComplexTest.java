package junit.bmiPerson.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import junit.bmiPerson.Person;

public class PersonComplexTest {
	
	public static final int MAX_ERSON_AGE = 150; 
	public static final float MAX_HEIGHT = (float)200.75;
	public static final float MIN_HEIGHT = 35;
	public static final float MAX_WEIGHT = 720;
	public static final float MIN_WEIGHT = 1;

	Calendar rightNow = Calendar.getInstance();
	
	Person person;
	
//	@Before
//	public void beforeTests(){
//		rightNow.
//	}
	@Test
	public void testMinHeight() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		try{
			Person person = new Person("a","b", rightNow, (float)30.0, (float) 1.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}
	public void testMinWeight() throws Exception {

		try{
			Person person = new Person("a","b", rightNow, (float)50.0, (float) 0.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}
	public void testMin() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		try{
			Person person = new Person("a","b", rightNow, (float)30.0, (float) 0.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}
	public void testMin() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		try{
			Person person = new Person("a","b", rightNow, (float)30.0, (float) 0.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}
	public void testMin() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		try{
			Person person = new Person("a","b", rightNow, (float)30.0, (float) 0.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}
	public void testMin() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		try{
			Person person = new Person("a","b", rightNow, (float)30.0, (float) 0.5);
		}catch( Exception e){
			assertEquals("BadParametersException" , e);
		}

	}

}
