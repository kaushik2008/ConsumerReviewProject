import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
  
  static{
    try {
      Scanner input = new Scanner(new File("ConsumerLab_Code\\cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        // System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("ConsumerLab_Code\\positiveAdjectives.txt"));
      while(input.hasNextLine()){
        posAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("ConsumerLab_Code\\negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    // remove any additional space that may have been added at the end of the string
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  /**
   * This code analyzes movie reviews from a file, determines the movie with the highest sentiment score, assigns a grade based on the score, and prints the most liked movie along with its corresponding grade.
   */


public static void analyzeMovieReviews(String fileName) {
  try {
      Scanner input = new Scanner(new File(fileName));
      String mostLikedMovie = "";
      double highestSentiment = 0.0;

      while (input.hasNextLine()) {
          String movieReview = input.nextLine();
          String movieName = extractMovieName(movieReview); 
          double sentimentScore = calculateReviewSentiment(movieReview);

          if (sentimentScore > highestSentiment) {
              highestSentiment = sentimentScore;
              mostLikedMovie = movieName;
          }
      }

    /**
    while (input.hasNextLine()) {
    String movieReview = input.nextLine();
    String movieName = extractMovieName(movieReview); 
    double sentimentScore = calculateReviewSentiment(movieReview);

    switch ((sentimentScore > highestSentiment) ? 1 : 0) {
        case 1:
            highestSentiment = sentimentScore;
            mostLikedMovie = movieName;
            break;
        case 0:
            // no change if the sentiment is not greater
            break;
    */

  
    if (highestSentiment >= 15) {
      gradeValue = "A";      
    }
    if (highestSentiment <= 10) {
      gradeValue = "B";      
    }
    if (highestSentiment <= 8) {
      gradeValue = "C";      
    }
    if (highestSentiment <= 5) {
      gradeValue = "D";      
    }
    if (highestSentiment <= 3) {
      gradeValue = "E";      
    }

      input.close();
     
    
      System.out.println("The most liked movie is: " + mostLikedMovie + " with a sentiment score of " + gradeValue);
  } catch (Exception e) {
      System.out.println("Error reading reviews: " + e.getMessage());
  }
}
  /**
his method extracts and returns the movie name by splitting the review string at the colon
  */
public static String extractMovieName(String review) {
  return review.split(":")[0].trim();
}

 /**
This method calculates and returns the total sentiment score of a review by splitting the review into words, 
  */

public static double calculateReviewSentiment(String review) {
  String[] wordlist = review.split("\\s+");
  double fullSentiment = 2;

  for (String word : wordlist) {
      String cleanedWord = removePunctuation(word).toLowerCase();
      fullSentiment += (double) sentimentVal(cleanedWord); 
  }
  return fullSentiment;
}

  /**
    int i = 0;
while (i < words.length) {
    String cleanedWord = removePunctuation(words[i]).toLowerCase();
    totalSentiment += sentimentVal(cleanedWord);
    i++;
*/

}

}
