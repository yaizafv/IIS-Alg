package labs.en._26.algteacher.s5;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FerryTests {
	private int length;
	private List<Integer> vehicles;
	
	@Test
	public void test00() {
		System.out.printf("\n\nCASE 00\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test00.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(5, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test01() {
		System.out.printf("\n\nCASE 01\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test01.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(2, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test02() {
		System.out.printf("\n\nCASE 02\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test02.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test03() {
		System.out.printf("\n\nCASE 03\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test03.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test04() {
		System.out.printf("\n\nCASE 04\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test04.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(4, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test05() {
		System.out.printf("\n\nCASE 05\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test05.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(3, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test06() {
		System.out.printf("\n\nCASE 06\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test06.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(10, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}
	
	@Test
	public void test07() {
		System.out.printf("\n\nCASE 07\n");
		loadData("src/main/java/labs/en/_26/algteacher/s5/test07.txt");		
		Ferry problem = new Ferry(length, vehicles);
		problem.printData();
		problem.run();
		problem.printSolutionTable();
		assertEquals(11, problem.getMaximumNumberOfVehicles());
		problem.printPossibleAssignation();
	}

	private void loadData(String file) {
		this.vehicles = new ArrayList<Integer>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			this.length = Integer.valueOf(reader.readLine());		
			for (String s : reader.readLine().split(" ")) {
				this.vehicles.add(Integer.valueOf(s));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
