
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
/**
 *
 * @author manikhanuja
 */
public class TestPorterStemmer {
//   public static void main(String[] args) {
//        
//       Scanner scan = new Scanner(System.in);
//        System.out.println("Enter token");
//        while (scan.hasNext()) {
//            String token = scan.next();
//            if (token.equals("q")) {
//                break;
//            }
//            String processToken;
//            ArrayList<String> normalizeToken=new ArrayList<>();
//            normalizeToken = NormalizeToken.normalizeToken(token);
//                //System.out.println("Normalized Token: " + normalizeToken);
//                for(String tkn:normalizeToken){
//                //System.out.println("we are on "+tkn);
//                processToken = PorterStemmer.processToken(tkn);
//                System.out.println("stem: " + processToken);
//                }
//            
//        }
//
//    }
   
   public static String[] callPC(String token){
       String[] processToken;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter token");
//        while (scan.hasNext()) {
//     
//            if (token.equals("q")) {
//                break;
//            }
            
            ArrayList<String> normalizeToken=new ArrayList<>();
            normalizeToken = NormalizeToken.normalizeToken(token);
                //System.out.println("Normalized Token: " + normalizeToken);
            int i=0;
            processToken=new String[normalizeToken.size()];
                for(String tkn:normalizeToken){
                //System.out.println("we are on "+tkn);
                processToken[i] = PorterStemmer.processToken(tkn);
                System.out.println("stem: " + processToken);
                i++;
                }
            
   //}
        return processToken;

   }

}

               
                
                

    


