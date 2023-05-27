package com.example.appmongodb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.Adapters.Product;
import com.example.appmongodb.Adapters.ProductsAdapter;

import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SpecialOffersActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI;

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    ProductsAdapter adapter;
    TextView toHome;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers);

        toHome = (TextView) findViewById(R.id.spOffersToHome);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        Retrofit retrofit = RetrofitProduct.getInstance();
        myAPI = getAPI();

        recycler_search = (RecyclerView) findViewById(R.id.recycler_searchSp);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.setHasFixedSize(true);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        getAllPerson();
    }

    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
    }

    private void getAllPerson() {
        compositeDisposable.add(myAPI.getProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                               @Override
                               public void accept(List<Product> people) throws Exception {
                                   Iterator<Product> itr = people.iterator();
                                   while(itr.hasNext()){
                                       Product person = itr.next();
                                       if (person.isSpecial_offer() != true) {
                                           itr.remove();
                                       } else { }
                                   }
                                   adapter = new ProductsAdapter(people);
                                   recycler_search.setAdapter(adapter);
                               }

                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(SpecialOffersActivity.this, "Not found from All Products", Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }
}