package com.example.e_dcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ceaser_cipher extends AppCompatActivity {

    EditText editText;

    EditText key;
    TextView textView;

    String total = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceaser_cipher);



    }

    public void onclickk(View view)
    {
        editText = findViewById(R.id.ceaser_EditText);
        textView = findViewById(R.id.Ceaser_textView);
        key = findViewById(R.id.ceaser_key);
        int key_value;

        if (key.getText().toString().trim().isEmpty())
            key_value = 8;
        else
            key_value= Integer.parseInt(String.valueOf(key.getText()));



        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String en = encrypt(editText.getText().toString(),key_value);
                decrypt(en,key_value);
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



//--------------------------------------------------------------------------------------------------
    public String encrypt(String Text, int Kay) {
        StringBuilder encryptedText = new StringBuilder();
            int num;
            int isCapital = 0;
        total = "To encrypt: \n\n"+"C = ( P + k ) % 53\nk = " + Kay +"\n\n";
        for (char ch : Text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int encryptedChar = (ch - base + Kay) % 26 + base;
                if (encryptedChar-base < 0 )
                    encryptedChar +=26;
                if (base == 'A') {
                    num = ch - base + 26+1;
                    isCapital = 26;
                }
                else
                    num = ch - base+1;

                total += "C("+ ch + ") = ( " + num + " + "+ Kay +" ) %53 = " + (encryptedChar-base +1 +isCapital) + " --> "+ (char)encryptedChar + "\n\n" ;
                encryptedText.append((char) encryptedChar);
                isCapital = 0;
            } else {
                // Non-alphabetic characters remain unchanged
                encryptedText.append(ch);
            }
        }

        total += "\nThe encrypted text is: \n\n"+ "->  " + encryptedText.toString() + "\n";
        total += "__________________________________\n";
        return encryptedText.toString();
    }

//--------------------------------------------------------------------------------------------------


    public String decrypt(String Text, int Key) {
        // Decryption is essentially the same as encryption with a negative shift
//        return encrypt(ciphertext, 26 - shiftKey);

        StringBuilder decryptedText = new StringBuilder();
        total += "To decrypt: \n\n"+"P = ( C - k ) % 53\nk =  " + Key +"\n\n";

            int num;
        for (char ch : Text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int decryptedChar = (ch - base - Key) % 26 + base;
                // if the result is negative +26
                if (decryptedChar-base < 0 )
                    decryptedChar +=26;

                if (base == 'A')
                    num = ch - base + 26+1;
                else
                    num = ch - base+1;

                total += "C("+ ch + ") = ( " + num + " - "+ Key +" ) %53 = " + (decryptedChar-base+1) + " --> "+ (char)decryptedChar + "\n\n" ;
                decryptedText.append((char) decryptedChar);
            } else {
                // Non-alphabetic characters remain unchanged
                decryptedText.append(ch);
            }
        }

        total += "\nThe decrypted text is: \n\n"+ "->  " + decryptedText.toString() + "\n";
        return decryptedText.toString();
    }
}



