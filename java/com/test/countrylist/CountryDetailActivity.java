package com.test.countrylist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.test.countrylist.Adapter.CountryAdapter;
import com.test.countrylist.Model.Country;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountryDetailActivity extends AppCompatActivity {
    TextView countryNameView;
    TextView nativeNameView;
    private CountryViewModel countryViewModel;
    private CountryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.country_detail);

        Intent i=getIntent();

        final String countryName=i.getExtras().getString("CountryName");
        final String nativeName=i.getExtras().getString("NativeName");
        final ArrayList<String> borderList = i.getStringArrayListExtra("Borders");

        String codelist = "";

        for (String s : borderList)
        {
            codelist += s + ";";
        }

        String bordersURL = "alpha?codes="+codelist;

        recyclerView = findViewById(R.id.recyclerView);
        countryNameView = findViewById(R.id.countryName);
        nativeNameView = findViewById(R.id.nativeName);

        countryNameView.setText(countryName);
        nativeNameView.setText(nativeName);

        if(borderList.size() != 0) {

            countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);

            countryViewModel.getBorderCountriesLiveData(bordersURL).observe(this, new Observer<List<Country>>() {

                @Override
                public void onChanged(@Nullable List<Country> countries) {
                    adapter = new CountryAdapter(getApplicationContext(), countries);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });

        }

    }
}
