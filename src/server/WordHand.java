package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class WordHand {
	private String randomWord;
	private Iterator<String> it;
    private Set<String> dicWordList;
    private ArrayList<String> chosenWordsList;
   	private ArrayList<Integer> numOfPossibleAnswers;
   	
   	private static final int MAX_WORD_LENGTH = 7;
   	private static final int MIN_WORD_LENGTH = 2;
   	
   	public WordHand(){
   		chosenWordsList = new ArrayList<String>();
   		numOfPossibleAnswers = new ArrayList<Integer>();
   	}
   	
   	public ArrayList<String> chosenWordsList(){
   		dicWordList = FileUtil.loadFile("words.txt");
   		it = dicWordList.iterator();
   		while(it.hasNext())
        {
            String query = it.next();
            if(query.length() == MAX_WORD_LENGTH)
            {
                chosenWordsList.add(query);
            }
        }
   		return chosenWordsList;
   	}
   	
   	public String chooseWord()
    {
        randomWord = chosenWordsList.get((int)(Math.random() * chosenWordsList.size()));
        System.out.println("The chosen Random Word is : " + randomWord.toUpperCase());
        return randomWord;
    }
   	
   	public void printPotentialAns(){
   		for(int i = 0; i < numOfPossibleAnswers.size(); i++)
        {
			System.out.println(numOfPossibleAnswers.get(i));
		}
   	}
   	
   	public String getRandomWord(){
   		return randomWord;
   	}
   	
    public int potentialAnswers()
    {
		int total = 0;
		it = dicWordList.iterator();
		while(it.hasNext())
		{
			String test = it.next();
			if(test.length() > 2)
			{
				if(randomWord.contains(test.substring(0, 1)))
				{
					if(checkWord(test))
					{
						numOfPossibleAnswers.add(test.length());
						total++;
					}
				}
			}
		}
		return total;
	}
    
    public boolean checkWord(String guess)
    {
        String tempAnswer = randomWord;
        if(guess.length() <= MAX_WORD_LENGTH && guess.length() >= MIN_WORD_LENGTH && dicWordList.contains(guess))
        {
            int num = 0;
            for (int i = 0; i < guess.length(); i++)
            {
                String letter = guess.substring(i, i+1);
                if (tempAnswer.indexOf(letter) != -1) {
                    num++;
                    tempAnswer = tempAnswer.substring(0, tempAnswer.indexOf(letter)) + tempAnswer.substring(tempAnswer.indexOf(letter)+1);
                }
                else return false;
            }
            
            return (num == guess.length());
        }
        
        return false;
    }
}
