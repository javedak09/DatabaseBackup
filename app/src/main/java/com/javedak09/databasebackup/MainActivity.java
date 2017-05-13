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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

    public void deleteFoldersOlder(View view) {
        deleteFolders("mydatabase.db");
    }

    private void deleteFolders(String db_name) {

        //int numOfDays = -3;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date();

        //String path = "/DBBackup-" + sdf.format(dt) + "/mydatabase.db";
        //String path = Environment.getExternalStorageDirectory() + "/DBBackup-" + sdf.format(dt) + "/" + db_name;

        //File file = new File(path);

        //Toast.makeText(this, path, Toast.LENGTH_LONG).show();

        //FileHandler fh;
        //Calendar ThreeDaysAgo = Calendar.getInstance();
        //Calendar currentDate = Calendar.getInstance();


        //Logger logger = Logger.getLogger("MyLog");
        //ThreeDaysAgo.add(Calendar.DAY_OF_MONTH, numOfDays);

        //File[] files = file.listFiles();


        try {

            //Date lastModDate = new Date(file.lastModified());

            //Toast.makeText(this, String.valueOf(lastModDate.before(ThreeDaysAgo.getTime())), Toast.LENGTH_LONG).show();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            for (int days = 3; days >= 0; days--) {

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, days);

                String path = Environment.getExternalStorageDirectory() + "/DBBackup-" + dateFormat.format(cal.getTime()) + "/" + db_name;
                File file = new File(path);
                Toast.makeText(this, path, Toast.LENGTH_LONG).show();

                File[] files = file.listFiles();


                Toast.makeText(this, "Deleting file ... " + file.getName(), Toast.LENGTH_LONG).show();
                file.delete();
                file.getParentFile().delete();

            }

            //Toast.makeText(this, dateFormat.format(cal.getTime()), Toast.LENGTH_LONG).show();


            /*if (file.isDirectory() && file.exists()) {

                String[] children = file.list();
                for (i = 0; i < children.length; i++) {
                    boolean success = file.delete();
                    Toast.makeText(this, String.valueOf(success), Toast.LENGTH_LONG).show();
                }

            } else {

                file.delete();
                Toast.makeText(this, file.getName() + " not a directory", Toast.LENGTH_LONG).show();
            }*/


            /*for (File f : files) {

                Toast.makeText(this, files[i].toString(), Toast.LENGTH_LONG).show();

                i++;

                if (f.isDirectory() && f.exists()) {

                    Date lastModDate = new Date(f.lastModified());

                    if (lastModDate.before(ThreeDaysAgo.getTime())) {

                        Toast.makeText(this, "4", Toast.LENGTH_LONG).show();

                        //f.delete();
                    }


                } else {
                    Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
                }

            }*/


        } catch (Exception e) {
            Toast.makeText(this, "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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