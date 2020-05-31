package com.example.corona;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import jxl.Workbook;

public class StatisticsActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView recyclerView;
    Adapter adapter;
    InputStream inputStream;
    AsyncHttpClient client;
    Workbook workbook;
    List<String> countries, state;
    List<Integer> confirmed, active, deaths, recovered;
    List<Double> lat, longi;

    List<String> countries1, state1;
    List<Integer> confirmed1, active1, deaths1, recovered1;
    List<Double> lat1, longi1;

    ProgressBar progressBar;
    ProgressBar progressBar1;
    String[] data;
    TextView tv_totConfirmedCases;
    TextView tv_totalDeaths;
    TextView tv_totalRecovered;
    private RadioGroup radioAll;
    private RadioButton radioCountry;
    private Button btnDisplay;

    List<List<String>> formattedRows = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListner);

        tv_totalRecovered = findViewById(R.id.tv_totalRecovered);
        tv_totConfirmedCases = findViewById(R.id.tv_totConfirmedCases);
        tv_totalDeaths = findViewById(R.id.tv_totalDeaths);
        //Commented by siddhesh
//        String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/04-26-2020.csv";
////        inputStream = getResources().openRawResource(R.raw.cleancorona);
////
        recyclerView = findViewById(R.id.listOfData);
        progressBar = findViewById(R.id.progressBar);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);

        state = new ArrayList<>();
        countries = new ArrayList<>();
        confirmed = new ArrayList<>();
        lat = new ArrayList<>();
        longi = new ArrayList<>();
        active = new ArrayList<>();
        deaths = new ArrayList<>();
        recovered = new ArrayList<>();

        state1 = new ArrayList<>();
        countries1 = new ArrayList<>();
        confirmed1 = new ArrayList<>();
        lat1 = new ArrayList<>();
        longi1 = new ArrayList<>();
        active1 = new ArrayList<>();
        deaths1 = new ArrayList<>();
        recovered1 = new ArrayList<>();
////
////        client = new AsyncHttpClient();
////        progressBar.setVisibility(View.VISIBLE);
////        client.get(url, new FileAsyncHttpResponseHandler(this) {
////            @Override
////            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
////                progressBar.setVisibility(View.GONE);
////                Toast.makeText(StatisticsActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onSuccess(int statusCode, Header[] headers, File file) {
////                Toast.makeText(StatisticsActivity.this, "Download Success", Toast.LENGTH_SHORT).show();
////                WorkbookSettings ws = new WorkbookSettings();
////                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
////                ws.setGCDisabled(true);
////                if (file != null) {
////                    try {
//////                        workbook = Workbook.getWorkbook(file);
//////                        Sheet sheet = workbook.getSheet(0);
//////                        for(int i=0; i<sheet.getRows(); i++){
//////                            Cell[] row = sheet.getRow(i);
//////                            state.add(row[2].getContents());
//////                            countries.add(row[3].getContents());
//////                            lat.add(Double.valueOf((row[5]).getContents()));
//////                            longi.add(Double.valueOf(row[6].getContents()));
//////                            confirmed.add(Integer.valueOf(row[7].getContents()));
//////                            deaths.add(Integer.valueOf(row[8].getContents()));
//////                            recovered.add(Integer.valueOf(row[9].getContents()));
//////                            active.add(Integer.valueOf(row[10].getContents()));
//////                            }
////                        String csvLine;
//////                        StringBuilder country1 = new StringBuilder();
//////                        StringBuilder confirmed1 = new StringBuilder();
//////                        StringBuilder active1 = new StringBuilder();
//////                        StringBuilder deaths1 = new StringBuilder();
//////                        StringBuilder recovered1 = new StringBuilder();
////
////                        while ((csvLine = reader.readLine()) != null) {
////                            data = csvLine.split(",");
////
////                            try {
////                                state.add(data[2]);
////                                countries.add(data[3]);
////                                lat.add(Double.valueOf(data[5]));
////                                confirmed.add(Integer.valueOf(data[7]));
////                                longi.add(Double.valueOf(data[6]));
////                                active.add(Integer.valueOf(data[10]));
////                                recovered.add(Integer.valueOf(data[9]));
////                                deaths.add(Integer.valueOf(data[8]));
////                            } catch (Exception e) {
////                                Log.e("Problem", e.toString());
////                            }
////                        }
////
////                        showData();
////
////                        Log.d(TAG, "On Success ----------------------------:" + countries);
////                        progressBar.setVisibility(View.GONE);
////
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//////                    catch (BiffException e) {
//////                        e.printStackTrace();
//////                    }
////                }
////            }
////        });
////
////
//////        try{
//////
//////        }
//////        catch(IOException ex){
//////            throw new RuntimeException("Error in CSV:"+ex);
//////        }
////    }
////
////    private void showData() {
////        adapter = new Adapter(this, countries, state, confirmed, active, deaths, recovered, lat, longi);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        recyclerView.setAdapter(adapter);


        String url = "";
        List<DataModel> dataModelList = new ArrayList<>();
        List<DataModel> dataModelListCountrywise = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String today = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(cal.getTime());
