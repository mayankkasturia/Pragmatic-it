/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author manikhanuja
 */
public class NormalizeToken {

    public static ArrayList normalizeToken(String token) {
        //convert the token to lower case
        //String[] term = new String[]{};
        ArrayList<String> term = new ArrayList<>();
        if (token != null) {

            token = token.toLowerCase();
            // remove all non-alphanumeric characters from begining
            token = token.replaceAll("^[^a-zA-Z0-9\\s]*", "");
        //System.out.println("Matched Non alphanumeric at begining: " + token);
            // remove all non-alphanumeric characters from end
            token = token.replaceAll("[^a-zA-Z0-9\\s]*$", "");
        //System.out.println("Matched Non alphanumeric at end: " + token);
            // remove all apostropes (single quotes)
            token = token.replaceAll("[']", "");
        //System.out.println("Remove all single quotes: " + token);

            if (token.contains("-")) {

                String[] str = token.split("-");
                for (int i = 0; i < str.length; i++) {
                    String p = str[i];
                    term.add(p);
                }
            //term.add(Arrays.toString(token.split("-")));
                //term.stream().forEach((s) -> {
                //  System.out.println("print tokens: " + s);
                //});
                token = token.replaceAll("[-]", "");
                //System.out.println("TOKEN:::: "+token);
            }

            term.add(token);
        }
        return term;
    }
}
