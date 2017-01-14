package EnglishHangmanGamePack;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainHangmanRunner {
	// Global fields
	private static int score = 0;
	private boolean isItPressed = false;
	public static  int placeHolder = 0;
	BufferedImage backgroundImage = null;

	public static void main(String args[]) throws IOException {
		HangmanGame run = new HangmanGame();
		run.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run.setSize(812, 690);
		run.setVisible(true);

		// loop to run the program for 10 questions/////////////////
		for (int i = 0; i < 5; i++) {

			if (i > 0)
				run.printQuestionToScreen(); // prints out subsequent questions
												// as first question is created
												// in constructor

			int numWrongGuesses = 0;
			ArrayList<String> finalWord = new ArrayList<String>(
			run.getCurrentAnswerLength()); // words correct displayed
			ArrayList<String> finalWrongGuesses = new ArrayList<String>(); // words wrong displayed


			// initialize initial finalWord
			for (int z = 0; z < run.getCurrentAnswerLength(); z++)
				finalWord.add(" ");
				run.printsAnswerToScreen(finalWord + "", finalWrongGuesses + "","0"); // initially display
				run.drawTheHangman(numWrongGuesses);
				
			//BUG FIX
			if(i ==0){
				while(HangmanGame.isPressed == false){
					System.out.println("hi");
				}
			
			}

			// do this until one game session ends
			while ((numWrongGuesses < HangmanGame.MAX_NUM_GUESS)&& (finalWordHasSpace(finalWord) == true)) {

				String cl = run.getInputCurrentLetter();

				while (finalWord.contains(cl.toUpperCase())|| finalWrongGuesses.contains(cl)) {
					JOptionPane.showMessageDialog(null,"Le lettre est répétée, tres mal! ");
					cl = run.getInputCurrentLetter();
				}

				// if the user inputed letter is in the word given, then add it
				// to certain spot in arrayList
				if (run.isInWord(cl) == true) {
					ArrayList<Integer> spots = run.getWordSpots(cl);
					// loop through to add the user entered letter at given
					// spots in finalWord
					for (int z = 0; z < spots.size(); z++) {
						finalWord.set(spots.get(z), cl.toUpperCase());
					}
					// prints the answer to screen
					run.printsAnswerToScreen(finalWord + "", finalWrongGuesses+ "", numWrongGuesses + "");

				//if guess incorrectly
				} else {
					numWrongGuesses++;
					finalWrongGuesses.add(cl);
					run.printsAnswerToScreen(finalWord + "", finalWrongGuesses+ "", numWrongGuesses + "");
					run.drawTheHangman(numWrongGuesses);
					run.repaintsScreen();


				}
			}
			//if you win increase score
			if (!finalWordHasSpace(finalWord)){
				run.incrementsScore();
				JOptionPane.showMessageDialog(null, "Tres bien!  Pointage:" + run.getScore());
			}

			//if you lose display answer
			if (numWrongGuesses == HangmanGame.MAX_NUM_GUESS)
				JOptionPane.showMessageDialog(null,"Faux! La réponse est: " + run.getCurrentAnswer());
			run.popNewQuestionSet();

		}
		
		// END OF QUESTION LOOP//////////////////

	}

	// see if final has zero
	public static boolean finalWordHasSpace(ArrayList<String> finalWor) {
		for (int i = 0; i < finalWor.size(); i++) {
			if (finalWor.get(i).equals(" "))
				return true;
		}
		return false;
	}

}