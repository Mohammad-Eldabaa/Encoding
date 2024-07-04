package com.example.e_dcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Affine_activity extends AppCompatActivity {

    EditText key_a ;
    EditText key_b;
    EditText text;
    TextView textView;
    String total ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine);

    }

    public void onclick_affineRun(View view){
        key_a = findViewById(R.id.Kay_a);
        key_b = findViewById(R.id.Kay_b);
        text = findViewById(R.id.affine_EditText);
        textView = findViewById(R.id.affine_textView);

        int A;
        int B;
        if (key_a.getText().toString().trim().isEmpty() || key_b.getText().toString().trim().isEmpty())
        {
            A = 7;
            B = 10;
        }
        else
        {
            A = Integer.parseInt(key_a.getText().toString());
            B = Integer.parseInt(key_b.getText().toString());
        }

        if (is_gcd(A, 26) == 1) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String ciphertext = encrypt(String.valueOf(text.getText()), A, B);
                    decrypt(ciphertext,A,B);
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
        else {
            Toast.makeText(this,"The gcd don't equal 1",Toast.LENGTH_SHORT).show();

        }


    }
//__________________________________________________________________________________________________

    public String encrypt(String text, int a, int b) {
        StringBuilder ciphertext = new StringBuilder();

        total = "C = [ (a * P) + b ] % n \n";
        total += "a = " + a + "\nb= " + b + "\n\n";
        int num;
        int isCapital = 0;
        String cap= "";

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int encryptedChar = (a * (ch - base) + b) % 26 + base;

                if (encryptedChar-base < 0 )
                    encryptedChar +=26;

                if (base == 'A') {
                    num = ch - base + 26;
                    isCapital = 26;
                    cap = "(+26)";
                }
                else
                    num = ch - base;

                total += "C(" + ch + ") = " + "[(" + a + " * " + num + ") + " + b + "] %26 = " + (encryptedChar-base+isCapital) +cap + " --> "+ (char)encryptedChar + "\n\n" ;
                ciphertext.append((char) encryptedChar);
                isCapital = 0;
                cap = "";
            } else {
                // Non-alphabetic characters    remain unchanged
                ciphertext.append(ch);
            }
        }

        total += "\nThe encrypted text is: \n\n"+ "->  " + ciphertext.toString() + "\n";
        total += "_____________________________________\n";

        return ciphertext.toString();
    }
//__________________________________________________________________________________________________

    public String decrypt(String ciphertext, int a, int b) {
        int aInverse = modInverse(a, 26);
        StringBuilder decryptedText = new StringBuilder();


        total += "\nP = [ (a^-1) C - b ] % n \n\n";
        int num;

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int decryptedChar = (aInverse * (ch - base - b + 26)) % 26 + base;

                if (decryptedChar-base < 0 )
                    decryptedChar +=26;

                if (base == 'A') {
                    num = ch - base + 26;

                }
                else
                    num = ch - base;

                total += "P(" + ch + ") = " + "[ " + aInverse + "( " +  num + " - " + b +") " + "] %26 = " + (decryptedChar-base) + " --> "+ (char)decryptedChar + "\n\n" ;



                decryptedText.append((char) decryptedChar);
            } else {
                // Non-alphabetic characters remain unchanged
                decryptedText.append(ch);
            }
        }

        total += "\nThe decrypted text is: \n\n"+ "->  " + decryptedText.toString() + "\n";
        return decryptedText.toString();
    }

//__________________________________________________________________________________________________


    public int is_gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

//__________________________________________________________________________________________________
    public int modInverse(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ((a * i) % m == 1) {
                return i;
            }
        }
        return -1; // Inverse doesn't exist
    }

}