package com.shehaz.railwaybookingapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.shehaz.railwaybookingapp.R;
import com.shehaz.railwaybookingapp.models.Train;
import com.shehaz.railwaybookingapp.utils.DatabaseHelper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;


public class BookingActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
//    public static int VERSION = 1;
    Train chosenTrain;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    AwesomeValidation awesomeValidation;

    EditText source,destination,etDate,noOfTrav,trainNo;
    TextView showTrainList;
    DatabaseHelper myDb;
    Button book;
    LocalDate date1;
    String dayOfWeek;
    SQLiteDatabase database;
    Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        myDb = new DatabaseHelper(this);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        etDate = findViewById(R.id.date);
        noOfTrav = findViewById(R.id.noOfTravellers);
        trainNo = findViewById(R.id.trainNo);
        showTrainList = findViewById(R.id.showTrainList);
        book = findViewById(R.id.book);

        //for validating the form
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.source, "[A-Za-z]+",R.string.invalid_source);
        awesomeValidation.addValidation(this,R.id.destination, "[A-Za-z]+",R.string.invalid_destination);
        awesomeValidation.addValidation(this,R.id.noOfTravellers, "^[1-9]{1,}$",R.string.invalid_number_of_travellers);
        awesomeValidation.addValidation(this,R.id.date, RegexTemplate.NOT_EMPTY,R.string.invalid_date);
        awesomeValidation.addValidation(this,R.id.trainNo, "(1000[1-9])|(1001[0-9])|(1002[0-1])",R.string.invalid_train);

        //for date picker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
//                        DateTimeFormatter dayOfWeekFormatter
//                                = DateTimeFormatter.ofPattern("EEEE");
                        date1 = LocalDate.of(
                                view.getYear(), view.getMonth(), view.getDayOfMonth());
                        dayOfWeek = date1.getDayOfWeek().plus(3).name();
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });



        showTrainList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etDate.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Please Select A Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                else
                {
                    Intent int1 = new Intent(BookingActivity.this, TrainListActivity.class);
                    int1.putExtra("day", dayOfWeek);
                    startActivityForResult(int1,REQUEST_CODE);

                }

            }
        });
;
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate())
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                    builder.setTitle("Book Seat");
                    builder.setMessage("Do you want to confirm the booking ?");

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BookingActivity.this,"Confirmed",Toast.LENGTH_SHORT).show();

                            boolean isInserted = myDb.insertData(source.getText().toString(),
                                    destination.getText().toString(),
                                    etDate.getText().toString(),
                                    noOfTrav.getText().toString(),
                                    trainNo.getText().toString());

                            if(isInserted == true)
                            {
                                Toast.makeText(BookingActivity.this,"Booking Confirmed",Toast.LENGTH_LONG).show();
                                source.setText("");
                                destination.setText("");
                                etDate.setText("");
                                noOfTrav.setText("");
                                trainNo.setText("");
                            }

                            else
                                Toast.makeText(BookingActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BookingActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent listIntent) {
        super.onActivityResult(requestCode, resultCode, listIntent);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            chosenTrain = listIntent.getParcelableExtra("train");
            trainNo.setText(chosenTrain.getTrainNo());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            preferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);
            editor = preferences.edit();
            Context context = this;
            //deleting db when user logs out
            context.deleteDatabase("MyDB.db");
            editor.remove("Logged");
            editor.apply();
            Intent intentLogout = new Intent(BookingActivity.this, LoginActivity.class);
            intentLogout.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
            intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentLogout);
            finish();
        }

        if(item.getItemId() == R.id.history) {
            Intent intentHistory =  new Intent(BookingActivity.this, HistoryActivity.class);
            startActivity(intentHistory);
        }
        return super.onOptionsItemSelected(item);
    }
}