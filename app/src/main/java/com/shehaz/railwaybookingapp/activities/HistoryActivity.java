package com.shehaz.railwaybookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.shehaz.railwaybookingapp.R;
import com.shehaz.railwaybookingapp.utils.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private Context context = this;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SOURCE";
    public static final String COL_3 = "DESTINATION";
    public static final String COL_4 = "QUANTITY";
    public static final String COL_5 = "DATE";
    public static final String COL_6 = "TRAIN_NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Create DatabaseHelper instance
        DatabaseHelper dataHelper=new DatabaseHelper(this);
        // Reference to TableLayout
        TableLayout tableLayout=findViewById(R.id.bookingTable);
        tableLayout.setColumnStretchable(0, true);
        tableLayout.setColumnStretchable(1, true);
        tableLayout.setColumnStretchable(2, true);
        tableLayout.setColumnStretchable(3, true);
        tableLayout.setColumnStretchable(4, true);
        tableLayout.setColumnStretchable(5, true);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Sl No","Source","Destination","Date","Quantity","Train No"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(10);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add then to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            Cursor cursor = dataHelper.getAllData();
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    int outlet_id= cursor.getInt(cursor.getColumnIndex(COL_1));
                    String source= cursor.getString(cursor.getColumnIndex(COL_2));
                    String destination= cursor.getString(cursor.getColumnIndex(COL_3));
                    String date= cursor.getString(cursor.getColumnIndex(COL_4));
                    String quantity= cursor.getString(cursor.getColumnIndex(COL_5));
                    String number= cursor.getString(cursor.getColumnIndex(COL_6));


                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={outlet_id+"",source,destination,date,quantity,number};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
}