package com.example.appmongodb.Adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmongodb.API.RetrofitProduct;
import com.example.appmongodb.API.RetrofitUser;
import com.example.appmongodb.API.UserAPI;
import com.example.appmongodb.CartActivity;
import com.example.appmongodb.OneProductActivity;
import com.example.appmongodb.PrefConfig;
import com.example.appmongodb.R;
import com.google.android.material.button.MaterialButton;

import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.MyViewHolderCart>{

    List<Cart> cartList;
    String epa;


    public CartsAdapter(List<Cart> cartList, String epa) {this.cartList = cartList; this.epa = epa;}

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UserAPI myAPI, myAPIget;

    @NonNull
    @Override
    public MyViewHolderCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_table_view, viewGroup, false);

        return new MyViewHolderCart(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCart myViewHolder, int i) {

        myViewHolder.name.setText(cartList.get(i).getName());

        myViewHolder.price.setText((cartList.get(i).getPrice()).toString());
        myViewHolder.amount.setText(String.valueOf(cartList.get(i).getAmount()));

        String prod_id = String.valueOf(cartList.get(i).getId());
        String user_id = String.valueOf(cartList.get(i).getUsers_id());
        String amount = String.valueOf(cartList.get(i).getAmount());

        myViewHolder.btnPlus.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);
            //funkcija kur skaitu palielina pa 1
            int am = (Integer.parseInt(amount)) + 1;
            incProduct(prod_id, user_id, String.valueOf(am));
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));

        myViewHolder.btnMinus.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);
            if (Integer.parseInt(amount)==1){
                deleteProduct(prod_id, user_id);
            } else {
                int am = Integer.parseInt(amount) - 1;
                decProduct(prod_id, user_id, String.valueOf(am));
            }
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));

        myViewHolder.btnDel.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);

            deleteProduct(prod_id, user_id);

            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));

//        myViewHolder.btnDelete.setOnClickListener((v->{
//            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), DeleteActivity.class);
//            String iToS = Integer.toString(productList.get(i).getId());
//            cardViewsActivity.putExtra("id", iToS);
//            cardViewsActivity.putExtra("name",  productList.get(i).getName());
//            myViewHolder.name.getContext().startActivity(cardViewsActivity);
//        }));



    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class MyViewHolderCart extends RecyclerView.ViewHolder {
        CardView c_root_view;
        TextView name, price, amount; //vel pievienot visus
        MaterialButton btnPlus, btnMinus, btnDel;
        //
        public MyViewHolderCart(@NonNull View itemView) {
            super(itemView);


            c_root_view = (CardView) itemView.findViewById(R.id.cart_root_view);
            name = (TextView) itemView.findViewById(R.id.cart_product_name);
            price = (TextView) itemView.findViewById(R.id.cart_product_price);
            amount = (TextView) itemView.findViewById(R.id.cart_product_amount);

            btnMinus = (MaterialButton) itemView.findViewById(R.id.btn_minuss);
            btnPlus = (MaterialButton) itemView.findViewById(R.id.btn_plus);
            btnDel = (MaterialButton) itemView.findViewById(R.id.btn_delete_product);

            Retrofit retrofitUser = RetrofitUser.getInstance();
            myAPI = retrofitUser.create(UserAPI.class);

            Retrofit retrofit = RetrofitProduct.getInstance();
            myAPIget = getAPI();//retrofit.create(UserAPI.class);
        }
    }

    private UserAPI getAPI() {
        return RetrofitProduct.getInstance().create(UserAPI.class);
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


    }

    private void decProduct(String product, String user, String amount){
        compositeDisposable.add(myAPI.decProduct(product, user, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Toast.makeText(CartsAdapter.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                }));


    }

    private void deleteProduct(String product, String user){
        compositeDisposable.add(myAPI.deleteProduct(product, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Toast.makeText(CartsAdapter.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                }));



    }


    private void getUsersIdInc(String product){

        compositeDisposable.add(myAPIget.getUserList()
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
                                           String uId = String.valueOf(userID);
//                                           float priceFl = Float.parseFloat(strPrice);
////                                           addToCart(prod_id, userID, amount, name, price);
//                                           Toast.makeText(OneProductActivity.this, prodId  +" "+ userID  +" "+ 1  +" "+strName +" "+ priceFl, Toast.LENGTH_SHORT).show();
//                                           addToCart(prodId, userID, 1, strName, priceFl);
                                            incProduct(product, uId, "");
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
//                                   Toast.makeText(CartActivity.this, "Not found from All Products" , Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }



    public void deleteItem(int position){
        this.cartList.remove(position);
        notifyItemRemoved(position);
    }

}
