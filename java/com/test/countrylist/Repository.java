package com.test.countrylist;

import android.util.Log;
import com.test.countrylist.Model.Country;
import com.test.countrylist.Remote.APIClient;
import com.test.countrylist.Remote.APIService;
import java.util.List;
import androidx.lifecycle.MutableLiveData;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private APIService apiService;

    private MutableLiveData<List<Country>> countriesLD = new MutableLiveData<>();
    private MutableLiveData<List<Country>> borderCountriesLD = new MutableLiveData<>();

    public Repository() {
        apiService = APIClient.getAPIService();
    }

    public MutableLiveData<List<Country>> getBorderCountries(String bordersURL){

        Call<List<Country>> getBorderCountriesCall = apiService.getBorderCountries(bordersURL);

        getBorderCountriesCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                HttpUrl url = response.raw().request().url();
                Log.d("url",url.toString());
                if(!response.isSuccessful()){
                    Log.d("repo-negresponse",response.message());
                    return;
                }
                borderCountriesLD.setValue(response.body());

                /*List<Country> borderList = response.body();
                for(Country country: borderList){
                    Log.d("country: ", country.getName());
                }*/
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("repo-failure",t.toString());
            }
        });
        return borderCountriesLD;
    }


    public MutableLiveData<List<Country>> getCountries(){

        Call<List<Country>> getCountriesCall = apiService.getCountries();

        getCountriesCall.enqueue(new Callback<List<Country>>() {

            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(!response.isSuccessful()){
                    Log.d("repo-negresponse",response.message());
                    return;
                }

                countriesLD.setValue(response.body());
                //List<Country> countryList = response.body();
                /*for(Country country: countryList){
                    Log.d("country: ", country.getName());
                }*/
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("repo-failure",t.toString());
            }
        });
        return countriesLD;


    }



}
