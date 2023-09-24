import java.util.*;
import java.lang.Character;
/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    enum Action {UP, DOWN, LEFT, RIGHT};
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";   

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        // game loop
        while (true) {
            String s = in.nextLine();
            System.err.println("Input: " + s);
            String[] stringArray = s.split(" ");
            List<String> strings = Arrays.asList(stringArray);
            System.err.println("Strings: " + strings);

//public static String toBinaryString(int i) method of the Integer class

            List<String> toBin = new ArrayList<>();

            List<Integer> numbers = new ArrayList<>();
            List<Character> chars = new ArrayList<>();
            strings.forEach(s1 -> {
                int ascii = Integer.parseInt(s1);
                numbers.add(ascii);
                char asciiToChar = (char)ascii;
                chars.add(asciiToChar);
            });
            System.err.println("Numbers: " + numbers);

            numbers.forEach(i -> {
                toBin.add(Integer.toBinaryString(i));
            });

            // System.err.println("Chars: " + chars);
            // System.err.println("Binary: " + toBin);
            String s1 = Action.valueOf(transform(chars)).toString();
            System.err.println(s1);
            System.out.println("RB");
        }
        //QTTLT : Vlrt jlkq wplrek tqfrto RB, KLUO, EQXF lt TYHPF.
        //ERROR: -our -ode -hould return UP, DOWN, LEFT, or RIGHT.
    }

    private static String transform(List<Character> chars) {

        for (int i = 0; i < chars.size(); i++) {

        }

        // System.err.println('V' - 'Y'); //-3
        // System.err.println('J' - 'C'); //7
        // System.err.println('W' - 'S'); //4
        // System.err.println('R' - 'U'); //-3
        // System.err.println('B' - 'P'); //-14
        // System.err.println('K' - 'D'); //7
        // System.err.println('L' - 'O'); //-3
        // System.err.println('U' - 'W'); //-2
        // System.err.println('O' - 'N'); //1
        // System.err.println('E' - 'L'); //-7
        // System.err.println('Q' - 'E'); //12
        // System.err.println('X' - 'F'); //18
        // System.err.println('F' - 'T'); //-14
        // System.err.println('T' - 'R'); //2
        // System.err.println('Y' - 'I'); //16
        // System.err.println('H' - 'G'); //1
        // System.err.println('P' - 'H'); //8
        // System.err.println('Q' - 'E'); //12
        // System.err.println('T' - 'R'); //2

        return "UP";
    }

     public static String encryptData(String inputStr, int shiftKey)   
    {   
        // convert inputStr into lower case   
        inputStr = inputStr.toLowerCase();   
          
        // encryptStr to store encrypted data   
        String encryptStr = "";   
          
        // use for loop for traversing each character of the input string   
        for (int i = 0; i < inputStr.length(); i++)   
        {   
            // get position of each character of inputStr in ALPHABET   
            int pos = ALPHABET.indexOf(inputStr.charAt(i));   
              
            // get encrypted char for each char of inputStr   
            int encryptPos = (shiftKey + pos) % 26;   
            char encryptChar = ALPHABET.charAt(encryptPos);   
              
            // add encrypted char to encrypted string   
            encryptStr += encryptChar;   
        }   
          
        // return encrypted string   
        return encryptStr;   
    }   

    public static String decryptData(String inputStr, int shiftKey)   
    {   
        // convert inputStr into lower case   
        inputStr = inputStr.toLowerCase();   
          
        // decryptStr to store decrypted data   
        String decryptStr = "";   
          
        // use for loop for traversing each character of the input string   
        for (int i = 0; i < inputStr.length(); i++)   
        {   
              
            // get position of each character of inputStr in ALPHABET   
            int pos = ALPHABET.indexOf(inputStr.charAt(i));   
              
            // get decrypted char for each char of inputStr   
            int decryptPos = (pos - shiftKey) % 26;   
              
            // if decryptPos is negative   
            if (decryptPos < 0){   
                decryptPos = ALPHABET.length() + decryptPos;   
            }   
            char decryptChar = ALPHABET.charAt(decryptPos);   
              
            // add decrypted char to decrypted string   
            decryptStr += decryptChar;   
        }   
        // return decrypted string   
        return decryptStr;   
    }   
}