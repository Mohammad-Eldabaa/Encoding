package com.example.e_dcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Vigenere_activity extends AppCompatActivity {

    EditText editText;

    EditText key;
    TextView textView;

    String total = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere);


    }


    public void onclick_vig(View view)
    {
        editText = findViewById(R.id.vigenere_text);
        textView = findViewById(R.id.vigenere_textView);
        key = findViewById(R.id.vigenere_key);
        String theKey ;

        if (key.getText().toString().trim().isEmpty())
        {
            theKey = "iteam" ;
        }
        else
        {

            theKey = key.getText().toString().trim().toLowerCase().replace(" ","");
        }
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String encrypt = encrypt(String.valueOf(editText.getText()),theKey);
                decrypt(encrypt,theKey);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(total);

                    }
                });

            }
        });

        thread1.start();
    }

//__________________________________________________________________________________________________

    public String encrypt(String message, String key) {
        StringBuilder encryptedText = new StringBuilder();

        total = "To encrypt: \n\n"+"C = ( P + k ) % n\nk = " + key +"\n\n";

        String cap= "";
        int num = 0;

        int messageLength = message.length();
        int keywordLength = key.length();

        for (int i = 0; i < messageLength; i++) {
            char currentChar = message.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                char encryptedChar = (char) ((currentChar + key.charAt(i % keywordLength) - 2 * base) % 26 + base);

                if (base == 'A') {
                    num = currentChar - base + 26;
                    cap = "(+26)";
                }
                else
                    num = currentChar - base;


                total += "C("+ currentChar + ") = ( " + num + " + "+ (int)((key.charAt(i % keywordLength) - base)%26) +" ) %26 = " + (encryptedChar-base ) + cap +" --> "+ (char)encryptedChar + "\n\n" ;

                encryptedText.append(encryptedChar);
                num = 0;
                cap = "";
            } else {


                encryptedText.append(currentChar);
            }
        }

        total += "\nThe encrypted text is: \n\n"+ "->  " + encryptedText + "\n";
        total += "_____________________________________\n";
        return encryptedText.toString();
    }

//____________________________________________________________________________________________________



    public   String decrypt(String encryptedMessage, String key) {
        StringBuilder decryptedText = new StringBuilder();

        total += "To decrypt: \n\n"+"P = ( C - k ) % 53\nk =  " + key +"\n\n";

        int messageLength = encryptedMessage.length();
        int keywordLength = key.length();

        String cap= "";
        int num = 0;


        for (int i = 0; i < messageLength; i++) {
            char currentChar = encryptedMessage.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                char decryptedChar = (char) ((currentChar - key.charAt(i % keywordLength) + 26) % 26 + base);

                if (decryptedChar-base < 0 ) {
                    decryptedChar += 26;
                    cap = "(+26)";
                }

                if (base == 'A') {
                    num = currentChar - base + 26;

                }
                else
                    num = currentChar - base;




                total += "C("+ currentChar + ") = ( " + num + " - "+ (int)((key.charAt(i % keywordLength) - base)%26) +" ) %26 = " + (decryptedChar-base ) + cap +" --> "+ (char)decryptedChar + "\n\n" ;



                num = 0;
                cap = "";
                decryptedText.append(decryptedChar);
            } else {
                // Non alphabetic characters remain unchanged
                decryptedText.append(currentChar);
            }
        }

        total += "\nThe decrypted text is: \n\n"+ "->  " + decryptedText + "\n";
        return decryptedText.toString();
    }



}