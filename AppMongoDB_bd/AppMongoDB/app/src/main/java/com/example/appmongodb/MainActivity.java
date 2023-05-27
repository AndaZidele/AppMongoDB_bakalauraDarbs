package com.example.appmongodb;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmongodb.API.RetrofitUser;
import com.example.appmongodb.API.UserAPI;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.perf.FirebasePerformance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    MaterialButton toCart, toLog;
    TextView toMenu;
    Button toProd;

//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    UserAPI myAPI;
//
//    @Override
//    protected void onStop() {
//        compositeDisposable.clear();
//        super.onStop();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String epa = PrefConfig.loadEpasts(this);
        toCart = (MaterialButton) findViewById(R.id.mainToCart);
        toCart.setOnClickListener(view -> {
        if (epa.equals("Not Logged In") == true){

            String nav = "To See Cart You Have To Login!";
            Intent intent = new Intent(getApplicationContext(), HaventLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//            intent.putExtra("user_email",  user_email);
//            intent.putExtra("thisUsersEmail",  epastins);
            startActivity(intent);
        }
        });


        toLog = (MaterialButton) findViewById(R.id.mainToLogin);
        toLog.setOnClickListener(view -> {
            if (epa.equals("Not Logged In") == true){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            intent.putExtra("user_email",  user_email);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("thisUsersEmail",  epastins);
                startActivity(intent);
            }
//
//            Retrofit retrofitUser = RetrofitUser.getInstance();
//            myAPI = retrofitUser.create(UserAPI.class);
//
//            compositeDisposable.add(myAPI.registerUserProducts("To Store")
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String response) throws Exception {
//                            Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
////                            PrefConfig.saveUserEmail(getApplicationContext(), epasts);
//                            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//                            startActivity(intent);
//                        }
//                    }));

        });

        toMenu = (TextView) findViewById(R.id.toMenu);
        toMenu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//            intent.putExtra("thisUsersEmail",  epastins);
            startActivity(intent);
        });

        toProd = (Button) findViewById(R.id.mainToProducts);
        toProd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

    }
}