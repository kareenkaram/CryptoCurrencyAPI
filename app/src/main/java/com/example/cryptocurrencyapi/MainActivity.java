package com.example.cryptocurrencyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =  MainActivity.class.getSimpleName();
    final String BASE_URL = "http://api.cryptonator.com/api/full/";

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CryptocurrencyService cryptocurrencyService = retrofit.create(CryptocurrencyService.class);

        Observable<List<Crypto.Market>> btcObservable = cryptocurrencyService
                .getCoin("btc")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();

        Observable<List<Crypto.Market>> ethObservable = cryptocurrencyService.getCoin("eth")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "eth";
                    return true;
                }).toList().toObservable();

        Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(List<Crypto.Market> markets) {
        Log.d(TAG,"markets =" + markets);
        if (markets == null && markets.size() == 0 ){
            Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT)
                    .show();
        }else {
            recyclerViewAdapter.setData(markets);
            recyclerView.setAdapter(recyclerViewAdapter);

        }
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this,"Error in Fetching API Response. Try again",
                Toast.LENGTH_SHORT).show();
    }
}