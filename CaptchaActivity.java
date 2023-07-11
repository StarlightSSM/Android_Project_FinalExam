package com.inhatc.android_finalexamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class CaptchaActivity extends AppCompatActivity {
    private List<Integer> numbers;
    private EditText edtNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);

        TextView txtNumbers = findViewById(R.id.txtNumbers);
        edtNumbers = findViewById(R.id.edtNumbers);
        Button btnCheck = findViewById(R.id.btnCheck);

        numbers = generateRandomNumbers();
        StringBuilder numbersText = new StringBuilder("랜덤 숫자: ");
        for (int number : numbers) {
            numbersText.append(number).append(" ");
        }
        txtNumbers.setText(numbersText.toString());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumbers();
            }
        });
    }

    private List<Integer> generateRandomNumbers() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(9) + 1;
            numbers.add(number);
        }

        return numbers;
    }

    private void checkNumbers() {
        String userInput = edtNumbers.getText().toString().trim();
        String[] inputArray = userInput.split(" ");
        List<Integer> guessedNumbers = new ArrayList<>();

        for (String input : inputArray) {
            try {
                int number = Integer.parseInt(input);
                guessedNumbers.add(number);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (guessedNumbers.size() != numbers.size()) {
            Toast.makeText(this, "숫자 개수가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        } else if (checkNumbers(numbers, guessedNumbers)) {
            Toast.makeText(this, "인증되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CaptchaActivity.this, WeatherActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkNumbers(List<Integer> numbers, List<Integer> guessedNumbers) {
        if (numbers.size() != guessedNumbers.size()) {
            return false;
        }

        for (int i = 0; i < numbers.size(); i++) {
            if (!numbers.get(i).equals(guessedNumbers.get(i))) {
                return false;
            }
        }

        return true;
    }
}
