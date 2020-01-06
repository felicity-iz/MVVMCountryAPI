package com.test.countrylist;

import com.test.countrylist.Model.Country;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CountryViewModel extends ViewModel {
    private static Repository repo;

    public CountryViewModel() {
        repo = new Repository();
    }

    public LiveData<List<Country>> getCountriesLiveData(){
        return repo.getCountries();
    }

    public LiveData<List<Country>> getBorderCountriesLiveData(String bordersURL){
        return repo.getBorderCountries(bordersURL);
    }

}
