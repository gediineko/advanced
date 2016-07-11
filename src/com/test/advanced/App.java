package com.test.advanced;
import com.test.advanced.util.RandomUtil;
import com.test.advanced.util.FileUtil;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
public class App {
	private Scanner scanner;
	private List<Map<String,String>> table;
	public App () {
		table = FileUtil.readFile();
		scanner = new Scanner(System.in);

	}
	public static void main (String[] args){
		new App().run();
	}
	public void run (){
		if (table == null){
			initTable();
			FileUtil.writeFile(table);
		}
		printTable();
		menu();
	}
	public void menu(){
		int opt;
		boolean cont;
		do {
			opt = 0;
			cont = true;
			try {
			  System.out.println("Menu: [1 Search] [2 Edit] [3 Print] [4 Add Row] [5 Sort] [6 Reset] [7 Exit]");
			  opt = scanner.nextInt();
			} catch (InputMismatchException ex){
				cont = true;
			}
			scanner.nextLine();
			switch(opt){
				case 1: 
					searchTable();
					break;
				case 2:
				    editTable();
					break;
				case 3:
					printTable();
					break;
				case 4: 
					addRow();
					break;
				case 5: 
					sort();
					break;
				case 6:
					initTable();
					break;
				case 7: 
					cont = false;
					break;
				default:
					System.out.println("[Invalid option]");
					break;
			}
		} while (cont);
		System.out.println("Exiting...");
	}
	public void printTable(){
		for(Map<String, String> row : table){
			for(Map.Entry<String, String> entry : row.entrySet()){
				System.out.print("[" + entry.getKey() + ":" + entry.getValue() + "]\t");
			}
			System.out.println("");
		}
	}
	public void initTable(){
		int fDim = 0;
		int sDim = 0;
		boolean isValid;
		System.out.println("[Set new table dimensions]");
		do {
			isValid = true;
			try {
				System.out.println("Row: ");
				fDim = scanner.nextInt();
				if (fDim < 1){
					isValid = false;
					System.out.println("[Row count should be atleast 1]");
				}
			} catch (InputMismatchException ex){
				isValid = false;
				System.out.println("[Invalid row value]");
				scanner.nextLine();
			}
		} while(!isValid);
		do {
			isValid = true;
			try {
				System.out.println("Column: ");
				sDim = scanner.nextInt();
				if (sDim < 1){
					isValid = false;
					System.out.println("[Column count should be atleast 1]");
				}
			} catch (InputMismatchException ex){
				isValid = false;
				System.out.println("[Invalid column value]");
				scanner.nextLine();
			}
		} while (!isValid);
		table = new LinkedList<>();
		for (int x = 0; x < fDim; x++){
			Map<String,String> row = new LinkedHashMap<>();
			for (int y = 0; y < sDim; y++){
				row.put(RandomUtil.generate(3), RandomUtil.generate(3));
			}
			table.add(row);
		}
	}
	public void searchTable(){
		System.out.print("Search for: ");
		String character = scanner.nextLine();
		List<Map<String,String>> result = new ArrayList<>();
		int x = 0;
		int y = 0;
		for(Map<String, String> row : table){
			y = 0;
			for(Map.Entry<String, String> entry : row.entrySet()){
				if (entry.getKey().contains(character)) {
					Map<String,String> keyFound = new HashMap<>();
					keyFound.put("x", x + "");
					keyFound.put("y", y + "");
					keyFound.put("entryType", "KEY");
					result.add(keyFound);
				}
				 if (entry.getValue().contains(character)) {
				 	Map<String,String> valueFound = new HashMap<>();
					valueFound.put("x", x + "");
					valueFound.put("y", y + "");
					valueFound.put("entryType", "VALUE");
					result.add(valueFound);
				}
				y++;
			}
			x++;
		}
		if(result.size() > 0){
			int keyCount = 0;
			int valueCount = 0;
			System.out.print("Result(s): ");
			for (Map<String,String> entry : result){
				System.out.print("\n"  + "[" + entry.get("x") + "," + entry.get("y") + "] : " + entry.get("entryType"));
				if (entry.get("entryType").equals("KEY")){
					keyCount++;
				} else {
					valueCount++;
				}
			}
			System.out.println("\nOccurences as KEY: " + keyCount);
			System.out.println("Occurences as VALUE: " + valueCount);
			System.out.println("Total occurences: " + (keyCount+valueCount));
		} else {
			System.out.println("[No data found]");
		}
	}
	public void editTable(){
		int fInd = 0;
		int sInd = 0;
		boolean isValid = false;
		System.out.println("[Enter index of set to edit]");
		do {
			try {
				isValid = true;
				System.out.print("Row: ");
				fInd = scanner.nextInt();
				//if (fInd > table length)
			} catch (InputMismatchException ex) {
				System.out.println("[Invalid row value]");
				scanner.nextLine();
				isValid = false;
			}
		} while (!isValid);
		do {
			try {
				isValid = true;
				System.out.print("Column: ");
				sInd = scanner.nextInt();
			} catch (InputMismatchException ex){
				System.out.println("[Invalid column value]");
				scanner.nextLine();
				isValid = false;
			}
		} while (!isValid);
		// System.out.println("\n" + table.get(fInd));
		Map<String,String> editedMap = new LinkedHashMap<>();
		boolean repeat = true;
		String identifier = "";
		do {
			System.out.println("What would you like to change? [1 KEY] [2 VALUE]");
			int option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
				case 1: 
					identifier = "KEY";
					repeat = false;
					break;
				case 2:
					identifier	= "VALUE";
					repeat = false;
					break;
				default: 
					System.out.println("You need to choose if you want to edit a KEY or a VALUE!");
					break;
			}
		} while (repeat);
		//GET NEW VALUE
		System.out.println("Enter new info: ");
		String newValue = scanner.nextLine();
		int x = 0;
		for (Map.Entry<String,String> entry : table.get(fInd).entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			if (x == sInd){
				if(identifier.equals("KEY")){
					key = newValue;
				} else {
					value = newValue;
				}
			}
			editedMap.put(key, value);
			x++;
		}
		table.set(fInd, editedMap);
		FileUtil.writeFile(table);
	}
	public void addRow(){
		Map<String,String> newRow = new LinkedHashMap<>();
		System.out.println("Adding new row...\nInput " + table.get(0).size() + " key and value pairs separated by commas.\nex: <key>,<value>");
		for (int y = 0; y < table.get(0).size(); y++){
			String entry = scanner.nextLine();
			String[] keyValue = entry.split(",");
			newRow.put(keyValue[0],keyValue[1]);
		}
		table.add(newRow);
		FileUtil.writeFile(table);
	}
	public void sort(){
		List<Map<String,String>> sortedTable = new LinkedList<>();
		for (Map<String,String> row : table){
			List<Map.Entry<String,String>> list = new LinkedList<Map.Entry<String,String>>(row.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String,String>>(){
				public int compare(Map.Entry<String,String> ent1, Map.Entry<String,String> ent2){
					return (ent1.getKey() + ent1.getValue()).compareTo(ent2.getKey() + ent2.getValue());
				}
			}); 
			Map<String,String> sortedMap = new LinkedHashMap<>();
			for (Map.Entry<String,String> entry : list){
				sortedMap.put(entry.getKey(),entry.getValue());
			}
			sortedTable.add(sortedMap);
		}

		Collections.sort(sortedTable, new Comparator<Map<String,String>>(){
			public int compare(Map<String,String> map1, Map<String,String> map2){
					String o1 = "";
					String o2 = "";
					for (Map.Entry<String,String> entry : map1.entrySet()){
						o1 += entry.getKey() + entry.getValue();
					}
					for (Map.Entry<String,String> entry : map2.entrySet()){
						o2 += entry.getKey() + entry.getValue();
					}
					System.out.println(o1 + " " + o2);
					return o1.compareTo(o2);
			}
		});

		table = sortedTable;
		FileUtil.writeFile(table);
	}
}

















