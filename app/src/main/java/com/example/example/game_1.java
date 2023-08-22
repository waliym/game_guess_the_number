package com.example.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class game_1 extends AppCompatActivity {
    private TextView balance2;
    private int balance_int;
    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1001;
    private TextView rand_text;


    private TextView more_user;
    private TextView less_user;
    private  int randomNumber;
    private boolean[] win = {false};
    private int bg_int;

    ImageButton show;
    Dialog dialog;
    private ConstraintLayout bg_g1;
    private int lime_color;
    private int stand_color;
    private int dark_color;
    SharedPreferences sPref;
    final String balance_key = "sav_balance";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        balance2 = findViewById(R.id.balance2);
        rand_text = findViewById(R.id.randText);




        more_user = findViewById(R.id.more_user);
        less_user = findViewById(R.id.less_user);
        int def = 0;

        balance_int = getIntent().getIntExtra("message", def);
        bg_int = getIntent().getIntExtra("bg", def);
        balance2.setText(String.valueOf(balance_int));


        Random random = new Random();
        // Генерируем случайное число от 1 до 100
        randomNumber = random.nextInt(100) + 1;

        show = findViewById(R.id.info_game1_btn_open);
        dialog = new Dialog(game_1.this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        bg_g1 = findViewById(R.id.bg_g1);
        lime_color = ContextCompat.getColor(this, R.color.lime);
        stand_color = ContextCompat.getColor(this, R.color.bg);
        dark_color = ContextCompat.getColor(this, R.color.dark);



        switch (bg_int)
        {
            case 0:
                bg_g1.setBackgroundColor(stand_color);
                break;
            case 1:
                bg_g1.setBackgroundColor(lime_color);
                break;
            case 2:
                bg_g1.setBackgroundColor(dark_color);
                break;
        }

        sPref = getSharedPreferences ("test", MODE_PRIVATE);

    }

    private void showCustomDialog ()
    {
    dialog.setContentView(R.layout.info_game1);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    MaterialButton close = dialog.findViewById(R.id.info_game1_btn_close);
    close.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.cancel();
        }
    });
    dialog.show();
    }

    public void back_main(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UPDATED_BALANCE", balance_int);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);
    }




    public void chect_user_numb(View view)
    {
        int user_numb_rund_int;

        String str = rand_text.getText().toString();
        if(rand_text.getText().toString().isEmpty()){
            user_numb_rund_int = 0;
        }else {
            user_numb_rund_int = Integer.parseInt(str);
        }

        if (randomNumber > user_numb_rund_int)
        {
                more_user.setText(String.valueOf(user_numb_rund_int));
        }
        if (randomNumber < user_numb_rund_int)
        {
            less_user.setText(String.valueOf(user_numb_rund_int));
        }
        rand_text.setText("0");
        if (randomNumber == user_numb_rund_int)
        {
            balance_int += 5;
            balance2.setText(String.valueOf(balance_int));
            rand_text.setText("Вы угадали число!!!");
            win[0] = true;

            Random random = new Random();
            randomNumber = random.nextInt(100) + 1;


            more_user.setText("-");
            less_user.setText("-");

            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(balance_key, balance2.getText().toString());
            ed.apply();

        }



    }

    public void click_n(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        String str = rand_text.getText().toString();
        int strint;
        if (win[0] == true)
        {
            strint = 0;
        }else {
            strint = Integer.parseInt(str);
        }
        win[0] = false;

        if (strint == 0)
        {

        rand_text.setText(buttonText);
        }else if (strint >= 10)
        {

        }
        else {

            rand_text.setText(rand_text.getText() + buttonText);
        }
    }

    public void numb_del(View view) {
        if(rand_text.getText().toString().isEmpty()){

        }else {
            int user_numb_rund_intt;
            String strr = rand_text.getText().toString();
            user_numb_rund_intt = Integer.parseInt(strr);
            user_numb_rund_intt = user_numb_rund_intt / 10;
            rand_text.setText(String.valueOf(user_numb_rund_intt));

        }

    }
}