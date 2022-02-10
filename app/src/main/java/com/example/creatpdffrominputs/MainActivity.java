package com.example.creatpdffrominputs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity {

    private Button btnCreatePdf;
    private Spinner spinnerItem1, spinnerItem2;
    private EditText txtCustomNam, txtPhoneNo, txtQu1, txtQu2;
    private static final int PERMISSION_REQUEST_CODE = 200;
    int pageWidth = 1200;
    Bitmap bitmap, scaleBitmap;
    Date dateObj;
    DateFormat dateFormat;

    float[] prices = new float[]{0,200,300,450,325,500};

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreatePdf = findViewById(R.id.btnCreatePdf);
        spinnerItem1 = findViewById(R.id.spinnerItem1);
        spinnerItem2 = findViewById(R.id.spinnerItem2);
        txtCustomNam = findViewById(R.id.txtCustomName);
        txtPhoneNo = findViewById(R.id.txtCustomPhone);
        txtQu1 = findViewById(R.id.editeQu1);
        txtQu2 = findViewById(R.id.editeQu2);
        button=findViewById(R.id.btnToActivty2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LineChartActivity.class));
            }
        });


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pizza);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }


        btnCreatePdf.setOnClickListener(view -> createPDF());

    }

    private void createPDF() {

        dateObj = new Date();
        if (txtCustomNam.getText().length() == 0 || txtPhoneNo.getText().length() == 0 || txtQu1.getText().length() == 0 || txtQu2.getText().length() == 0) {

            Toast.makeText(MainActivity.this, "Some Filed Empty", Toast.LENGTH_SHORT).show();
        } else {

            PdfDocument pdfDocument = new PdfDocument();
            Paint paint = new Paint();
            Paint titlePaint = new Paint();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();

            PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
            Canvas canvas = myPage.getCanvas();
            canvas.drawBitmap(scaleBitmap, 0, 0, paint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(70);
            titlePaint.setColor(Color.WHITE);
            canvas.drawText("Dimond Pizza", (pageWidth / 2+40), 270, titlePaint);


            paint.setColor(Color.rgb(30, 113, 188));
            paint.setTextSize(30f);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Call: 228-1569875", 1160, 40, paint);
            canvas.drawText("059558843", 1160, 80, paint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setColor(Color.WHITE);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canvas.drawText("InVoice", pageWidth / 2, 400, titlePaint);


            paint.setColor(Color.BLACK);
            paint.setTextSize(30f);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Custom Name: " + txtCustomNam.getText().toString(), 20, 590, paint);
            canvas.drawText("Contact NO. : " + txtPhoneNo.getText().toString(), 20, 640, paint);

            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Invoice No. :" + "256894", pageWidth - 20, 590, paint);

            dateFormat = new SimpleDateFormat("dd/MM/yy");
            canvas.drawText("Date :" + dateFormat.format(dateObj), pageWidth - 20, 640, paint);


            dateFormat = new SimpleDateFormat("HH:mm:ss");
            canvas.drawText("Time :" + dateFormat.format(dateObj), pageWidth - 20, 690, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(20,780,pageWidth-20,860,paint);


            paint.setTextAlign(Paint.Align.LEFT);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText("Si. No.",40,830,paint);
            canvas.drawText("Item Description",200,830,paint);
            canvas.drawText("Price",700,830,paint);
            canvas.drawText("Qty.",900,830,paint);
            canvas.drawText("Total.",1050,830,paint);

            canvas.drawLine(180,790,180,840,paint);
            canvas.drawLine(680,790,680,840,paint);
            canvas.drawLine(880,790,880,840,paint);
            canvas.drawLine(1030,790,1030,840,paint);


            float total =0, total2=0;
            if (spinnerItem1.getSelectedItemPosition()!=0){

                canvas.drawText("1.",40,950,paint);
                canvas.drawText(spinnerItem1.getSelectedItem().toString(),200,950,paint);
                canvas.drawText(String.valueOf(prices[spinnerItem1.getSelectedItemPosition()]),700,950,paint);
                canvas.drawText(txtQu1.getText().toString(),900,950,paint);
                total =Float.parseFloat(txtQu1.getText().toString())*prices[spinnerItem1.getSelectedItemPosition()];
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(total),pageWidth-40,950,paint);
                paint.setTextAlign(Paint.Align.LEFT);

            }

            if (spinnerItem2.getSelectedItemPosition()!=0){

                canvas.drawText("2.",40,1050,paint);
                canvas.drawText(spinnerItem2.getSelectedItem().toString(),200,1050,paint);
                canvas.drawText(String.valueOf(prices[spinnerItem2.getSelectedItemPosition()]),700,1050,paint);
                canvas.drawText(txtQu2.getText().toString(),900,1050,paint);
                total2 =Float.parseFloat(txtQu2.getText().toString())*prices[spinnerItem2.getSelectedItemPosition()];
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(total2),pageWidth-40,1050,paint);
                paint.setTextAlign(Paint.Align.LEFT);

            }

            float subTotal=total+total2;
            canvas.drawLine(680,1200,pageWidth-20,1200,paint);
            canvas.drawText("Sub total : ",700,1250,paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(subTotal),pageWidth-40,1250,paint);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Tax (12%) : ",700,1300,paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(subTotal*12/100),pageWidth-40,1300,paint);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.rgb(247,147,30));

            canvas.drawRect(600,1350,pageWidth-20,1450,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50f);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Total",700,1415,paint);
            paint.setTextAlign(Paint.Align.RIGHT);

            canvas.drawText(String.valueOf(subTotal+(subTotal*12/100)),pageWidth-40,1415,paint);




            pdfDocument.finishPage(myPage);

            File file = new File(Environment.getExternalStorageDirectory(), "/HindPDF.pdf");

            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
        }

    }


    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
