package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity
        extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener,
            EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private City selectedCity;

    @Override
    public void addCity(City city) {

        // add city to data
        cityAdapter.add(city);

        // update adapter
        cityAdapter.notifyDataSetChanged();
    }

    public void editCity(City city, String newName, String newProvince) {

        // set new info
        city.setName(newName);
        city.setProvince(newProvince);

        // update adapter
        cityAdapter.notifyDataSetChanged();
    }

    public void launchEditFrag() {
        // call newInstance to create fragment then show it
        EditCityFragment editFragment = EditCityFragment.newInstance(selectedCity);
        editFragment.show(getSupportFragmentManager(), "Edit City");
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create string arrays
        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        // create new ArrayList<City> dataList from string arrays
        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // link cityList and set new adapter
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // implements floating action button that makes new fragment
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // set click listener for list item
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // get city instance clicked and create fragment
                selectedCity = (City) adapterView.getItemAtPosition(i);
                launchEditFrag();
            }
        });
    }
}