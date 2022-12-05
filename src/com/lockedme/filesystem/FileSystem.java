package com.lockedme.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileSystem {
	
	static final String ROOT_DIR_PATH = "files";
	
	static ArrayList<String> files = new ArrayList<>();
	
	public static void main(String[] args) {
		displaySoftwareDetails();
		readInputMenuAction();			
	}
	
	// MENU
	static void displayMenu() {
		System.out.println("Please choose an option below:");
		System.out.println("[1] View All Files.");
		System.out.println("[2] Manage Files");
		System.out.println("[3] Exit Program");
	}
	
	static void displayBackMenu() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter [0] to go back to main menu");
		scan.next();
		readInputMenuAction();
	}
	
	static void readInputMenuAction() {
		displayMenu();
		
		try {
			int selectedMenuAction = new Scanner(System.in).nextInt();
			
			switch(selectedMenuAction) {
				case 1:
					displayFiles();
					break;
				case 2:
					manageFiles();
					break;
				case 3:
					exitProgram();
					break;
				default:
					System.out.println("Please enter a valid option");
					readInputMenuAction();
					break;
			}
		}
		catch(Exception ex) {
			System.out.println("Please enter a valid menu option:");
			readInputMenuAction();
		}
	}
	// MENU
	
	// 1. DISPLAY FILES
	static void displayFiles() {
		loadFilesFromRootDir();
		
		if(files.size() > 0) {
			System.out.println("Below is the list of files");
			
			files.sort(new Comparator<String>() {
				@Override
				public int compare(String file1, String file2) {
					// TODO Auto-generated method stub
					return file1.compareTo(file2);
				}
			});
			
			files.forEach(System.out::println);
			System.out.println();
			displayBackMenu();
		}
		else {
			System.out.println("No files exist at the moment!");
			displayBackMenu();
		}
	}
	
	// 2. MANAGE FILES
	static void manageFiles() {
		
		System.out.println("Please choose an option below:");
		System.out.println("[a] Add New File");
		System.out.println("[b] Delete A File");
		System.out.println("[c] Search For A File");
		System.out.println("[0] Go Back To Main Menu");
		
		Scanner scan = new Scanner(System.in);

		String sortChoice = scan.next();
		
		switch(sortChoice) {
			case "a":
				addFile();
				break;
			case "b":
				deleteFile();
				break;
			case "c":
				searchFile();
				break;
			case "0":
				readInputMenuAction();
				break;
			default:
				System.out.println("Please enter a valid choice:");
				manageFiles();
				break;
		}
	}
	
	// 2a. ADD NEW FILE
	static void addFile() {
		try{
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Enter file name");
			String name = scan.nextLine();
			
			if(name.isEmpty())
				throw new InvalidFileNameException("Please enter a valid file name!");
			
			File rootDirPath = new File(ROOT_DIR_PATH);
			File newFile = new File(rootDirPath, name);
			if(!newFile.createNewFile())
				throw new FileAlreadyExistException("File " + name + " already exist! Please choose a different file name");
							
			System.out.println("File is added successfully!");	
			displayBackMenu();
		}
		catch(InvalidFileNameException ex) {
			System.out.println(ex.getMessage());
			addFile();
		}
		catch(FileAlreadyExistException ex) {
			System.out.println(ex.getMessage());
			addFile();
		} catch (IOException ex) {
			System.out.println("File not created... " + ex.getMessage());
			addFile();
		}		
	}
	
	// 2b. DELETE FILE
	static void deleteFile() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter file name you want to delete");
		String name = scan.next();
		
		if(findFileInRootDir(name)) {
			File rootDirPath = new File(ROOT_DIR_PATH);
			File file = new File(rootDirPath, name);
			file.delete();
			
			System.out.println("File is deleted successfully!");
		}
		else
			System.out.println("Specified file doesn't exist!");
		
		displayBackMenu();
	}
	
	// 2c. SEARCH FILE
	static void searchFile() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter file name you are looking for");
		String name = scan.next();
		
		if(findFileInRootDir(name))
			System.out.println("File exists!");
		else 
			System.out.println("Specified file doesn't exsist!");
		
		displayBackMenu();
	}
	
	// 3. EXIT PROGRAM
	static void exitProgram() {
		System.out.println("Exsiting the program...");
	}
	
	
	// UTILITIES
	static void displaySoftwareDetails() {
		System.out.println("======================================");
		System.out.println("FILE MANAGER V1.0 - lockedme.com");
		System.out.println("Lockers Pvt. Ltd.");
		System.out.println("Developer: Atif Javed Minhas");
		System.out.println("======================================");
		System.out.println("");
	}
	
	static boolean findFileInRootDir(String name) {
		File rootDirPath = new File(ROOT_DIR_PATH);
		File file = new File(rootDirPath, name);
		
		return file.exists();
	}
	
	static void loadFilesFromRootDir() {
		File rootDirPath = new File(ROOT_DIR_PATH);
		String filesInRootDir[] = rootDirPath.list();
		
		files.clear();
		
		for(String fileName: filesInRootDir) {
			files.add(fileName);
		}
	}
	// UTILITIES


}

record MyFile(String name) {}

class FileAlreadyExistException extends Exception {
	
	public FileAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
	
}

class InvalidFileNameException extends Exception {
	
	public InvalidFileNameException(String errorMessage) {
		super(errorMessage);
	}
	
}
