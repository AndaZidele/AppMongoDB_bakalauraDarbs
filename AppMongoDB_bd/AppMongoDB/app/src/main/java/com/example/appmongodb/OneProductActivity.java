package com.example.appmongodb;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.RetrofitUser;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.Adapters.Cart;
import com.example.appmongodb.Adapters.CartsAdapter;
import com.example.appmongodb.Adapters.Product;
import com.example.appmongodb.Adapters.ProductsAdapter;
import com.example.appmongodb.Adapters.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OneProductActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI, myAPIforAddUpdate;

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
//    CartsAdapter adapter;

    ProductsAdapter adapter;


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    TextView description, price, name, toHome;
    ImageView image;
    MaterialButton addTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_product);

        toHome = (TextView) findViewById(R.id.oneProductToHome);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        String epa = PrefConfig.loadEpasts(this);

        Retrofit retrofitForAddAndUpdate = RetrofitUser.getInstance();
        myAPIforAddUpdate = retrofitForAddAndUpdate.create(UserAPI.class);

        Retrofit retrofit = RetrofitProduct.getInstance();
        myAPI = getAPI();//retrofit.create(UserAPI.class);

        name = (TextView) findViewById(R.id.productName);
        price = (TextView) findViewById(R.id.productPrice);
        image = (ImageView) findViewById(R.id.productImage);
        description = (TextView) findViewById(R.id.productDescription);
        addTo = (MaterialButton) findViewById(R.id.btnAddToCart);
        //lietotājam bus jauns pasutijums ar id, kam bus produktu saraksts ar to id

        Intent oneProduct = getIntent();
//        String user_email = oneProduct.getStringExtra("user_email");// lietotaja epasts
        String strName = oneProduct.getStringExtra("oneName");
        String strPrice = oneProduct.getStringExtra("onePrice");
//        String strImage = oneProduct.getStringExtra("oneImage");
        String strDescr = oneProduct.getStringExtra("oneDescr");
        int strId = oneProduct.getIntExtra("oneId", 0);


//        if (strName!=null && strId!=0 && strPrice!=null && strDescr!=null) { //&& strImage!=null
            name.setText(strName);
            price.setText(strPrice + " EUR");
//            image.setImageResource(Integer.parseInt(strImage));
            description.setText(strDescr);

//        String user = "3"; //Te japarliecinas vai lietotajs ir ielogojies un japanjem vinja id
//        String product_id = strId.toString();


            //1) sakuma dabut id produktam
            int prodId = strId;//Integer.parseInt(strId);

            //2) tad id lietotajam
            //

            float pri = Float.parseFloat(strPrice);

            //3) tad ka register pievienot db cart
            String user_email = "lll@l.com";//so te dabut no ielogosanas

//            getUsersId();//prodId, 1, strName, pri, user_email);


//        }

            addTo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

//                    Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//                intent.putExtra("user_email",  userLE.getText().toString());
//                startActivity(intent);

                    if (epa.equals("Not Logged In") == true){

                        //Snackbar mySnackbar = Snackbar;//.make(view, stringId, duration);

                        String nav = "To Add Product To Cart You Have To Login!";
//                        Snackbar.make(findViewById(R.id.myCoordinatorLayout), nav,
//                                        Snackbar.LENGTH_SHORT)
//                                .show();
                        Toast.makeText(OneProductActivity.this, nav, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OneProductActivity.this, "Product Has Been Added to Cart", Toast.LENGTH_LONG).show();

                        getUsersId();//prodId, 1, strName, pri, user_email);

                    }
