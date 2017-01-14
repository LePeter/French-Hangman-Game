package EnglishHangmanGamePack;

/**
 *This file can not be reproduced unless specifically allowed by the owners.
 *Copyright 2014 Mrs.Brauzer's class
 *@author Charlie,Peter
 *@param Letter guesses read in through the console using util.Scanner
 */

import java.util.*; //imports everything
import java.awt.*;
import java.awt.event.*;
import sun.audio.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.FlowLayout;

import javax.swing.JOptionPane;

public class HangmanGame extends JFrame {

        // GUI GLOBAL FIELDS
        private Color color = new Color(234,234,234); // default color black
        private JButton b1, b2, b3, b4, playButton;

        // GAME ITSELF
        private JPanel panel;
        private JLabel labelQuestion, labelAnswer, labelScore;
        private JLabel back;
        public static boolean isPressed = false;

        // GLOBAL FIELDS
        private static Queue<Question> randomQuestions = new LinkedList<Question>();
        private static ArrayList<Question> constructedWord = new ArrayList<Question>(); 
        private int numWrongGuesses; // dealt with in the isInWord method
        private String currentWord;
        private ArrayList<String> usedLetters = new ArrayList<String>();
        private int score;

        // CONSTANTS
        public static final int MAX_NUM_GUESS = 6;

        public HangmanGame() {
                // GUI Set Up
                super("Le Jeu De Hangman");

                // Code starts here::
                createsQuestionSets();
                randomizeQuestionSets();

                setPanelUp();
                createsRetryButton();
                waitForPlayButton();
                score = 0;


                // create the user interaction buttons
                // createsHangmanInputButton();

        }

        // GUI METHODS **********************************************************************
        
        //button to play
        public void waitForPlayButton(){
        	playButton = new JButton("Jouer");
        	playButton.addActionListener(new ActionListener() {
                //wait for user press
                public void actionPerformed(ActionEvent e) {
                	isPressed = true;
                }

        	});
        	add(playButton);
        }
        
        //BUG FIX BOOLEAN
        public boolean isPressed(boolean bool){
        	return bool;
        }

