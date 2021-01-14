package com.djm.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.djm.exception.InputOutOfRangeException;


public class ConsoleIO {

	Scanner sc = new Scanner(System.in);
	
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
				System.out.println("Please insert an integer value");
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
				System.out.println("Please insert an number value");
			}
			
		}
	}

	public int readInt(String prompt, int min, int max) {
		int userInput;
		while (true) {
			try {
				System.out.print(prompt);
				userInput = sc.nextInt();
				if(userInput >= min &&  userInput <= max) {
					sc.nextLine();
					return userInput;
				}else {
					throw new InputOutOfRangeException(min, max);
				}
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please insert an integer value");
			}catch(InputOutOfRangeException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}

	

	public String readString(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return sc.nextLine();
				
			} catch (InputMismatchException e) {
				System.out.println("Please insert a proper value");
			}
			
		}
	}

}
