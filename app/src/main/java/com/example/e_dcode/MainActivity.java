package com.example.e_dcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onclick_ceaser(View view)
    {
        Intent intent = new Intent(this, ceaser_cipher.class);
        startActivity(intent);
    }

    public void onclick_affine(View view)
    {
        Intent intent = new Intent(this, Affine_activity.class);
        startActivity(intent);
    }

    public void onclick_vigenere(View view)
    {
        Intent intent = new Intent(this, Vigenere_activity.class);
        startActivity(intent);
    }

    public void onclick_button(View view)
    {
        Toast.makeText(this,"أنا مليش لازمة متضغطش تاني\uD83D\uDE01",Toast.LENGTH_SHORT).show();
    }
}