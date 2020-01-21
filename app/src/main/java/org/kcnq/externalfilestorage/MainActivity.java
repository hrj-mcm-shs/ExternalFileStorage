package org.kcnq.externalfilestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    final EditText callTextBox = findViewById(R.id.textBox);
    final Button callClearButton = findViewById(R.id.clearButton);
    final Button callReadButton = findViewById(R.id.readButton);
    final Button callWriteButton = findViewById(R.id.writeButton);

    private String filename = "jugar.txt";
    private String filepath = "jugar_externalio";
    File akongFile;
    String akongGitype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        callClearButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callTextBox.setText("");
                    }
                }
        );

        callReadButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fileIn=openFileInput("mytextfile.txt");
                            InputStreamReader InputRead= new InputStreamReader(fileIn);

                            char[] inputBuffer= new char[READ_BLOCK_SIZE];
                            String s="";
                            int charRead;

                            while ((charRead=InputRead.read(inputBuffer))>0) {
                                // char to string conversion
                                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                                s +=readstring;
                            }
                            InputRead.close();
                            callTextBox.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        callWriteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            FileOutputStream fos = new FileOutputStream(akongFile);
                            fos.write(callTextBox.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), "File saved successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            callWriteButton.setEnabled(false);
        }

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}

// hihihihi