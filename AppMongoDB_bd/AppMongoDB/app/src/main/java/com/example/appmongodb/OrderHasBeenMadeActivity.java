package com.example.appmongodb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.RetrofitUser;
import com.example.appmongodb.API.UserAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderHasBeenMadeActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI, myAPIforRegister;

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
        setContentView(R.layout.activity_order_has_been_made);

        Retrofit retrofit = RetrofitProduct.getInstance();
        myAPI = getAPI();

        Retrofit retrofitUser = RetrofitUser.getInstance();
        myAPIforRegister = retrofitUser.create(UserAPI.class);


        Intent inten = getIntent();
        int user_id = inten.getIntExtra("forOrderId", 0);

        String produktuVirkne = inten.getStringExtra("forOrderVi");
        String cenina = inten.getStringExtra("forOrderCe");


        Toast.makeText(OrderHasBeenMadeActivity.this, "Hi From Orders", Toast.LENGTH_SHORT).show();
        makeOrder(user_id, produktuVirkne, cenina);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
    }

    private void makeOrder(int user_id, String prod_ids, String cena){
        Intent inte = getIntent();
        String email = inte.getStringExtra("forOrderEma");
        String phone = inte.getStringExtra("forOrderPh");
        String address = inte.getStringExtra("forOrderA");
        String name = inte.getStringExtra("forOrderName");
        String prod_names = inte.getStringExtra("forOrderProd_names");


//        productsNames = (TextView) findViewById(R.id.checkout_txt_name);
//        totalCena = (TextView) findViewById(R.id.checkout_txt_price);
//        String prod_names = String.valueOf(productsNames.getText());
//        Float price = Float.parseFloat(cena);
        String userId = String.valueOf(user_id);

        compositeDisposable.add(myAPIforRegister.registerOrder(userId, name, email, phone, address, prod_names, prod_ids, cena)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(OrderHasBeenMadeActivity.this, ""+response, Toast.LENGTH_SHORT).show();


                        //sendUserToNextView();

                    }
                }));

    }

    private void sendUserToNextView() {
        Intent intent = new Intent(OrderHasBeenMadeActivity.this, MainActivity.class);
//        intent.putExtra("thisUsersEmail",  epastins);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}