package com.example.cryptocurrencyapi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptocurrencyService {

    @GET("{coin}-usd")
    Observable <Crypto> getCoin (@Path("coin") String coin);

}
