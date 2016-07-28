package amnesiac.cryptonic;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.*;


public class MainActivity extends AppCompatActivity {

    private String calcBook = "As t increases, P(t) becomes larger, so dP/dt becomes larger. In turn, P(t) increases even faster. That is, the rate of growth increases as the population increases. We therefore expect that the graph of the function P(t) might look like Figure 1.2. The value of P(t) at t = 0 is called an initial condition. If we start with a different initial condition we get a different function P(t) as is indicated in Figure 1.3. If P(0) is negative (remembering k > 0), we then have dP/dt < 0 for t = 0, so P(t) is initially decreasing. As t increases, P(t) becomes more negative. The graphs below the t-axis are mirror images of the graphs above the t -axis, although they are not physically meaningful because a negative population doesn’t make much sense. Our analysis of the way in which P(t) increases as t increases is called a qualitative analysis of the differential equation. If all we care about is whether the model predicts 'population explosions,' then we can answer 'yes, as long as P(0) > 0.'";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private EditText decryptInputMessage;
    private TextView decryptOutputMessage;
    private EditText encryptInputMessage;
    private TextView encryptOutputMessage;

    public void encryptButtonOnClick(View v)
    {
        Button encryptButton =  (Button) v;
        encryptInputMessage = (EditText) findViewById(R.id.inputMessage);
        encryptOutputMessage = (TextView) findViewById(R.id.txtOut);
        String message = encryptInputMessage.getText().toString();


        String text = calcBook.toLowerCase();
        message = message + " ";
        message = message.toLowerCase();
        ArrayList<String> allLetters = new ArrayList<String>();
        //get the amount of text that we are using
        int significantLetterCounter = 0;
        String significantLetterText = "";
        for(int i = 0; i < text.length(); i ++)
        {
            if(Character.isLetter(text.charAt(i)))
            {
                significantLetterText = significantLetterText + text.substring(i, i + 1);
            }
            if(significantLetterText.length() == message.length() * 5)
            {
                significantLetterCounter = i;
            }
        }
        text = text.substring(0, significantLetterCounter + 1);
        //add every letter of the text to the arraylist of allLetters
        for(int i = 0; i < significantLetterCounter + 1; i ++)
        {
            allLetters.add(text.substring(i, i + 1));
        }
        ArrayList<String> messageLetters = new ArrayList<String>();
        //add every letter of the message to the arraylist of messageLetters
        for(int i = 0; i < message.length(); i ++)
        {
            messageLetters.add(message.substring(i, i +1));
        }
        //convert every element of messageLetters into the binary format
        messageLetters = binaryConvert(messageLetters);
        //print out that binary format to check that it's working
        /*for(int i = 0; i < messageLetters.size(); i ++)
        {
        System.out.println(messageLetters.get(i));
        }
         */
        int messageCounter = 0;
        //messageCounter counts the letters in the message that are already used
        int textCounter = 0;
        //textCounter counts the 5 letters in the binary and then resets.
        String tempLetter = "";
        //tempLetter is either 0 or 1, determined by the string of 5 numbers.
        //encodes messages
        String textLetter = "";
        for(int  i= 0; i < significantLetterCounter; i ++)
        {
            textLetter = allLetters.get(i);
            tempLetter = messageLetters.get(messageCounter).substring(textCounter, textCounter + 1);
            // bug here
            if(Character.isLetter(textLetter.charAt(0)))
            // if the corresponding letter in the text is a letter
            {
                textCounter ++;
                //add a textcounter
                if(tempLetter.equals("1"))
                {
                    allLetters.set(i, textLetter.toUpperCase());
                    //if the tempLetter is equal to 1, make the letter in the text an upperCase.
                }
                if(textCounter == 5)
                //when textCounter reaches 5, reset textCounter and move on to the next letter of the message.
                {
                    textCounter = 0;
                    messageCounter ++;
                }//end textCounter if loop
            }
        } // end forloop
        //message: I am professor moriarty
        String test = "";
        for(int i = 0; i < allLetters.size(); i++)
        {
            test = test + allLetters.get(i);
        }
        test = test + "e";
        // end for loop
        /*
        if(messageLetters.get(messageLetters.size() - 1).equals("1"))
        {
        test = test + allLetters.get(allLetters.size() - 1).toUpperCase();
        }
        else
        {
        test = test + allLetters.get(allLetters.size() - 1);
        }
         */
        ;
        encryptOutputMessage.setText(test);
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(test,test);
        clipboard.setPrimaryClip(clip);
    }
    public void decryptButtonOnClick(View v)
    {
        encryptInputMessage = (EditText) findViewById(R.id.decryptText);
        encryptOutputMessage = (TextView) findViewById(R.id.decryptedMessage);
        String code = encryptInputMessage.getText().toString();
        String significantPart = "";
        for(int i = 0; i < code.length(); i ++)
        {
            if(Character.isLetter(code.charAt(i)))
            {
                significantPart = significantPart + code.charAt(i);
            }
        }
        /*if(significantPart.length()%5 == 0)
        {
        return "This isn't a code, try again. ";
        }
         */
        String binaryMessage = "";
        String message = "";
        int messageCounter = 0;
        for(int i = 0; i < significantPart.length(); i ++)
        {
            if(Character.isUpperCase(significantPart.charAt(i)))
            {
                binaryMessage = binaryMessage + "1";
            }
            else
            {
                binaryMessage = binaryMessage + "0";
            }
            messageCounter ++;
            if(messageCounter == 5)
            {
                if(binaryMessage.equals("00000"))
                {
                    message = message + ",";
                }
                if(binaryMessage .equals( "00001"))
                {
                    message = message + "y";
                }
                if(binaryMessage .equals( "00010"))
                {
                    message = message + "?";
                }
                if(binaryMessage .equals( "00011"))
                {
                    message = message + "d";
                }
                if(binaryMessage .equals( "00100"))
                {
                    message = message + "!";
                }
                if(binaryMessage .equals( "00101"))
                {
                    message = message + "e";
                }
                if(binaryMessage .equals( "00110"))
                {
                    message = message + "f";
                }
                if(binaryMessage .equals( "00111"))
                {
                    message = message + "z";
                }
                if(binaryMessage .equals( "01000"))
                {
                    message = message + "a";
                }
                if(binaryMessage .equals( "01001"))
                {
                    message = message + "r";
                }
                if(binaryMessage .equals( "01010"))
                {
                    message = message + "g";
                }
                if(binaryMessage .equals( "01011"))
                {
                    message = message + "c";
                }
                if(binaryMessage .equals( "01100"))
                {
                    message = message + " ";
                }
                if(binaryMessage .equals( "01101"))
                {
                    message = message + "h";
                }
                if(binaryMessage .equals( "01110"))
                {
                    message = message + "b";
                }
                if(binaryMessage .equals( "01111"))
                {
                    message = message + "q";
                }
                if(binaryMessage .equals( "10000"))
                {
                    message = message + ".";
                }
                if(binaryMessage .equals( "10001"))
                {
                    message = message + "'";
                }
                if(binaryMessage .equals( "10010"))
                {
                    message = message + "i";
                }
                if(binaryMessage .equals( "10011"))
                {
                    message = message + "s";
                }
                if(binaryMessage .equals( "10100"))
                {
                    message = message + "k";
                }
                if(binaryMessage .equals( "10101"))
                {
                    message = message + "p";
                }
                if(binaryMessage .equals( "10110"))
                {
                    message = message + "t";
                }
                if(binaryMessage .equals( "10111"))
                {
                    message = message + "j";
                }
                if(binaryMessage .equals( "11000"))
                {
                    message = message + "u";
                }
                if(binaryMessage .equals( "11001"))
                {
                    message = message + "o";
                }
                if(binaryMessage .equals( "11010"))
                {
                    message = message + "v";
                }
                if(binaryMessage .equals( "11011"))
                {
                    message = message + "l";
                }
                if(binaryMessage .equals( "11100"))
                {
                    message = message + "n";
                }
                if(binaryMessage .equals( "11101"))
                {
                    message = message + "w";
                }
                if(binaryMessage .equals( "11110"))
                {
                    message = message + "m";
                }
                if(binaryMessage .equals( "11111"))
                {
                    message = message + "x";
                }
                binaryMessage = "";
                messageCounter = 0;
            }
        }
        encryptOutputMessage.setText(message);
    }

