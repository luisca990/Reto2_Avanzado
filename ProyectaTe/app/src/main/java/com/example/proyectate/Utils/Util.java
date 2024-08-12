package com.example.proyectate.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Util {
    public static void showDialogueGenerico(Context context) {
        if (!(context instanceof AppCompatActivity)) {
            return;
        }

        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        DialogueGenerico.getInstance().showDialogue(appCompatActivity.getSupportFragmentManager(), "DialogoGenerico");
    }
    public static Boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    public static void hideKeyboard(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static void showDatePickerDialog(Context context, int type, OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Manejar la fecha seleccionada
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    // Llamar al callback con la fecha seleccionada
                    listener.onDateSelected(selectedDate, type);
                },
                year, month, day
        );
        // Establecer la fecha mínima (hoy) para el DatePicker
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    public interface OnDateSelectedListener {
        void onDateSelected(String date, int type);
    }
    public static void validateEmailFormat(EditText emailEditText, TextInputLayout textFieldEmail, String mess){
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (isValidEmail(email)) {
                    textFieldEmail.setError(null);
                } else {
                    textFieldEmail.setError(mess);
                }
            }
        });
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static void validateSizePassCharacters(EditText passEditText, TextInputLayout textFieldPass) {
        passEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String errorMessage = PasswordValidator.validatePassword(passEditText.getText().toString());
                textFieldPass.setError(errorMessage);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