        // creates a button to quit emergency
        public void createsRetryButton() {
                b1 = new JButton("Quiter");
                b1.addActionListener(new ActionListener() {
                        // gets a a name for SVG and converts it
                        public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                        }

                });
                add(b1);
        }

        // returns a letter user enters (guess the letter)
        public String getsUserInput() {
                return JOptionPane.showInputDialog(null, "Entrez une lettre:   ");

        }

        // sets up initial panel (background)
        public void setPanelUp() {

                // set initial panel layout
                labelQuestion = new JLabel();
                labelQuestion.setForeground(Color.BLACK);
                labelQuestion.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                labelQuestion.setText("<html>" + getCurrentQuestion() + " " + "(" +  
                getCurrentAnswerLength() +" " + "lettres)" + "<p>" + ""
                + "</p>" + "</html>");

                labelAnswer = new JLabel();
                labelAnswer.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                labelAnswer.setForeground(Color.BLACK);
                
                labelScore = new JLabel();
                labelScore.setForeground(Color.BLACK);
                labelScore.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                labelScore.setText("<html>"  +"Pointage: "+  score+ "&nbsp;&nbsp;&nbsp;" +"</html>");
                
                back = new JLabel();
                

                panel = new JPanel(new BorderLayout());
                panel.add(labelQuestion, BorderLayout.NORTH);
                panel.add(labelAnswer, BorderLayout.SOUTH);
                panel.add(labelScore, BorderLayout.EAST);
                panel.setPreferredSize(new Dimension(792, 600));
                panel.setBackground(color);
                setLayout(new FlowLayout());
                add(panel, BorderLayout.CENTER);
                
                //add image

                
                
        }

        // prints the question to screen
        public void printQuestionToScreen() {
                labelQuestion.setText("<html>" + getCurrentQuestion() + " " + "(" +  
                getCurrentAnswerLength() +" " + "lettres)" + "<p>" + ""
                + "</p>" + "</html>");
                repaintsScreen();

        }

        // @param String answer, String wrong, String number of wrong
        // prints answer to screen
        public void printsAnswerToScreen(String theAnswer, String theWrong,
                        String numWrong) {
                labelAnswer.setText("<html>"
                                                + theAnswer
                                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                                + theWrong + " &nbsp; " + numWrong + "</html>");
                repaintsScreen();

        }
        
        //draws the hangman depending on number of wrong
        public void drawTheHangman(int numWrong){
        	
        	ImageIcon icon = null;
        	repaintsScreen();
        	switch(numWrong){
        	//add image
    
        		case 0:  
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman0.png"));
        			 break;
        		case 1:  
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman1.png"));
        			 break;
        		case 2:  
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman2.png"));
        			 break;
        		case 3:  
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman3.png"));
        			 break;
        		case 4:  
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman4.png"));
        			 break;
        		case 5:
        			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman5.png"));
       			     break;
        		case 6:
       			 icon = new ImageIcon(getClass().getClassLoader().getResource("Hangman6.png"));
      			     break;
        	    default:
        	    	break;
               
        		 
        	}
        	Image image = icon.getImage();
            Image newImage = image.getScaledInstance(650,500, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImage);
            //JLabel back = new JLabel();
            back.setIcon(icon);
             panel.add(back);
             repaintsScreen();
        }

        // reprints the whole screen
        public void repaintsScreen() {
                // refreshes the panel
                panel.revalidate();
                panel.repaint();
        }

        // END OF GUI METHODS ***********************************************************

        // creates the question and answers then add it into an arraylist to be
        // randomized
        public void createsQuestionSets() {
                // input the questions here
                // Great Gatsby Questions Section
                Question qo1 = new Question("Fete de la ... celebre le conclusion de WW2 ","Victoire");
                Question qo2 = new Question("La fete celebre la ... de militaire de Allemagne ", "Capitulation" );
                Question qo3 = new Question("Les soldats presentent une ... de fleurs aux anciens combattants ", "gerbe");
                Question qo4 = new Question("Le Fete de la Victoire celebre le victoire de les?",   "allies");
                Question qo5 = new Question("Fete de la Victoire est celebrer avec le jambon, le fromage, et c'est bu avec ...,  ", "champagne");

                
                // add into constructedWord arraylist to be randomized
                constructedWord.add(qo1);
                constructedWord.add(qo2);
                constructedWord.add(qo3);
                constructedWord.add(qo4);
                constructedWord.add(qo5);
                   
        }
                
                
        // get the current word from arraylist of constructedWord
        public String getCurrentWord() {
                return this.currentWord;
        }

        // randomize the questions to be put in a queue
        public void randomizeQuestionSets() {
                while (constructedWord.size() > 0) {
                        int random = (int) (Math.random() * constructedWord.size());
                        // condition so no repeats of questions
                        if (constructedWord.get(random) != null) {
                                randomQuestions.add(constructedWord.get(random));
                                constructedWord.remove(random);
                        }
                }
        }

        // checks if the user inputed word is inside of the queued word
        public boolean isInWord(String chosenLetter) {
                char[] givenWord = getCurrentAnswer().toCharArray();
                for (int i = 0; i < givenWord.length; i++) {
                        if (chosenLetter.equalsIgnoreCase(givenWord[i] + ""))
                                return true;
                }
                return false;

        }

        // gets the spots of the user input letter in comparison to the queued word
        public ArrayList<Integer> getWordSpots(String chosenLetter) {
                ArrayList<Integer> allTheSpot = new ArrayList<Integer>();
                char[] givenWord = getCurrentAnswer().toCharArray();
                for (int i = 0; i < givenWord.length; i++) {
                        // if is inside ,then get the spot
                        if (chosenLetter.equalsIgnoreCase(givenWord[i] + "")) {
                                allTheSpot.add(i);
                        }
                }
                return allTheSpot;
        }

        // gets the queue of questions
        public Queue<Question> getQuestionsAndAnswers() {
                return randomQuestions;
        }

        // gets the sets of questions and answer
        public Queue<Question> getQuestionSet() {
                return randomQuestions;
        }

        // pops the queue for new question and answer set
        public void popNewQuestionSet() {
                randomQuestions.remove();
        }

        // gets the question from in front of the queue (current)
        public Question getCurrentQuestionSet() {
                return randomQuestions.peek();
        }

        // gets the current question
        public String getCurrentQuestion() {
                return randomQuestions.peek().getQuestion();
        }

        // gets the current answer
        public String getCurrentAnswer() {
                return randomQuestions.peek().getAnswer();
        }

        // gets the current answer length
        public int getCurrentAnswerLength() {
                return getCurrentAnswer().length();
        }

        // gets the user input letter
        public String getInputCurrentLetter() {

                String letterInputed = JOptionPane.showInputDialog(null,"Devinez une lettre: ");
                if(letterInputed == null)
                	System.exit(0);

                if (letterInputed.length() > 1|| letterInputed.equals(" ") || letterInputed.equals(""))
                        return getInputCurrentLetter();

                return letterInputed;

        }
        
        //increments the score updates GUI
        public void incrementsScore(){
        	score++;
        	labelScore.setText("<html>"  +"Pointage: "+  score+ "&nbsp;&nbsp;&nbsp;" +"</html>");
            repaintsScreen();
        	
        }
        
        //gets the score
        public int getScore(){
        	return score;
        }
        
        
        
        
}