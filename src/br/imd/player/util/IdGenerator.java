package br.imd.player.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IdGenerator{
	private static Integer nextId = 1;
	private static final String ID_FILE_PATH = "id.txt";
	
	public static int getNextId() {
		int id = nextId++;
		saveId();
		return id;
	}
	
	private static void saveId() {
		try {
			FileWriter fileWriter = new FileWriter(ID_FILE_PATH);
			BufferedWriter bufferedWriter =  new BufferedWriter(fileWriter);
			
			bufferedWriter.write(String.valueOf(nextId));
			bufferedWriter.close();
			fileWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
        try {
            FileReader fileReader = new FileReader(ID_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            if (line != null) {
                nextId = Integer.parseInt(line);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
