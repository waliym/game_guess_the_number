package com.example.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class Activity_game2 extends AppCompatActivity {
    private TextView balance3;
    private int balance_int;
    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1001;


    private int[] bot_number;
    private TextView cout_rand;

    private TextView user_numb, cow, cow_man;
    private int check = 0;

    private int[] digits;

     ImageButton info_game2_btn_open;
     Dialog dialog_info_game2;

    SharedPreferences sPref;
    final String balance_key = "sav_balance";

    private ConstraintLayout bg_g2;
    private int lime_color;
    private int stand_color;
    private int dark_color;
    private int bg_int;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        balance3 = findViewById(R.id.balance3);
        int def = 0;

        balance_int = getIntent().getIntExtra("message", def);
        bg_int = getIntent().getIntExtra("bg", def);
        balance3.setText(String.valueOf(balance_int));

    bot_number = new int[4];
    cout_rand = findViewById(R.id.cout_rand);
        rand_numb();

        user_numb = findViewById(R.id.user_numb1);
        cow = findViewById(R.id.cow);
        cow_man = findViewById(R.id.cow_man);
        digits = new int [4];

        info_game2_btn_open = findViewById(R.id.info_game2_btn_open);
        dialog_info_game2 = new Dialog(Activity_game2.this);
        info_game2_btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        sPref = getSharedPreferences ("test", MODE_PRIVATE);

        bg_g2 = findViewById(R.id.bg_g2);
        lime_color = ContextCompat.getColor(this, R.color.lime);
        stand_color = ContextCompat.getColor(this, R.color.bg);
        dark_color = ContextCompat.getColor(this, R.color.dark);


        switch (bg_int)
        {
            case 0:
                bg_g2.setBackgroundColor(stand_color);
                break;
            case 1:
                bg_g2.setBackgroundColor(lime_color);
                break;
            case 2:
                bg_g2.setBackgroundColor(dark_color);
                break;
        }

        sPref = getSharedPreferences ("test", MODE_PRIVATE);

    }










    private void showCustomDialog() {
        dialog_info_game2.setContentView(R.layout.info_game2);
        dialog_info_game2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialButton close1 = dialog_info_game2.findViewById(R.id.info_game2_btn_close);
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_info_game2.cancel();
            }
        });

        dialog_info_game2.show();
    }

    public void back_main(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UPDATED_BALANCE", balance_int);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public  void rand_numb ()
    {

        Random random = new Random();
        // Генерируем случайное число от 1 до 100

        for (int i = 0; i < 4;)
        {
            int flag = 0;
            bot_number[i] = random.nextInt(10);
            if (i == 0)
            {
                i++;
                continue;
            }
            for (int a = 0; a < i; a++)
            {
                if (bot_number[i] == bot_number[a])
                {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0)
                i++;
        }
        String str1 = String.valueOf(bot_number[0]);
        String str2 = String.valueOf(bot_number[1]);
        String str3 = String.valueOf(bot_number[2]);
        String str4 = String.valueOf(bot_number[3]);





    }





    public void Click_user_numb_btn(View view) {
        int cow_int = 0;
        int cow_man_int = 0;
        String user_numb_str = cout_rand.getText().toString();
        int user_numb_int = Integer.parseInt(user_numb_str);
        user_numb.setText(user_numb_str);

        if (user_numb_int > 9999)
        {
            cout_rand.setText("число слишком большое");
            cout_rand.setTextSize(20);
            check = 1;
        }else{
            digits[0] = user_numb_int / 1000;
            digits[1] = (user_numb_int / 100) % 10;
            digits[2] = (user_numb_int / 10) % 10;
            digits[3] = user_numb_int % 10;

            for (int i = 0; i < 4; i++) {
                for (int a = i + 1; a < 4; a++) {
                    if (digits[i] == digits[a])
                    {
                        cout_rand.setText("Все числа должны быть разные");
                        cout_rand.setTextSize(20);
                        check = 1;
                        break;
                    }
                }
                if (check == 1)
                    break;
            }
            for (int i = 0; i < 4; i++)
            {
                if (digits[i] == bot_number[i])
                {
                    cow_man_int += 1;
                }
            }


            for (int i = 0; i < 4; i++)
            {
                for (int a = 0; a < 4; a++)
                {
                    if (digits[a] == bot_number[i])
                        cow_int += 1;
                }
            }

            if(cow_man_int == 4)
            {
                cout_rand.setText("Вы угадали!");
                balance_int += 30;
                balance3.setText(String.valueOf(balance_int));
                check = 1;

                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(balance_key, balance3.getText().toString());
                ed.apply();
            }

            if(check == 0)
            {
                cow_man.setText("На месте: " + cow_man_int);
                cow.setText("Совпадений: " + (cow_int - cow_man_int));
                cout_rand.setText("");
            }else
            {
                cow_man.setText("На месте: 0");
                cow.setText("Совпадений: 0");
            }

        }
    }

    public void click_n(View v) {
        cout_rand.setTextSize(30);
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        if(cout_rand.getText().toString().isEmpty())
        {
            check = 1;
        }
        if (check == 1)
        {
            cout_rand.setText(buttonText);
            check = 0;
        }else {
            int strint = Integer.parseInt(cout_rand.getText().toString());
            if (strint >= 9999)
            {

            }else {
                cout_rand.setText(cout_rand.getText() + buttonText);
            }
        }
    }


    public void click_n1(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        String str = cout_rand.getText().toString();
        int strint;
        if (check == 1)
        {
            strint = 0;
            check = 0;
        }else {
            strint = Integer.parseInt(str);
        }

        if (strint == 0)
        {
            cout_rand.setText(buttonText);
        }
        else {

            cout_rand.setText(cout_rand.getText() + buttonText);
        }
    }

    public void numb_del(View view)
    {
        if(cout_rand.getText().toString().isEmpty()){

        }else {
            if(check == 0) {
            String strr = cout_rand.getText().toString();
            strr = strr.substring(0, strr.length() - 1);
            cout_rand.setText(strr);

            }else
                cout_rand.setText("");
        }

    }

}