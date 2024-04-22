package com.helloworldapps.campusbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText username_edittext,password_edittext;
    Button login_button;
    String username,password,usertype;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if(isLoggedIn){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("usertype",getUsertype());
            startActivity(intent);
        }

        username_edittext = findViewById(R.id.username_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        login_button = findViewById(R.id.login_button);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputStream inputStream = getResources().openRawResource(R.raw.studentregistry);

                // Use the InputStream to create a File object
                File file = null;
                try {
                    file = streamToFile(inputStream, "studentregistry.xlsx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Read the Excel sheet using Apache POI
                //File file = new File(getExternalFilesDir(null), "user_details.xlsx");
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                XSSFWorkbook workbook = null;
                try {
                    workbook = new XSSFWorkbook(fis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                XSSFSheet sheet = workbook.getSheetAt(0);

// Loop through each row and compare values
                boolean matchFound = false;
                for (Row row : sheet) {
                    //String username = row.getCell(0).getStringCellValue();
                    //String password = row.getCell(1).getStringCellValue();


                    if(row.getCell(0).getCellType()== CellType.STRING)
                        username = row.getCell(0).getStringCellValue();
                    else if(row.getCell(0).getCellType()==CellType.NUMERIC)
                        username = String.valueOf(row.getCell(0).getNumericCellValue());

                    if(row.getCell(1).getCellType()== CellType.STRING)
                        password = row.getCell(1).getStringCellValue();
                    else if(row.getCell(1).getCellType()==CellType.NUMERIC)
                        password = String.valueOf(row.getCell(1).getNumericCellValue());

                    if(row.getCell(2).getCellType()== CellType.STRING)
                        usertype = row.getCell(2).getStringCellValue();
                    else if(row.getCell(2).getCellType()==CellType.NUMERIC)
                        usertype = String.valueOf(row.getCell(2).getNumericCellValue());

                    System.out.println(username+"--"+password+"=====================================");

                    if(!username_edittext.getText().toString().isEmpty()  && !password_edittext.getText().toString().isEmpty() && !username.isEmpty()  && !password.isEmpty()) {
                        if (username.equals(username_edittext.getText().toString()) && password.equals(password_edittext.getText().toString())) {
                            matchFound = true;
                            break;
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Invalid field", Toast.LENGTH_SHORT).show();
                    }
                }

// Handle the login result
                if (matchFound) {
                    // Navigate to the next activity
                    saveCredentials(username,password,usertype);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("usertype",usertype);
                    intent.putExtra("isloggedin",isLoggedIn);
                    startActivity(intent);
                    finish();
                } else {
                    // Display an error message
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

// Close the workbook and input stream
                try {
                    workbook.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(username + " " + password+"===========================");

            }
        });
    }

    private File streamToFile(InputStream inputStream, String fileName) throws IOException {
        File file = new File(getCacheDir(), fileName);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return file;
    }

    private void saveCredentials(String username, String password, String usertype) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("usertype",usertype);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private String getUsername() {
        return preferences.getString("username", null);
    }

    private String getPassword() {
        return preferences.getString("password", null);
    }

    private String getUsertype() {
        return preferences.getString("usertype", null);
    }


}