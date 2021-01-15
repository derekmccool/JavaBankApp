package com.djm.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.djm.exception.InputOutOfRangeException;


public class ConsoleIO {

	Scanner sc = new Scanner(System.in);
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	
	public void print(String message) {
		System.out.println(message);
	}

	
	public int readInt(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return sc.nextInt();
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(ANSI_RED + "Please insert an integer value".toUpperCase() + ANSI_RESET);
			}
			
		}
	}

	public double readDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return sc.nextDouble();
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(ANSI_RED + "Please insert an number value".toUpperCase() + ANSI_RESET);
			}
			
		}
	}

	public int readInt(int min, int max) {
		int userInput;
		while (true) {
			try {
				System.out.print("CHOOSE AN OPTION: ");
				userInput = sc.nextInt();
				if(userInput >= min &&  userInput <= max) {
					sc.nextLine();
					return userInput;
				}else {
					throw new InputOutOfRangeException(min, max);
				}
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(ANSI_RED + "Please insert an integer value".toUpperCase() + ANSI_RESET);
			}catch(InputOutOfRangeException e) {
				System.out.println(ANSI_RED + e.getMessage().toUpperCase() + ANSI_RESET);
			}
			
		}
	}

	

	public String readString(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return sc.nextLine();
				
			} catch (InputMismatchException e) {
				System.out.println(ANSI_RED + "Please insert a proper value".toUpperCase() + ANSI_RESET);
			}
			
		}
	}

	public char readChar(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return sc.next().charAt(0);
				
			} catch (InputMismatchException e) {
				System.out.println(ANSI_RED + "Please insert a proper value".toUpperCase() + ANSI_RESET);
			}
			
		}
	}


}
