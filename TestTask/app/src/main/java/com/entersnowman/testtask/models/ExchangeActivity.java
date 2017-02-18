package com.entersnowman.testtask.models;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.entersnowman.testtask.BankExchangeAdapter;
import com.entersnowman.testtask.ExchangeTask;
import com.entersnowman.testtask.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExchangeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ExchangeTask exchangeTask;
    ProgressBar progressBar;
    private ApiTask apiTask;
    ArrayList<BankExchange> nbuList;
    ArrayList<BankExchange> privatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
        nbuList = new ArrayList<BankExchange>();
        privatList = new ArrayList<BankExchange>();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        exchangeTask = new ExchangeTask(this);
        exchangeTask.setSectionsPagerAdapter(mSectionsPagerAdapter);
        exchangeTask.setNbuList(nbuList);
        exchangeTask.setPrivatList(privatList);
        //((PlaceholderFragment)mSectionsPagerAdapter.getItem(0)).setExchanges(privatList);
        //((PlaceholderFragment)mSectionsPagerAdapter.getItem(1)).setExchanges(nbuList);
        apiTask = new ApiTask();
        apiTask.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exchange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ApiTask extends AsyncTask<Date,Void,Exchange>{


        @Override
        protected Exchange doInBackground(Date... params) {

            Calendar firstDate = Calendar.getInstance();
            firstDate.set(getIntent().getIntExtra("year1",2016),getIntent().getIntExtra("month1",10),getIntent().getIntExtra("day1",1));
            firstDate.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            firstDate.set(Calendar.MINUTE, 0);                 // set minute in hour
            firstDate.set(Calendar.SECOND, 0);                 // set second in minute
            firstDate.set(Calendar.MILLISECOND, 0);
            Calendar secondDate = Calendar.getInstance();
            secondDate.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            secondDate.set(Calendar.MINUTE, 0);                 // set minute in hour
            secondDate.set(Calendar.SECOND, 0);                 // set second in minute
            secondDate.set(Calendar.MILLISECOND, 0);
            secondDate.set(getIntent().getIntExtra("year2",2016),getIntent().getIntExtra("month2",10),getIntent().getIntExtra("day2",10));
            secondDate.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MMMM.yyyy");


            do{
                try {
                    exchangeTask.getExchangeByDate(firstDate.getTime());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(simpleDate.format(firstDate.getTime()));
                firstDate.add(Calendar.DAY_OF_MONTH, 1);
            }while (firstDate.before(secondDate));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Refresh lists");
                    mSectionsPagerAdapter.notifyAdapters();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

            return null;
        }
    }



    public static class PrivatFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private BankExchangeAdapter bankExchangeAdapter;
        private RecyclerView recyclerView;
        public PrivatFragment() {
            bankExchangeAdapter = new BankExchangeAdapter();
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PrivatFragment newInstance(int sectionNumber) {
            PrivatFragment fragment = new PrivatFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Log.d("d","fr = "+sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        public BankExchangeAdapter getBankExchangeAdapter() {
            return bankExchangeAdapter;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_exchange, container, false);
            recyclerView = (RecyclerView)rootView.findViewById(R.id.listOfExchanges);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            return rootView;
        }


    }


    public static class NbuFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private BankExchangeAdapter bankExchangeAdapter;
        private RecyclerView recyclerView;
        public NbuFragment() {
            bankExchangeAdapter = new BankExchangeAdapter();
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static NbuFragment newInstance(int sectionNumber) {
            NbuFragment fragment = new NbuFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Log.d("d","fr = "+sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        public BankExchangeAdapter getBankExchangeAdapter() {
            return bankExchangeAdapter;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_exchange, container, false);
            recyclerView = (RecyclerView)rootView.findViewById(R.id.listOfExchanges);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        PrivatFragment privatFragment;
        NbuFragment nbuFragment;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            privatFragment = new PrivatFragment();
            nbuFragment = new NbuFragment();
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println("FM getItem");
            switch (position) {
                case 0:
                    return privatFragment;
                case 1:
                    return nbuFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ПриватБанк";
                case 1:
                    return "НБУ";
            }
            return null;
        }

        public void notifyAdapters(){
            System.out.println(privatList.size()+" "+nbuList.size());
            privatFragment.getBankExchangeAdapter().setBankExchanges(privatList);
            privatFragment.recyclerView.setAdapter(privatFragment.getBankExchangeAdapter());
            privatFragment.getBankExchangeAdapter().notifyDataSetChanged();
            nbuFragment.getBankExchangeAdapter().setBankExchanges(nbuList);
            nbuFragment.recyclerView.setAdapter(nbuFragment.getBankExchangeAdapter());
            nbuFragment.getBankExchangeAdapter().notifyDataSetChanged();
        }
    }
}
