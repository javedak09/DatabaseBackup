package com.javedak09.databasebackup;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void InsertRecord(View view) {
        CreateDatabase db = new CreateDatabase(this);
        ContentValues values = new ContentValues();

        values.put("id", "1");
        values.put("nme", "jojo");

        db.Insert(values);

        Toast.makeText(this, "Saved record successfully", Toast.LENGTH_LONG).show();
    }


    public void BackupDatabase(View v) {
        //exportDB("mydatabase.db");
        exportDB1("mydatabase.db");
    }


    private void exportDB1(String db_name) {
        final String inFileName = "/data/data/" + this.getPackageName() + "/databases/" + db_name;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dt = new Date();

            File sd = new File(Environment.getExternalStorageDirectory() + "/DBBackup-" + sdf.format(dt));

            boolean success = true;
            if (!sd.exists()) {
                success = sd.mkdir();
            }


            if (success) {

                File dbFile = new File(inFileName);

                FileInputStream fis = new FileInputStream(dbFile);

                String outFileName = Environment.getExternalStorageDirectory() + "/DBBackup-" + sdf.format(dt) + "/" + db_name;

                Toast.makeText(this, outFileName, Toast.LENGTH_SHORT).show();

                OutputStream output = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = fis.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                fis.close();
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void exportDB(String db_name) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date();

        File sd = new File(Environment.getExternalStorageDirectory() + "/DBBackup-" + sdf.format(dt));

        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }

        Toast.makeText(this, String.valueOf(success), Toast.LENGTH_SHORT).show();


        if (success) {

            File data = Environment.getDataDirectory();

            Toast.makeText(this, Environment.getDataDirectory().getPath(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath(), Toast.LENGTH_LONG).show();

            FileChannel source = null;
            FileChannel destination = null;
            String currentDBPath = "/data/" + this.getPackageName() + "/databases/" + db_name;

            Log.d(TAG, "exportDB: " + currentDBPath);

            String backupDBPath = Environment.getExternalStorageDirectory() + "/" + db_name;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(this, "Please wait", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}