//        https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/04-30-2020.csv
//        https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/" + today + ".csv"

        if (dataExists("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/" + today + ".csv")) {
            url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/" + today + ".csv";
        } else {
            url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/" + yesterday + ".csv";
        }
        Log.d("TAG",url);

        Log.d("TAG","----------------------------------------------------------44444444444");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                int totConfirmedCases = 0;
                int totalDeaths = 0;
                int totalRecovered = 0;

                if (formattedRows != null) {
                    formattedRows.clear();
                }

                List<String> items = Arrays.asList(new String(response).split("\\r?\\n"));
                for (int i = 1; i < items.size(); i++) {
                    formattedRows.add(Arrays.asList(items.get(i).split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")));
                }

                ArrayList<String> countriesName = new ArrayList<>();
                List<String> indV = new ArrayList<>();
                for (int i = 0; i < formattedRows.size(); i++) {
                    indV = formattedRows.get(i);

                    countriesName.add(indV.get(3));


                    if(indV.get(3) != null && indV.get(3).length() > 0){
                        countries.add(indV.get(3));
                        countries1.add(indV.get(3));
                    }
                    else {
                        countries.add(null);
                        countries1.add(null);
                    }

                    if(indV.get(2) != null && indV.get(2).length() > 0){
                        state.add(indV.get(2));
                        state1.add(indV.get(2));
                    }
                    else{
                        state.add(null);
                        state1.add(null);
                    }

                    if(indV.get(5) != null && indV.get(5).length() > 0){
                        lat.add(Double.parseDouble(indV.get(5)));
                        lat1.add(Double.parseDouble(indV.get(5)));
                    }
                    else {
                        lat.add(0.00);
                        lat1.add(0.00);
                    }
                    if(indV.get(6) != null && indV.get(6).length() > 0){
                        longi.add(Double.parseDouble(indV.get(6)));
                        longi1.add(Double.parseDouble(indV.get(6)));
                    }
                    else {
                        longi.add(0.00);
                        longi1.add(0.00);
                    }
                    if(indV.get(7) != null && indV.get(7).length() > 0){
                        confirmed.add(Integer.parseInt(indV.get(7)));
                        confirmed1.add(Integer.parseInt(indV.get(7)));
                    }
                    else{
                        confirmed.add(0);
                        confirmed1.add(0);
                    }
                    if(indV.get(8) != null && indV.get(8).length() > 0){
                        deaths.add(Integer.parseInt(indV.get(8)));
                        deaths1.add(Integer.parseInt(indV.get(8)));
                    }
                    else{
                        deaths.add(0);
                        deaths1.add(0);
                    }
                    if(indV.get(9) != null && indV.get(9).length() > 0){
                        recovered.add(Integer.parseInt(indV.get(9)));
                        recovered1.add(Integer.parseInt(indV.get(9)));
                    }
                    else{
                        recovered.add(0);
                        recovered1.add(0);
                    }
                    if(indV.get(10) != null && indV.get(10).length() > 0){
                        active.add(Integer.parseInt(indV.get(10)));
                        active1.add(Integer.parseInt(indV.get(10)));
                    }
                    else{
                        active.add(0);
                        active1.add(0);
                    }

                    DataModel allData = new DataModel(indV.get(3), "as of " + indV.get(4), indV.get(7), indV.get(8), indV.get(9), indV.get(10));
                    dataModelList.add(allData);
//                    Log.d("TAG","---------------------"+dataModelList);
                    totConfirmedCases = totConfirmedCases + Integer.parseInt(indV.get(7));
//                    Log.d("TAG", String.valueOf(totConfirmedCases));
                    totalDeaths = totalDeaths + Integer.parseInt(indV.get(8));
                    totalRecovered = totalRecovered + Integer.parseInt(indV.get(9));
                }


                Map<String, List<DataModel>> map = dataModelList.stream().collect(Collectors.groupingBy(DataModel::getCountry));

                map.entrySet().stream().forEach(e -> {
                            int ccs = 0, deaths = 0, recovered = 0;
                            for (int i = 0; i < e.getValue().size(); i++) {
                                ccs += Integer.parseInt(e.getValue().get(i).getConfirmedCases());
                                deaths += Integer.parseInt(e.getValue().get(i).getTotalDeaths());
                                recovered += Integer.parseInt(e.getValue().get(i).getCasesRecovered());
                            }
                            DataModel allData = new DataModel(e.getKey(), "as of today", String.valueOf(ccs), String.valueOf(deaths), String.valueOf(recovered), "• All regions");
                            dataModelListCountrywise.add(allData);
                        }
                );

                Collections.sort(dataModelListCountrywise, new Comparator<DataModel>() {
                    public int compare(DataModel obj1, DataModel obj2) {
                        return Integer.valueOf(obj2.getConfirmedCases()).compareTo(Integer.valueOf(obj1.getConfirmedCases())); // To compare integer values
                    }
                });

                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(countriesName);
                countriesName.clear();
                countriesName.addAll(hashSet);

//                Log.d("TAG","-----------------------------------"+hashSet);

                radioAll = (RadioGroup) findViewById(R.id.radiogrp);
                btnDisplay = (Button) findViewById(R.id.btnFilter);

                btnDisplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar1.setVisibility(View.VISIBLE);
                        // get selected radio button from radioGroup
                        int selectedId = radioAll.getCheckedRadioButtonId();
                        //2131230954 2nd ID
                        //2131230953
                        // find the radiobutton by returned id
//                btnDisplay = (RadioButton) findViewById(selectedId);
                        if(selectedId == 2131230953)
                        {
                            showData();
                            progressBar1.setVisibility(View.GONE);
                        }
                        else
                        {
                            HashMap<String, Integer> countryPositive = new HashMap<>();
                            int sumPos = 0;
                            countries.clear();
                            confirmed.clear();
                            state.clear();
                            active.clear();
                            deaths.clear();
                            recovered.clear();
                            lat.clear();
                            longi.clear();
                            for(int i =0;i<countries1.size();i++)
                            {
                                if(!countryPositive.containsKey(countries1.get(i)))
                                {
                                    countryPositive.put(countries1.get(i), confirmed1.get(i));
                                }
                                else
                                {
                                    sumPos = confirmed1.get(i) + countryPositive.get(countries1.get(i));
                                    countryPositive.put(countries1.get(i), sumPos);
                                }
                            }
                            Set set3 = countryPositive.entrySet();
                            Iterator iterator3 = set3.iterator();
                            while(iterator3.hasNext()) {
                                Map.Entry mentry2 = (Map.Entry)iterator3.next();
//                                Log.d("TAG","Key is: "+mentry2.getKey() + " & Value is: "+mentry2.getValue());
                                countries.add(mentry2.getKey().toString());
                                confirmed.add((Integer) mentry2.getValue());
                                state.add(null);
                                longi.add(0.00);
                                lat.add(0.00);
//                                active.add(0);
                                deaths.add(0);
                                recovered.add(0);
                            }

                            HashMap<String, Integer> countryActive = new HashMap<>();
                            int sumPos1 = 0;
                            for(int i =0;i<countries1.size();i++)
                            {
                                if(!countryActive.containsKey(countries1.get(i)))
                                {
                                    countryActive.put(countries1.get(i), active1.get(i));
                                }
                                else
                                {
                                    sumPos1 = active1.get(i) + countryActive.get(countries1.get(i));
                                    countryActive.put(countries1.get(i), sumPos1);
                                }
                            }
                            Set set2 = countryActive.entrySet();
                            Iterator iterator2 = set2.iterator();
                            while(iterator2.hasNext()) {
                                Map.Entry mentry2 = (Map.Entry)iterator2.next();
//                                Log.d("TAG","Key is: "+mentry2.getKey() + " & Value is: "+mentry2.getValue());
//                                countries.add(mentry2.getKey().toString());
                                active.add((Integer) mentry2.getValue());
                            }

//
//                            HashMap<String, Integer> countryRecovered = new HashMap<>();
//                            int sumPos2 = 0;
//                            for(int i =0;i<countries1.size();i++)
//                            {
//                                if(!countryRecovered.containsKey(countries1.get(i)))
//                                {
//                                    countryRecovered.put(countries1.get(i), recovered1.get(i));
//                                }
//                                else
//                                {
//                                    sumPos2 = recovered1.get(i) + countryRecovered.get(countries1.get(i));
//                                    countryRecovered.put(countries1.get(i), sumPos2);
//                                }
//                            }
//                            Set set4 = countryRecovered.entrySet();
//                            Iterator iterator4 = set4.iterator();
//                            while(iterator4.hasNext()) {
//                                Map.Entry mentry4 = (Map.Entry)iterator4.next();
////                                Log.d("TAG","Key is: "+mentry2.getKey() + " & Value is: "+mentry2.getValue());
////                                countries.add(mentry4.getKey().toString());
//                                recovered.add((Integer) mentry4.getValue());
//                            }
//
////
//                            HashMap<String, Integer> countryDeaths = new HashMap<>();
//                            int sumPos3 = 0;
//                            for(int i =0;i<countries1.size();i++)
//                            {
//                                if(!countryDeaths.containsKey(countries1.get(i)))
//                                {
//                                    countryDeaths.put(countries1.get(i), deaths1.get(i));
//                                }
//                                else
//                                {
//                                    sumPos3 = deaths1.get(i) + countryDeaths.get(countries1.get(i));
//                                    countryDeaths.put(countries1.get(i), sumPos3);
//                                }
//                            }
//                            Set set5 = countryDeaths.entrySet();
//                            Iterator iterator5 = set5.iterator();
//                            while(iterator5.hasNext()) {
//                                Map.Entry mentry5 = (Map.Entry)iterator4.next();
////                                Log.d("TAG","Key is: "+mentry2.getKey() + " & Value is: "+mentry2.getValue());
////                                countries.add(mentry5.getKey().toString());
//                                deaths.add((Integer) mentry5.getValue());
//                            }
                            showData();

                        }
                        progressBar1.setVisibility(View.GONE);
//                Log.d("TAG","---------------"+btnDisplay.toString());
                    }

                });

                tv_totConfirmedCases.setText(String.valueOf(totConfirmedCases));
                tv_totalDeaths.setText(String.valueOf(totalDeaths));
                tv_totalRecovered.setText(String.valueOf(totalRecovered));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StatisticsActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

//        Log.d("TAG","----------------------------------------------------------5555555555");


    }

    private void showData() {
        adapter = new Adapter(this, countries, state, confirmed, active, deaths, recovered, lat, longi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerView.setAdapter(adapter);
    }

    private boolean dataExists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
//            Log.d("TAG",URLName);
//            Log.d("TAG","----------------------------------------------------------111111");
            HttpURLConnection con = (HttpURLConnection) new URL(URLName)
                    .openConnection();
//            Log.d("TAG","----------------------------------------------------------222222222");
            con.setRequestMethod("HEAD");
//            Log.d("TAG","----------------------------------------------------------233333333");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            break;
                        case R.id.exit:
                            finish();
                            System.exit(0);
                    }
                    return true;
                }
            };

}