     public static ArrayList<String> binaryConvert(ArrayList<String> message)
    {

        ArrayList<String> binary = new ArrayList<String>();
        String comma = "00000";
        String y = "00001";
        String questionMark = "00010";
        String d = "00011";
        String exclamationMark = "00100";
        String e = "00101";
        String f = "00110";
        String z = "00111";
        String a = "01000";
        String r = "01001";
        String g = "01010";
        String c = "01011";
        String space = "01100";
        String h = "01101";
        String b = "01110";
        String q = "01111";
        String period = "10000";
        String apostrophe = "10001";
        String i = "10010";
        String s = "10011";
        String k = "10100";
        String p = "10101";
        String t = "10110";
        String j = "10111";
        String u = "11000";
        String o = "11001";
        String v = "11010";
        String l = "11011";
        String n = "11100";
        String w = "11101";
        String m = "11110";
        String x = "11111";
        binary = message;
        for(int increment = 0; increment < message.size(); increment ++)
        {
            if(binary.get(increment).equals("a"))
            {
                binary.set(increment, a);
            }
            if(binary.get(increment).equals("b"))
            {
                binary.set(increment, b);
            }
            if(binary.get(increment).equals("c"))
            {
                binary.set(increment, c);
            }
            if(binary.get(increment).equals("d"))
            {
                binary.set(increment, d);
            }
            if(binary.get(increment).equals("e"))
            {
                binary.set(increment, e);
            }
            if(binary.get(increment).equals("f"))
            {
                binary.set(increment, f);
            }
            if(binary.get(increment).equals("g"))
            {
                binary.set(increment, g);
            }
            if(binary.get(increment).equals("h"))
            {
                binary.set(increment, h);
            }
            if(binary.get(increment).equals("i"))
            {
                binary.set(increment, i);
            }
            if(binary.get(increment).equals("j"))
            {
                binary.set(increment, j);
            }
            if(binary.get(increment).equals("k"))
            {
                binary.set(increment, k);
            }
            if(binary.get(increment).equals("l"))
            {
                binary.set(increment, l);
            }
            if(binary.get(increment).equals("m"))
            {
                binary.set(increment, m);
            }
            if(binary.get(increment).equals("n"))
            {
                binary.set(increment, n);
            }
            if(binary.get(increment).equals("o"))
            {
                binary.set(increment, o);
            }
            if(binary.get(increment).equals("p"))
            {
                binary.set(increment, p);
            }
            if(binary.get(increment).equals("q"))
            {
                binary.set(increment, q);
            }
            if(binary.get(increment).equals("r"))
            {
                binary.set(increment, r);
            }
            if(binary.get(increment).equals("s"))
            {
                binary.set(increment, s);
            }
            if(binary.get(increment).equals("t"))
            {
                binary.set(increment, t);
            }
            if(binary.get(increment).equals("u"))
            {
                binary.set(increment, u);
            }
            if(binary.get(increment).equals("v"))
            {
                binary.set(increment, v);
            }
            if(binary.get(increment).equals("w"))
            {
                binary.set(increment, w);
            }
            if(binary.get(increment).equals("x"))
            {
                binary.set(increment, x);
            }
            if(binary.get(increment).equals("y"))
            {
                binary.set(increment, y);
            }
            if(binary.get(increment).equals("z"))
            {
                binary.set(increment, z);
            }
            if(binary.get(increment).equals(" "))
            {
                binary.set(increment, space);
            }
            if(binary.get(increment).equals("."))
            {
                binary.set(increment, period);
            }
            if(binary.get(increment).equals(","))
            {
                binary.set(increment, comma);
            }
            if(binary.get(increment).equals("?"))
            {
                binary.set(increment, questionMark);
            }
            if(binary.get(increment).equals("'"))
            {
                binary.set(increment, apostrophe);
            }
            if(binary.get(increment).equals("!"))
            {
                binary.set(increment, exclamationMark);
            }
        }
        return binary;
    }

}
