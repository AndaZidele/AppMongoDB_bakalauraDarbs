package com.example.appmongodb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.Adapters.Cart;
import com.example.appmongodb.Adapters.CartsAdapter;
import com.example.appmongodb.Adapters.User;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI;

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    CartsAdapter adapter;

    TextView totalCena, toHome;

    MaterialButton btnOrder;

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
        setContentView(R.layout.activity_cart);


        Retrofit retrofit = RetrofitProduct.getInstance();
        myAPI = getAPI();



        recycler_search = (RecyclerView) findViewById(R.id.productListInCart);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.setHasFixedSize(true);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));



//        float kopejaCena = 0;
//        String epa = PrefConfig.loadEpasts(this);
//        if (epa.equals("Not Logged In") == true){
//
//            //Snackbar mySnackbar = Snackbar;//.make(view, stringId, duration);
//
//            String nav = "To Add Product To Cart You Have To Login!";
////                        Snackbar.make(findViewById(R.id.myCoordinatorLayout), nav,
////                                        Snackbar.LENGTH_SHORT)
////                                .show();
//            Toast.makeText(OneProductActivity.this, nav, Toast.LENGTH_LONG).show();
//        } else {
        //getUserId();//prodId, 1, strName, pri, user_email);

       // getAllCart("10");
        getUserId();
        btnOrder = (MaterialButton) findViewById(R.id.btnMakeOrder);
        btnOrder.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

        toHome = (TextView) findViewById(R.id.toHomeFromCart);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

//        }

//        String email = "2";
//        String esosaLietotajaEpasts = "lll@l.com";
//       getUserId();//esosaLietotajaEpasts);


//        getAllCart(2);


    }

    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
    }

    public int useris = 0;

    private void getUserId(){//String user_email){
        String epa = PrefConfig.loadEpasts(this);
//        final int[] idUser = {0};
        compositeDisposable.add(myAPI.getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                               @Override
                               public void accept(List<User> people) throws Exception {
                                   String vajadzigaisEpasts = epa;
                                   Iterator<User> itr = people.iterator();
                                   int atgriezt = 0;
                                   while(itr.hasNext()){
                                       User person = itr.next();
                                       String listesProduktaKategorija = person.getEmail();

                                       if ((listesProduktaKategorija.equals(vajadzigaisEpasts)) != true) {
                                            itr.remove();
                                       } else {
//                                           Toast.makeText(CartActivity.this, person.getEmail() +  "   " + epa , Toast.LENGTH_SHORT).show();

                                           int userID = person.getId();
//                                           Toast.makeText(CartActivity.this, person.getId() +  "   " + userID , Toast.LENGTH_SHORT).show();

                                           atgriezt = userID;
//                                           idUser[0] = userID;
                                         //  return userID;
                                           //getAllCart(userID);
                                           useris = userID;
                                           getC();//(userID);
                                       }
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
//                                   Toast.makeText(CartActivity.this, "Not found from All User" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
//        return isUser[0];
    }

    private void getAllCart(int thisUserId){
        String epa = PrefConfig.loadEpasts(this);


        compositeDisposable.add(myAPI.getUserCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cart>>() {

                               @Override
                               public void accept(List<Cart> people) throws Exception {
                                   float cenaVienam, cenaKopa = 0;
                                   Iterator<Cart> itr = people.iterator();

//                                   Toast.makeText(CartActivity.this, thisUserId , Toast.LENGTH_SHORT).show();
/*
                                   while(itr.hasNext()){
                                       cenaVienam = 0;
                                       Cart person = itr.next();
                                       int produktaUsers = person.getUsers_id();
                                       int th = thisUserId;
                                       if (produktaUsers != th) {
                                           itr.remove();
                                       } else {
                                           cenaVienam = person.getAmount() * person.getPrice();
                                       }
                                       cenaKopa = cenaKopa + cenaVienam;
                                   }

                                   totalCena = (TextView) findViewById(R.id.total_id);
                                   totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));
                                   */
                                   adapter = new CartsAdapter(people, epa);
                                   recycler_search.setAdapter(adapter);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
//                                   Toast.makeText(CartActivity.this, "Not found from All Cart" + thisUserId, Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }


    private void getC(){
        String epa = PrefConfig.loadEpasts(this);
//        final int[] idUser = {0};

        compositeDisposable.add(myAPI.getUserCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cart>>() {
                               @Override
                               public void accept(List<Cart> people) throws Exception {

                                  Iterator<Cart> itr = people.iterator();
                                   int us = useris;

//                                   Toast.makeText(CartActivity.this, useris , Toast.LENGTH_SHORT).show();
                                   float cenaVienam, cenaKopa = 0;
                                   while(itr.hasNext()){
                                       cenaVienam = 0;
                                       Cart person = itr.next();
                                       int produktaUsers = person.getUsers_id();
                                       int th = us;
                                       if (produktaUsers != th) {
                                           itr.remove();
                                       } else {
                                           cenaVienam = person.getAmount() * person.getPrice();
                                       }
                                       cenaKopa = cenaKopa + cenaVienam;
                                   }

                                   totalCena = (TextView) findViewById(R.id.total_id);
                                   totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));

//                                   String vajadzigaisEpasts = epa;
//                                   Iterator<Cart> itr = people.iterator();
//                                   int atgriezt = 0;

                                   adapter = new CartsAdapter(people, epa);
                                   recycler_search.setAdapter(adapter);


                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
//                                   Toast.makeText(CartActivity.this, "Not found from All User" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }
}