package com.todo.calculator;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

/*
* Test case:
* -2*3
* -4/-2
*/

public class MainActivity extends AppCompatActivity {

    private boolean isOperation(String src) {
        switch (src) {
            case "+":
            case "*":
            case "-":
            case "/":
                return true;
        }
        return false;
    }
    private boolean isArrayNotOperation(ArrayList<String> src) {
        for (String str: src){
            if(isOperation(str)) {
                return false;
            }
        }

        return true;
    }
    private double calcProccess(ArrayList<String> src){
        Double temp = 0.0;

        while( !isArrayNotOperation(src) )
        {
            for(int i = 1; i < src.size(); i++){
                    switch (src.get(i)) {
                        case "*": {
                            temp = Double.parseDouble(src.get(i-1)) * Double.parseDouble(src.get(i+1));
                            String tempS = temp.toString();
                            src.set(i-1, tempS);
                            src.remove(i);
                            src.remove(i);
                            break;
                        }
                        case "/": {
                            if(Double.parseDouble(src.get(i+1)) != 0.0) {
                                temp = Double.parseDouble(src.get(i-1)) / Double.parseDouble(src.get(i+1));
                                String tempS = temp.toString();
                                src.set(i-1, tempS);
                                src.remove(i);
                                src.remove(i);
                            }else{
                                return 0;
                            }
                            break;
                        }
                    }
            }
            for(int i = 1; i < src.size(); i++){
                if(isOperation(src.get(i))){
                    switch (src.get(i)) {
                        case "+": {
                            temp = Double.parseDouble(src.get(i-1)) + Double.parseDouble(src.get(i+1));
                            String tempS = temp.toString();
                            src.set(i-1, tempS);
                            src.remove(i);
                            src.remove(i);
                            break;
                        }
                        case "-": {
                            temp = Double.parseDouble(src.get(i-1)) - Double.parseDouble(src.get(i+1));
                            String tempS = temp.toString();
                            src.set(i-1, tempS);
                            src.remove(i);
                            src.remove(i);
                            break;
                        }
                    }
                }
            }
        }

        return Double.parseDouble(src.get(0));
    }

    public void calculator() {
        // Nút số (0 - 9)
        ArrayList<CalcButton> butttonArray = new ArrayList<>();
        String historyList[] = {"0","0","0"};
        int[] numberButtonIds = {
                R.id.calc_btn_0, R.id.calc_btn_1, R.id.calc_btn_2,
                R.id.calc_btn_3, R.id.calc_btn_4, R.id.calc_btn_5,
                R.id.calc_btn_6, R.id.calc_btn_7, R.id.calc_btn_8, R.id.calc_btn_9
        };

        // Các nút số
        for (int i = 0; i < 10; i++) {
            CalcButton button = findViewById(numberButtonIds[i]);
            button.setContent(String.valueOf(i));

            butttonArray.add(button);
        }

        // Nút chức năng
        CalcButton btnPlus     = findViewById(R.id.calc_btn_plus);
        CalcButton btnMinus    = findViewById(R.id.calc_btn_minus);
        CalcButton btnMultiply = findViewById(R.id.calc_btn_multiply);
        CalcButton btnDivide   = findViewById(R.id.calc_btn_division);
        CalcButton btnEqual    = findViewById(R.id.calc_btn_equal);
        CalcButton btnRefesh   = findViewById(R.id.calc_btn_AC);

        //Dua vao array -> xu ly so luong lon onClickListener
        butttonArray.addAll(Arrays.asList(btnPlus, btnMultiply, btnMinus, btnDivide));

        // Gán content
        btnPlus.setContent("+");
        btnMinus.setContent("-");
        btnMultiply.setContent("*");
        btnDivide.setContent("/");

        // Màn hình hiển thị
        EditText inputShow     = findViewById(R.id.calc_input_show);
        TextView outputShow    = findViewById(R.id.calc_output_show);
        TextView historyShow[] = {
            findViewById(R.id.calc_history1),
            findViewById(R.id.calc_history2),
            findViewById(R.id.calc_history3),
        };

//Xu ly click
        for (CalcButton button: butttonArray) {
            button.toScreen(inputShow);
        }

        //Refesh I/O
        btnRefesh.setOnClickListener(v -> {
            inputShow.setText("");
            outputShow.setText("");
        });
        //Print result
        btnEqual.setOnClickListener(v -> {
            String src = inputShow.getText().toString();

            ArrayList<String> slideString = new ArrayList<String>();
            String temp = "";

        //Chuan hoa chuoi!
        if(src.isEmpty()) {
            Snackbar.make(findViewById(R.id.main),"", 100);
        }
        //Tach chuoi
            for(int i = 0; i < src.length(); ++i) {
                if(src.charAt(i) >= '0' && src.charAt(i) <= '9') {
                    temp += src.charAt(i);
                }else {
                    slideString.add(temp);
                    temp = "" + src.charAt(i);
                    slideString.add(temp);
                    temp = "";
                }
            }
            if (!temp.isEmpty()) {
                slideString.add(temp);
            }
            //Truong hop so dau tien la so am, chuyen ve cuoi
            if(slideString.get(0) == "-") {
                String tempO = slideString.get(0);
                String tempF = slideString.get(1);

                slideString.remove(0);
                slideString.remove(1);

                slideString.add(tempO);
                slideString.add(tempF);
            }

            //calc
            Double result = calcProccess(slideString);
            historyList[2]= historyList[1];
            historyList[1]= historyList[0];
            historyList[0]= String.format("%.3g", result);
            //out
            for(int i = 0; i < 3 ; ++i) {
                historyShow[i].setText(historyList[i]);
            }
            outputShow.setText(String.format("%.5g", result));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        calculator();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}