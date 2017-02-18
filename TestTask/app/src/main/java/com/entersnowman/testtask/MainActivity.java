package com.entersnowman.testtask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.entersnowman.testtask.models.ExchangeActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button fd_btn;
    Button sd_btn;
    TextView fd_tv;
    TextView sd_tv;
    TextView warning;
    public final static int DIALOG_FIRST_DATE = 1;
    public final static int DIALOG_SECOND_DATE = 2;
    Date first_date;
    Date second_date;
    Button getExchange;
    Intent intent;
    boolean isFirstDate;
    boolean isSecondDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,ExchangeActivity.class);
        isFirstDate = false;
        isSecondDate = false;
        warning = (TextView) findViewById(R.id.warning);
        fd_btn = (Button) findViewById(R.id.fd);
        sd_btn = (Button) findViewById(R.id.sd);
        fd_tv = (TextView) findViewById(R.id.fd_tv);
        sd_tv = (TextView) findViewById(R.id.sd_tv);
        getExchange = (Button) findViewById(R.id.getExchange) ;
        getExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getIntExtra("year1",1)>intent.getIntExtra("year2",1)||
                        (intent.getIntExtra("year1",1)==intent.getIntExtra("year2",1)&&intent.getIntExtra("month1",1)>intent.getIntExtra("month2",1))||
                        (intent.getIntExtra("year1",1)==intent.getIntExtra("year2",1)&&intent.getIntExtra("month1",1)==intent.getIntExtra("month2",1)&&intent.getIntExtra("day1",1)>intent.getIntExtra("day2",1)))
                warning.setVisibility(View.VISIBLE);
                else
                startActivity(intent);
            }
        });
        getExchange.setEnabled(false);
        fd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_FIRST_DATE);
            }
        });

        sd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_SECOND_DATE);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==DIALOG_FIRST_DATE){
            DatePickerDialog dialog = new DatePickerDialog(this, firstDateCallback, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            return dialog;
        }
        if (id==DIALOG_SECOND_DATE){
            DatePickerDialog dialog = new DatePickerDialog(this, secondDateCallBack, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            return dialog;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener firstDateCallback = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            System.out.println(monthOfYear+" "+dayOfMonth);
            intent.putExtra("year1",year);
            intent.putExtra("month1",monthOfYear);
            intent.putExtra("day1",dayOfMonth);
            fd_tv.setText(Integer.toString(dayOfMonth)+" "+new DateFormatSymbols().getMonths()[monthOfYear]+" "+Integer.toString(year));
            isFirstDate = true;
            checkDates();
            warning.setVisibility(View.INVISIBLE);
        }
    };

    DatePickerDialog.OnDateSetListener secondDateCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            intent.putExtra("year2",year);
            intent.putExtra("month2",monthOfYear);
            intent.putExtra("day2",dayOfMonth);
            sd_tv.setText(Integer.toString(dayOfMonth)+" "+new DateFormatSymbols().getMonths()[monthOfYear]+" "+Integer.toString(year));
            isSecondDate = true;
            checkDates();
            warning.setVisibility(View.INVISIBLE);
        }
    };

    public  void checkDates(){
        if (isFirstDate&&isSecondDate)
            getExchange.setEnabled(true);
    }
}
