package com.todo.calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class CalcButton extends MaterialButton {
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void toScreen(EditText inputShow) {
        this.setOnClickListener(v -> {
            String current = inputShow.getText().toString();

            inputShow.setText(current + this.content);
            inputShow.setSelection(current.length()+1);
        });
    }

//    Extends
    public CalcButton(@NonNull Context context) {
        super(context);
    }

    public CalcButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalcButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
