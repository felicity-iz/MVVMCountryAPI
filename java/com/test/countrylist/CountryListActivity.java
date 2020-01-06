package com.test.countrylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.countrylist.Adapter.CountryAdapter;
import com.test.countrylist.Model.Country;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountryListActivity extends AppCompatActivity {
    private CountryViewModel countryViewModel;
    private CountryAdapter adapter;
    private RecyclerView recyclerView;
    private Button sortAZButton;
    private Button sortAreaButton;
    private Button sortEqualityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countryViewModel.getCountriesLiveData().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {
                adapter = new CountryAdapter(getApplicationContext(),countries);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                sortAZButton = findViewById(R.id.sortAZ);
                sortAZButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country o1, Country o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        adapter.notifyDataSetChanged();


                    }
                });
                sortAreaButton = findViewById(R.id.sortArea);
                sortAreaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country o1, Country o2) {
                                return Float.compare(o2.getArea(), o1.getArea());
                            }
                        });
                        adapter.notifyDataSetChanged();

                    }
                });
                sortEqualityButton = findViewById(R.id.sortEquality);
                sortEqualityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(countries, new Comparator<Country>() {
                            @Override
                            public int compare(Country o1, Country o2) {
                                return Float.compare(o2.getGini(), o1.getGini());
                            }
                        });
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }
}

