/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 * @author pasindu-tennage
 */
public class Classifier {

    public String getLanguage(String text) {
        if (text.trim().length() > 0) {
            List<String> tokens = this.getTokens(text);

            if (isSinhalaUnicode(tokens)) {
                return "Sinhala Unicode";
            }
            if (isTamilUnicode(tokens)) {
                return "Tamil Unicode";
            }
            if (isTACE16(tokens)) {
                return "TACE16";
            }

            //BAMINI 0
            //TSCII 1 
            //ANJAL 2 
            //TAB 3 
            //TAM 4
            String uniString = "";
            uniString = new TamilConverter().convertToTamil(0, text);
            tokens = this.getTokens(uniString);
            if (uniString.trim().length() > 0 && tokens.size() > 0 && isTamilUnicode(tokens)) {
                return "Tamil BAMINI";
            }
            uniString = new TamilConverter().convertToTamil(1, text);
            tokens = this.getTokens(uniString);
            if (uniString.trim().length() > 0 && tokens.size() > 0 &&isTamilUnicode(tokens)) {
                return "Tamil TSCII";
            }
            uniString = new TamilConverter().convertToTamil(2, text);
            tokens = this.getTokens(uniString);
            if (uniString.trim().length() > 0 && tokens.size() > 0 &&isTamilUnicode(tokens)) {
                return "Tamil ANJAL";
            }
            uniString = new TamilConverter().convertToTamil(3, text);
            tokens = this.getTokens(uniString);
            if (uniString.trim().length() > 0 && tokens.size() > 0 &&isTamilUnicode(tokens)) {
                return "Tamil TAB";
            }
            uniString = new TamilConverter().convertToTamil(4, text);
            tokens = this.getTokens(uniString);
            if (uniString.trim().length() > 0 && tokens.size() > 0 &&isTamilUnicode(tokens)) {
                return "Tamil TAM";
            }

            return "Unknown";
        }
        return "Empty File";
    }

    public List<String> getTokens(String input) {
        List<String> tokens = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(input);
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        return tokens;

    }

    public boolean isSinhalaUnicode(List<String> tokens) {
        int count = 0;
        String punctutations = ".,:;\"\'!~`@#$%^&*()_+-=|\\\"\':;<,>.?/“”‘’";
        List<String> nonSinhalaUnicode = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (Pattern.matches("\\p{Punct}", tokens.get(i)) || tokens.get(i).trim().isEmpty()) {
                continue;
            }
            for (int j = 0; j < tokens.get(i).length(); j++) {
                if (Character.isAlphabetic(tokens.get(i).charAt(j)) || Character.isDigit(tokens.get(i).charAt(j))
                        || Pattern.matches("\\p{Punct}", tokens.get(i).charAt(j) + "") || punctutations.contains(tokens.get(i).charAt(j) + "")) {
                    continue;
                }
//                String unicode =  String.format("%04x", (int) tokens.get(i).charAt(j)); 
                int unicode = tokens.get(i).codePointAt(j);
                if ((unicode > 3456 && unicode < 3583) | unicode == 8205) {
                    count++;
                } else {
                    count--;
                    if (!nonSinhalaUnicode.contains(tokens.get(i))) {
                        nonSinhalaUnicode.add(tokens.get(i));
                    }
                }
            }
        }
        //3456 - 3583                 

        for (int i = 0; i < nonSinhalaUnicode.size(); i++) {
            //System.out.println(nonSinhalaUnicode.get(i));
        }
        if (count
                > 0) {
            return true;
        }

        return false;

    }

    public boolean isTamilUnicode(List<String> tokens) {
        int count = 0;
        String punctutations = ".,:;\"\'!~`@#$%^&*()_+-=|\\\"\':;<,>.?/“”‘’";
        List<Integer> nonTamilUnicode = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (Pattern.matches("\\p{Punct}", tokens.get(i)) || tokens.get(i).trim().isEmpty()) {
                continue;
            }
            for (int j = 0; j < tokens.get(i).length(); j++) {
                if (Character.isAlphabetic(tokens.get(i).charAt(j)) || Character.isDigit(tokens.get(i).charAt(j))
                        || Pattern.matches("\\p{Punct}", tokens.get(i).charAt(j) + "") || punctutations.contains(tokens.get(i).charAt(j) + "")) {
                    continue;
                }
//                String unicode =  String.format("%04x", (int) tokens.get(i).charAt(j));
                int unicode = tokens.get(i).codePointAt(j);
                if ((unicode > 2944 && unicode < 3071) || unicode == 8204 || unicode == 8211 || unicode == 8203) {
                    count++;
                } else {
                    count--;
                    if (!nonTamilUnicode.contains(unicode)) {
                        nonTamilUnicode.add(unicode);
                    }
                }
                //3456 - 3583                 
            }

        }
        for (int i = 0; i < nonTamilUnicode.size(); i++) {
            //System.out.println(nonTamilUnicode.get(i));
        }
        if (count > 0) {
            return true;
        }
        return false;

    }

    public boolean isTACE16(List<String> tokens) {
        int count = 0;
        String punctutations = ".,:;\"\'!~`@#$%^&*()_+-=|\\\"\':;<,>.?/“”‘’";
        List<Integer> nonTamilUnicode = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (Pattern.matches("\\p{Punct}", tokens.get(i)) || tokens.get(i).trim().isEmpty()) {
                continue;
            }
            for (int j = 0; j < tokens.get(i).length(); j++) {
                if (Character.isAlphabetic(tokens.get(i).charAt(j)) || Character.isDigit(tokens.get(i).charAt(j))
                        || Pattern.matches("\\p{Punct}", tokens.get(i).charAt(j) + "") || punctutations.contains(tokens.get(i).charAt(j) + "")) {
                    continue;
                }
//                String unicode =  String.format("%04x", (int) tokens.get(i).charAt(j));
                int unicode = tokens.get(i).codePointAt(j);
                if ((unicode > 57600 && unicode < 58367) || unicode == 8204 || unicode == 8211 || unicode == 8203) {
                    count++;
                } else {
                    count--;
                    if (!nonTamilUnicode.contains(unicode)) {
                        nonTamilUnicode.add(unicode);
                    }
                }
                //3456 - 3583                 
            }

        }
        for (int i = 0; i < nonTamilUnicode.size(); i++) {
            //System.out.println(nonTamilUnicode.get(i));
        }
        if (count > 0) {
            return true;
        }
        return false;

    }

}
