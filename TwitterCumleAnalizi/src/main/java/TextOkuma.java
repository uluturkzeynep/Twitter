/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.tokenization.Token;
import zemberek.tokenization.TurkishTokenizer;

/**
 *
 * @author zeyne
 */

public class TextOkuma {

    public TextOkuma() {
        
    }
    List<String> cumleList = new ArrayList<String>();
    List<String> stopWord = new ArrayList<String>();
 public void textIslem() throws FileNotFoundException, IOException{
    
     BufferedReader reader;
     File dir = new File("src/main/java/raw_texts");
     File[] folder = dir.listFiles();
        for(File table : folder) {
            //System.out.println(table);
            File[] filenames = table.listFiles();
                for (File file : filenames) {
                    //System.out.println(file);
                    reader = new BufferedReader(new FileReader(file));
                    Scanner scanner = new Scanner(file); 
                    StringBuffer buffer=new StringBuffer();
			while (scanner.hasNextLine()) {
                             buffer.append(scanner.nextLine().toLowerCase(new Locale("tr-TR")));
                               }
                         
                        cumleList.add(new String(buffer));
                    }
            }
       
      
        readStopWords();
        tokenize();
        removeStopWords();
        removeDuplicates((ArrayList<String>) tokens);
        stemmize();
 }
 
    
  public void readStopWords() throws FileNotFoundException{
     BufferedReader reader;
     File file = new File("src/main/java/stop-words-turkish-github.txt");
     reader = new BufferedReader(new FileReader(file));
            Scanner scanner = new Scanner(file); 
		while (scanner.hasNextLine()) {
                    stopWord.add(scanner.nextLine().toLowerCase());
                }
 }

  List<String> tokens = new ArrayList<String>();
     static TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;
 public void tokenize() throws FileNotFoundException{
     
     for(String ar:cumleList){
             Iterator<Token> tokenIterator = tokenizer.getTokenIterator(ar);
    while (tokenIterator.hasNext()) {
      //Token token = tokenIterator.next();
           tokens.add(tokenIterator.next().getText());
    }
     }
      //System.out.println(tokens.size());
 }
 TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
 List<String> stems = new ArrayList<String>();
 public void stemmize(){
     for(String a:tokens){
         WordAnalysis result = morphology.analyze(a);
for (SingleAnalysis analysis : result) {
            if (!stems.contains(analysis.getStems().get(0))) { 
                stems.add(analysis.getStems().get(0)); 
            } 
}
     }
 }
public void removeStopWords(){
    tokens.removeAll(stopWord);
   /* String a[]= {"0","1","2","3","4","5","6","7","8","9","!",":",";",",",".","?"};
    tokens.remove(a);*/
}
 public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) 
    { 
        // Create a new ArrayList 
        ArrayList<T> newList = new ArrayList<T>(); 
        // Traverse through the first list 
        for (T element : list) { 
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
                newList.add(element); 
            } 
        } 
        // return the new list 
        return newList; 
    } 
}