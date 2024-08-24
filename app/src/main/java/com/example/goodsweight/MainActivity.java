package com.example.goodsweight;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private EditText _editText1; //given weight
    private EditText _editText2; //market weight
    private EditText _editText3;

    private TextView _resultText;

    public String goodsWeightCalculator(double actualWeightTotal, double marketWeight, double price) {

        final int MON = 40;
        long weightMons = 0;
        double weightKgs;
        double finalPrice;

        String result = "";

        if (marketWeight >= MON) {

            if (actualWeightTotal > MON) {
                weightMons = (long) (actualWeightTotal / marketWeight);
                weightKgs = actualWeightTotal % marketWeight;
                weightKgs = (weightKgs / marketWeight) * MON;
                result += weightMons + " মণ & " + String.format("%.4f", weightKgs).concat(" কেজি");
            } else {
                weightKgs = (actualWeightTotal / marketWeight) * MON;
                result += String.format("%.4f", weightKgs).concat(" কেজি");
            }
            double totalWeightInKgs = (weightMons * marketWeight) + weightKgs;
            finalPrice = (price / marketWeight) * totalWeightInKgs;
            return result + "\n #" + String.format("%.2f", finalPrice) + "টাকা";
        } else {
            return "বাজারের ওজন 39 এর বেশি হতে হবে!!!";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        _editText1 = findViewById(R.id.editText1Id);
        _editText2 = findViewById(R.id.editText2Id);
        _editText3 = findViewById(R.id.editText3Id);

        Button clearButton = findViewById(R.id.clearButtonId);
        Button resultButton = findViewById(R.id.resultButtonId);

        _resultText = findViewById(R.id.resultTextId);


        _editText1.setOnClickListener(handler);
        _editText2.setOnClickListener(handler);

        clearButton.setOnClickListener(handler);
        resultButton.setOnClickListener(handler);

        _resultText.setOnClickListener(handler); //Optional
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setTitle(R.string.title_text);
        alertDialogBuilder.setMessage(R.string.message_text);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(MainActivity.this, "iYousuf", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    class Handler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                if (v.getId() == R.id.clearButtonId) {
                    _resultText.setText("");
                    _editText1.setText("");
                    _editText2.setText("");
                    _editText3.setText("");
                    Toast.makeText(MainActivity.this,R.string.all_edit_text_empty, Toast.LENGTH_SHORT).show();
                } else {
                    //Values from TextEdits
                    String actualWeightStr = _editText1.getText().toString().trim();
                    String marketWeightStr = (_editText2.getText().toString().trim());
                    String priceStr = _editText3.getText().toString().trim();

                    //String to Double
                    double actualWeightTotal = Double.parseDouble(actualWeightStr);
                    double marketWeight = Double.parseDouble(marketWeightStr);
                    double price = Double.parseDouble(priceStr);

                    String result = goodsWeightCalculator(actualWeightTotal, marketWeight, price);
                    _resultText.setText(result);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setIcon(R.drawable.information);
                    alertDialogBuilder.setTitle(R.string.title_result_dialog);
                    alertDialogBuilder.setMessage(result);

                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            } catch (NumberFormatException numberFormatException) {
                Toast.makeText(MainActivity.this, R.string.exception_message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}