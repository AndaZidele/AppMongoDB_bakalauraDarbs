package com.example.appmongodb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.Adapters.User;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SettingsActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI;

    TextView name, email, phone, pass, address, toHome;
    Button btnN, btnE, btnPh, btnPass, btnA;
    MaterialButton delUser;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toHome = (TextView) findViewById(R.id.toMenuFromSetting);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        name = (TextView) findViewById(R.id.settingsName);
        email = (TextView) findViewById(R.id.settingsEmail);
        phone = (TextView) findViewById(R.id.settingsPhone);
        pass = (TextView) findViewById(R.id.settingsPass);
        address = (TextView) findViewById(R.id.settingsAddress);

//        String epa = PrefConfig.loadEpasts(this);
        Retrofit retrofit = RetrofitProduct.getInstance();
        myAPI = getAPI();//retrofit.create(UserAPI.class);


        getUsersData();

        delUser = (MaterialButton) findViewById(R.id.deleteAccount);

        delUser.setOnClickListener(view -> {
            getUserId();
        });



    }
    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
    }

    private void deleteUser(String user){
//            intent.putExtra("user_email",  user_email);
//            intent.putExtra("thisUsersEmail",  epastins);
//        String epa = PrefConfig.loadEpasts(this);
//        String email = String.valueOf(epa);
        compositeDisposable.add(myAPI.deleteUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Toast.makeText(CartsAdapter.this, ""+s, Toast.LENGTH_SHORT).show();

                    }
                }));

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PrefConfig.saveUserEmail(getApplicationContext(), "Not Logged In");
        startActivity(intent);


    }
    private void getUserId(){//String user_email){
        String epa = PrefConfig.loadEpasts(this);
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

                                           deleteUser(String.valueOf(userID));
                                           atgriezt = userID;
//                                           idUser[0] = userID;
                                           //  return userID;
                                           //getAllCart(userID);
//                                           useris = userID;
//                                           getC();//(userID);
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


    private void getUsersData(){
        String epa = PrefConfig.loadEpasts(this);

//        Intent oneProduct = getIntent();
//        String user_email = oneProduct.getStringExtra("user_email");// lietotaja epasts
//        String strName = oneProduct.getStringExtra("oneName");
//        String strPrice = oneProduct.getStringExtra("onePrice");
//        String strImage = oneProduct.getStringExtra("oneImage");
//        String strDescr = oneProduct.getStringExtra("oneDescr");
//        int strId = oneProduct.getIntExtra("oneId", 0);

//        int prodId = strId;
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

//                                           int userID = person.getId();
//                                           float priceFl = Float.parseFloat(strPrice);
//                                           addToCart(prod_id, userID, amount, name, price);
                                           Toast.makeText(SettingsActivity.this, "Te Esam", Toast.LENGTH_SHORT).show();
//
                                           name.setText("Full Name: " + person.getName());
                                           email.setText("Email: " +person.getEmail());
                                           phone.setText("Phone: " +person.getPhone());
                                           pass.setText("Password: " +person.getPassword());
                                           address.setText("Address: " +person.getAddress());
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
                                   Toast.makeText(SettingsActivity.this, "Not found from All Products" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }

}