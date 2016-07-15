package com.test.advanced.util;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.lang.Exception;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.lang.StringBuffer;
public class FileUtil{
	private static final String DIR_NAME = "resources";
	private static final String FILE_NAME = "table";
	private static final String ENTRY_DELIMITER = " \u037E "; //greek question mark
	private static final String KEY_VALUE_DELIMITER = " \u2261 "; //triple bar
	public static List<Map<String,String>> readFile(){
		List<Map<String,String>> table = new LinkedList<>();
		Path file = FileSystems.getDefault().getPath(DIR_NAME, FILE_NAME);
		try (InputStream in = Files.newInputStream(file); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
			String line = null;
			while((line = reader.readLine()) != null){
				Map<String,String> row = new LinkedHashMap<>();
				for (String entry : line.split(ENTRY_DELIMITER)){
					String[] keyValue = entry.split(KEY_VALUE_DELIMITER);
					row.put(keyValue[0],keyValue[1]);
				}
				table.add(row);
			}
		} catch (Exception ex) {
			System.out.println("[File does not exist]");
			return null;
		}
		return table;
	}
	public static void writeFile(List<Map<String,String>> table){
		try {
			File file = new File(DIR_NAME+File.separator+FILE_NAME);
			if (file.exists()){
				file.delete();
			}
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			for(Map<String, String> row : table){
				StringBuffer newRow = new StringBuffer();
				for(Map.Entry<String, String> entry : row.entrySet()){
					newRow.append(entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue() + ENTRY_DELIMITER);
				}
				bw.write(newRow.toString());
				bw.newLine();
			}
			bw.close();
		} catch (Exception ex) {
			System.out.println("Error writing file!");
		}
	}

}