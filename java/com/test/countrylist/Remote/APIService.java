package com.test.countrylist.Remote;

import com.test.countrylist.Model.Country;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {
    @GET("all")
    Call<List<Country>> getCountries();

    @GET
    Call<List<Country>> getBorderCountries(@Url String url);
}
