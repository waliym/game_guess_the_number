package com.example.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private TextView balance_str;
    private int balance_int = 0;
    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1001;
    private int game2_price = 50;
    private boolean[] by_game_2 = {false};

    private Button btn_game_2;
    private  int btn_color;
    private int balance_plas = 0;

    Dialog by_2game_dialog, ByLevels, dialog;

    private int lime_color, dark_color, grey, stand_color = 0, bg_int = 0;

    private ConstraintLayout bg_main;

    private ImageButton setting;

    SharedPreferences sPref, sPref1, sBg;


    final String balance_key = "sav_balance", balance_key1 = "sav_balance1", bg_key = "sav_bg";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balance_str = findViewById(R.id.balance);
        balance_str.setText(String.valueOf(balance_int));

        btn_game_2 = findViewById(R.id.btn_game_2);
        btn_color = ContextCompat.getColor(this, R.color.btn_color);
        lime_color = ContextCompat.getColor(this, R.color.lime);
        stand_color = ContextCompat.getColor(this, R.color.bg);
        dark_color = ContextCompat.getColor(this, R.color.dark);
        grey = ContextCompat.getColor(this, R.color.grey);
        bg_main = findViewById(R.id.bg_main);
        by_2game_dialog = new Dialog(MainActivity.this);


        setting = findViewById(R.id.btn_setting);
        dialog = new Dialog(MainActivity.this);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingDialog();
            }
        });





        sPref = getSharedPreferences ("test", MODE_PRIVATE);

        balance_str.setText(sPref.getString(balance_key, "пусто"));
        balance_int = Integer.parseInt(balance_str.getText().toString());

        sPref1 = getSharedPreferences ("test1", MODE_PRIVATE);
        by_game_2[0] = sPref1.getBoolean(balance_key1, false);

        sBg = getSharedPreferences("sBg", MODE_PRIVATE);
        bg_int = sBg.getInt(bg_key, 0);


        switch (bg_int)
        {
            case 0:
                bg_main.setBackgroundColor(stand_color);
                break;
            case 1:
                bg_main.setBackgroundColor(lime_color);
                break;
            case 2:
                bg_main.setBackgroundColor(dark_color);
                break;
        }




        if (by_game_2[0] == true)
        {
            btn_game_2.setBackgroundColor(btn_color);
        }
    }

    public void balance_plas(View view) {
        balance_plas++;
        if(balance_plas == 3)
        {
            balance_int += 50;
            balance_str.setText(String.valueOf(balance_int));
            balance_plas = 0;


        }
    }




    private void showSettingDialog() {
        dialog.setContentView(R.layout.setting);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton close_setting = dialog.findViewById(R.id.btn_setting_close);
        close_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
            }
        });

        MaterialButton clear = dialog.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balance_int = 0;
                balance_str.setText(String.valueOf(balance_int));
                by_game_2[0] = false;
                btn_game_2.setBackgroundColor(grey);
                bg_int = 0;
                bg_main.setBackgroundColor(stand_color);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(balance_key, balance_str.getText().toString());
                ed.apply();

                SharedPreferences.Editor g2 = sPref1.edit();
                g2.putBoolean(balance_key1, by_game_2[0]);
                g2.apply();

                SharedPreferences.Editor bg = sBg.edit();
                bg.putInt(bg_key, bg_int);
                bg.apply();

            }
        });
        dialog.show();
    }

    public void goTo1Game(View view) {
        Intent intent = new Intent(this, game_1.class);
        intent.putExtra("message", balance_int);
        intent.putExtra("bg", bg_int);
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
        overridePendingTransition(0, 0);
    }

    public void goTo2Game(View view) {
        if (by_game_2[0] == false)
        {

            ByLevels = by_2game_dialog;
            ByLevels.setContentView(R.layout.by_game_2);
            MaterialButton by_game_by = ByLevels.findViewById(R.id.by_game2_btn_by);
            MaterialButton by_game_btn_close = ByLevels.findViewById(R.id.by_game2_btn_close);
            TextView by_game2_text2 = ByLevels.findViewById(R.id.by_game2_text2);
            TextView by_game_text = by_game2_text2;

                    ByLevels(game2_price, by_game_2, ByLevels, by_game_by, by_game_btn_close, btn_game_2, by_game_text);
        } else {
            Intent intent = new Intent(this, Activity_game2.class);
            intent.putExtra("message", balance_int);
            intent.putExtra("bg", bg_int);
            startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
            overridePendingTransition(0, 0);
        }
    }

    private void ByLevels(int game_price, boolean[] by_game, Dialog ByLevels, MaterialButton by_game_by,
                          MaterialButton by_game_btn_close, Button btn_game, TextView by_game_text) {
        ByLevels.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        String mess = getResources().getString(R.string.by_game2_text2);
        String mess1 = getResources().getString(R.string.by_game2_text3);
        by_game_text.setText(mess + " " + game_price + " " + mess1);

        by_game_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByLevels.cancel();
            }
        });
        by_game_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(balance_int >= game_price)
                {
                    balance_int -= game_price;
                    balance_str.setText(String.valueOf(balance_int));
                    by_game[0] = true;
                    btn_game.setBackgroundColor(btn_color);
                    Toast.makeText(MainActivity.this, "Поздравляем с покупкой!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString(balance_key, balance_str.getText().toString());
                    ed.apply();
                    SharedPreferences.Editor g2 = sPref1.edit();
                    g2.putBoolean(balance_key1, by_game_2[0]);
                    g2.apply();
                    ByLevels.cancel();
                }else
                {
                    Toast.makeText(MainActivity.this, "Недостаточно монет!", Toast.LENGTH_SHORT).show();
                    ByLevels.cancel();
                }



            }
        });
        ByLevels.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY && resultCode == RESULT_OK && data != null) {
            int updatedBalance = data.getIntExtra("UPDATED_BALANCE", balance_int);
            // Обновляем значение переменной balance
            balance_int = updatedBalance;
            balance_str.setText(String.valueOf(balance_int));

            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(balance_key, balance_str.getText().toString());
            ed.apply();

            SharedPreferences.Editor g2 = sPref1.edit();
            g2.putBoolean(balance_key1, by_game_2[0]);
            g2.apply();

            // Здесь можно использовать обновленное значение переменной balance
        }
    }




    public void lime_bg(View view) {

            bg_int = 1;
            bg_main.setBackgroundColor(lime_color);

        SharedPreferences.Editor bg = sBg.edit();
        bg.putInt(bg_key, bg_int);
        bg.apply();

    }

    public void stand_bg(View view) {
            bg_int = 0;
            bg_main.setBackgroundColor(stand_color);

        SharedPreferences.Editor bg = sBg.edit();
        bg.putInt(bg_key, bg_int);
        bg.apply();
        }

    public void dark_bg(View view) {
        bg_int = 2;
        bg_main.setBackgroundColor(dark_color);

        SharedPreferences.Editor bg = sBg.edit();
        bg.putInt(bg_key, bg_int);
        bg.apply();
    }


    public void Click_btn_g3(View view) {
        Toast.makeText(MainActivity.this, "На стадии разработки", Toast.LENGTH_SHORT).show();
    }
}