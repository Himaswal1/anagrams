package com.google.engedu.anagrams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
public class AnagramDictionary {
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordlength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private int size, size1;
    private String s, s1, s2, s3, s4, s5;
    HashSet<String> wordset = new HashSet<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    ArrayList<String> wordlist = new ArrayList<String>();
    ArrayList<String> result = new ArrayList<String>();
    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordlist.add(word);
            String sortedword = sortLetters(word);
            ArrayList<String> sortedList = new ArrayList<>();
            if (!lettersToWord.containsKey(sortedword)) {
                sortedList.add(word);
                lettersToWord.put(sortedword, sortedList);
            } else {
                sortedList = lettersToWord.get(sortedword);
                sortedList.add(word);
                lettersToWord.put(sortedword, sortedList);
            }
        }
        size = wordlist.size();
    }
    public boolean isGoodWord(String word, String base) {
        if (wordset.contains(word) && !(base.contains(word)))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> resultlist = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            String anagram = word + ch;
            String sortedAnagram = sortLetters(anagram);
            if (lettersToWord.containsKey(sortedAnagram)) {
                temp = lettersToWord.get(sortedAnagram);
            }
            for (int i = 0; i < temp.size(); i++) {
                if (!(temp.get(i).contains(word))) {
                    resultlist.add(temp.get(i));
                }
            }
        }
        return resultlist;
    }
    public String sortLetters(String s) {
        String sorted;
        sorted = "";
        int i, j;
        char temp;
        char ch[] = new char[s.length()];
        for (i = 0; i < s.length(); i++)
            ch[i] = s.charAt(i);
        for (i = 0; i < s.length() - 1; i++) {
            for (j = 0; j < (s.length() - i - 1); j++) {
                if (ch[j] > ch[j + 1]) {
                    temp = ch[j];
                    ch[j] = ch[j + 1];
                    ch[j + 1] = temp;
                }
            }
        }
        for (i = 0; i < s.length(); i++)
            sorted = sorted + ch[i];
        return (sorted);
    }
    public String pickGoodStarterWord() {
        int randomnumber;
        String starterword;
        do {
            randomnumber = random.nextInt(wordlist.size());
            starterword = wordlist.get(randomnumber);
        } while (getAnagramsWithOneMoreLetter(starterword).size() <= MIN_NUM_ANAGRAMS);
        return starterword;
    }


}
