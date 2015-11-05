package junit.bmiPerson.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import junit.bmiPerson.BadParametersException;
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
	@Test(expected= BadParametersException.class)
	public void testMinHeight() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		Person person = new Person("a","b", rightNow, (float)30.0, (float) 1.5);
		
	}
	@Test(expected = BadParametersException.class)
	public void testMinWeight() throws Exception {
		Person person = new Person("a","b", rightNow, (float)50.0, (float) 0.5);
	}
	@Test(expected = BadParametersException.class)
	public void testMaxHeight() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		Person person = new Person("a","b", rightNow, (float)3000.0, (float) 0.5);
	}
	@Test(expected = BadParametersException.class)
	public void testMaxWeight() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		Person person = new Person("a","b", rightNow, (float)30.0, (float) 10000.0);
	}
	@Test(expected = BadParametersException.class)
	public void testTimeAfterNow() throws Exception{
		Calendar creation  = Calendar.getInstance();
		creation.add(Calendar.HOUR,1);
		Person person = new Person("a","b", creation, (float)30.0, (float) 10000.0);
	}
	@Test
	public void allOk() throws Exception{
		Calendar creation  = Calendar.getInstance();
		Person person = new Person("a","b", creation, (float)150.0, (float) 10.0);
	}

}