//                    getAllPerson();
                }
            });






    }

    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
    }

    private void getUsersId(){
        String epa = PrefConfig.loadEpasts(this);

        Intent oneProduct = getIntent();
//        String user_email = oneProduct.getStringExtra("user_email");// lietotaja epasts
        String strName = oneProduct.getStringExtra("oneName");
        String strPrice = oneProduct.getStringExtra("onePrice");
//        String strImage = oneProduct.getStringExtra("oneImage");
        String strDescr = oneProduct.getStringExtra("oneDescr");
        int strId = oneProduct.getIntExtra("oneId", 0);

        int prodId = strId;
//        String user_email = epa;

        //)int prod_id,int amount, String name, float price, String user_email)
        compositeDisposable.add(myAPI.getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> people) throws Exception {
                                   String vajadzigaisEpasts = epa;//"lll@l.com";//user_email;////so te dabut no ielogosanas
                                   Iterator<User> itr = people.iterator();
                                   while(itr.hasNext()){

//                                       cenaVienam = 0;
                                       User person = itr.next();

                                       String listesProduktaKategorija = person.getEmail();
                                       if ((listesProduktaKategorija.equals(vajadzigaisEpasts)) != true) {
                                           itr.remove();
                                       } else {

                                           int userID = person.getId();
                                           float priceFl = Float.parseFloat(strPrice);
//                                           addToCart(prod_id, userID, amount, name, price);
//                                           Toast.makeText(OneProductActivity.this, prodId  +" "+ userID  +" "+ 1  +" "+strName +" "+ priceFl, Toast.LENGTH_SHORT).show();
                                           viewCart(prodId, userID, 1, strName, priceFl);

//                                           addToCart(int id, int users_id, int amount, String name, float price){
                                            /*   compositeDisposable.add(myAPIforAddUpdate.addToCart(prodId, userID, 1, strName, priceFl)
                                                       .subscribeOn(Schedulers.io())
                                                       .observeOn(AndroidSchedulers.mainThread())
                                                       .subscribe(new Consumer<List<Cart>>() {
                                                                      @Override
                                                                      public void accept(List<Cart> cartList) throws Exception {
                                                                          Toast.makeText(OneProductActivity.this, "Product is Successfully Added to Cart!", Toast.LENGTH_SHORT).show();

                                                                      }
                                                                  }
                                                       ));*/
//                                           };
//                                           cenaVienam = person.getAmount() * person.getPrice();
                                       }
//                                       Toast.makeText(OneProductActivity.this, "Te!!!", Toast.LENGTH_SHORT).show();


//                                       cenaKopa = cenaKopa + cenaVienam;
                                   }

//                                   adapter = new ProductsAdapter(people);
//                                   recycler_search.setAdapter(adapter);

//                                   int produkta_id = people.get(2).getId();
//                                   Toast.makeText(ProductsActivity.this, "TE: " + produkta_id, Toast.LENGTH_SHORT).show();



                               }


                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(OneProductActivity.this, "Not found from All Products" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }

    private void viewCart(int id, int users_id, int amount, String name, float price){

        compositeDisposable.add(myAPI.getUserCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cart>>() {
                               @Override
                               public void accept(List<Cart> people) throws Exception {

                                   Iterator<Cart> itr = people.iterator();
//                                   int us = useris;
                                   if (itr.hasNext() != true){
                                           addToCart(id, users_id, 1, name, price);
//                                           Log.d(TAG, "Pieveinojam jaunu produktu");

                                   }

//                                   Toast.makeText(CartActivity.this, useris , Toast.LENGTH_SHORT).show();
                                   float cenaVienam, cenaKopa = 0;
                                   while(itr.hasNext()){
                                       cenaVienam = 0;
                                       int zed = 0;
                                       Cart person = itr.next();
                                       int produktaUsers = person.getUsers_id();
                                       int prodId = person.getId();
                                       int am = person.getAmount() + 1;
//                                       int th = us;
                                       if (produktaUsers != users_id) {
                                           itr.remove();
                                       } else {
                                           if (prodId == id){
                                               //update
                                               zed = zed + 1;
                                               incProduct(String.valueOf(prodId), String.valueOf(users_id), String.valueOf(am));
                                           } else {
                                               //add
                                              // addToCart(prodId, users_id, 1, name, price);

                                           }

                                           //cenaVienam = person.getAmount() * person.getPrice();
                                       }
                                       if (itr.hasNext()==false && zed==0){
                                           addToCart(id, users_id, 1, name, price);
//                                       Log.d(TAG, "Pieveinojam jaunu produktu");
                                       }
                                      // cenaKopa = cenaKopa + cenaVienam;
                                   }



                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
//                                   Toast.makeText(CartActivity.this, "Not found from All User" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));

    }

    private void incProduct(String product, String user, String amount){
        compositeDisposable.add(myAPI.incProduct(product, user, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Toast.makeText(CartsAdapter.this, ""+s, Toast.LENGTH_SHORT).show();


                    }
                }));
//        Intent intent = new Intent(getApplicationContext(), DoneActivity.class);
//        startActivity(intent);


    }


    private void addToCart(int id, int users_id, int amount, String name, float price){
//        Toast.makeText(OneProductActivity.this, "Esam seit" + id + " "+ price, Toast.LENGTH_SHORT).show();

        String pId = String.valueOf(id);
        String uId = String.valueOf(users_id);
        String am = String.valueOf(amount);
        String pr = String.valueOf(price);
        compositeDisposable.add(myAPIforAddUpdate.addToCart(pId, uId, am, name, pr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
//                        Toast.makeText(OneProductActivity.this, "Esam Te!!!", Toast.LENGTH_SHORT).show();


                        //ja izdodas:
//                        sendUserToNextView();

                    }
                }));
//        Intent intent = new Intent(getApplicationContext(), DoneActivity.class);
//        startActivity(intent);

//        compositeDisposable.add(myAPI.registerUser(epasts,vards,parole,telefons)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String response) throws Exception {
//                            Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
//
//                            //ja izdodas:
//                            sendUserToNextView();
//
//                        }
//                    }));
//                ));
    }


    private void getAllPerson() {
//        Toast.makeText(ProductsActivity.this, "Ieejam seit", Toast.LENGTH_SHORT).show();

        Intent category = getIntent();
        String kategorija = category.getStringExtra("categoryName");


        compositeDisposable.add(myAPI.getProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                               @Override
                               public void accept(List<Product> people) throws Exception {

//                                   Toast.makeText(ProductsActivity.this, ""+ people.get(109).getCategory(), Toast.LENGTH_SHORT).show();
                                   //dabut no iepriekseja kategoriju skata ar extra mainigo:
                                   String vajadzigaKategorija = kategorija;//"men";//Integer.parseInt(email);

//                                   float cenaVienam, cenaKopa = 0;

                                   Iterator<Product> itr = people.iterator();
                                   while(itr.hasNext()){

//                                       cenaVienam = 0;
                                       Product person = itr.next();

                                       String listesProduktaKategorija = person.getCategory();
                                       if ((listesProduktaKategorija.equals(vajadzigaKategorija)) != true) {
                                           itr.remove();
                                       } else {
//                                           cenaVienam = person.getAmount() * person.getPrice();
                                       }

//                                       cenaKopa = cenaKopa + cenaVienam;
                                   }


//                                   totalCena = (TextView) findViewById(R.id.total_id);
//                                   totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));



                                //   adapter = new ProductsAdapter(people);
                               //    recycler_search.setAdapter(adapter);

//                                   int produkta_id = people.get(2).getId();
//                                   Toast.makeText(OneProductActivity.this, "TE: ", Toast.LENGTH_SHORT).show();



                               }


                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
//                                   Toast.makeText(OneProductActivity.this, "Not found from All Products", Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }
}