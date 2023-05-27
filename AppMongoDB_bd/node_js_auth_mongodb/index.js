//Import package
var mongodb = require('mongodb');
var ObjectID = mongodb.ObjectId;
var crypto = require('crypto');
var express = require('express');
var bodyParser = require('body-parser');
const { emit } = require('process');
const { lookup } = require('dns');
//const { request } = require('http');
//const { response } = require('express');
//const res = require('express/lib/response');

// var router = express.Router();
// var jsonParser = bodyParser.json();

//Password ultils
//Create function to random salt


    //seit pēc saltHash (3)
var genRandomString = function(length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')
        .slice(0,length);
};


    //seit pēc genRandom... (4)
var sha512 = function(password,salt) {
    var hash = crypto.createHmac('sha512',salt);
    hash.update(password); //.update(password); //SEIT
    var value = hash.digest('hex');
    return{
        salt:salt,
        passwordHash:value
    };
};


    //seit pēc register (2)
function saltHashPassword(userPassword) {
    var salt = genRandomString(16);
    var passwordData = sha512(userPassword,salt); //SEIT
    return passwordData;
}

function checkHashPassword(userPassword, salt){
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

//Create Express Service
var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));





var MongoClient = mongodb.MongoClient;

var url = 'mongodb://127.0.0.1:27017'

MongoClient.connect(url,{useNewUrlParser: true}, function(err,client){
    if(err)
        console.log('Unable to connect to the mongoDB server.Error', err);
    else{

        // //te sakt dzest!!!
        // app.get('/registers', (req,res,nexr)=>{
        //     var te = req.body.email;
            
        // });
        


        //te beigt dzest



        //Register
        //seit sākumā (1)
        app.post('/register', (request,response,next)=>{
            var post_data = request.body; 
            var plaint_password = post_data.password;
            var hash_data = saltHashPassword(plaint_password);//SEIT
            var password = hash_data.passwordHash;
            var salt = hash_data.salt;

            var name = post_data.name;
           // var email = "OTRAIS";//
            var email = post_data.email;
            var phone = post_data.phone;
            var address = "";           
            var db = client.db('AppDB');
            // var useruSkaits = 1010;
            var useruSkaits = 0;
            db.collection('user').count({}, function(error, numOfDocs){
                useruSkaits = numOfDocs + 1;
                var insertJson = {
                'id':useruSkaits,
                'email': email,
                'password': password,
                'passwordText':plaint_password,
                'salt':salt,
                'name':name,
                'phone':phone,
                'address':address
            };

            db.collection('user')
                .find({'email':email}).count(function(err,number){
                    if(number != 0){
                        response.json('Email already exists');
                        console.log('Email already exists');
                    }
                    else{
                        db.collection('user')
                            .insertOne(insertJson,function(err,res){      
                                response.json('Registration successful');
                                console.log('Registration successful');
                            })
                    }
                })
            });


            

                
        });

        app.post('/pievienoProduktusKaaFirebase', (request,response,next)=>{
            var db = client.db('AppDB');
            for(var  i=1; i<51; i++){
                var prod_id = (100 + i);
                var prod_n = "Women's Product No. " + i.toString();
                var boole;
                var prod_price;
                if (i < 26) {
                    prod_price = i + 24.99;
                    boole = true;
                } else {
                    prod_price = 99.49 - i;
                    boole = false;
                }
                var prod_desc = "Women's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Women" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"women"
                };
                db.collection('product')
                            .insertOne(insertJson,function(err,res){      
                            })
            }
            var db = client.db('AppDB');
            for(var  i=51; i<101; i++){
                var prod_id = (100 + i);
                var prod_n = "Men's Product No. " + i.toString();
                var boole;
                var prod_price;
                if (i < 76) {
                    prod_price = i - 4.99;
                    boole = true;
                } else {
                    prod_price = 9.49 + i;
                    boole = false;
                }
                var prod_desc = "Men's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Men" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"men"
                };
                db.collection('product')
                            .insertOne(insertJson,function(err,res){      
                            })
            }
            var db = client.db('AppDB');
            for(var  i=101; i<151; i++){
                var prod_id = (100 + i);
                var prod_n = "Children's Product No. " + i.toString();
                var boole;
                var prod_price;
                if (i < 126) {
                    prod_price = i - 24.99;
                    boole = true;
                } else {
                    prod_price = 9.49 + i;
                    boole = false;
                }
                var prod_desc = "Children's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Children" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"children"
                };
                db.collection('product')
                            .insertOne(insertJson,function(err,res){      
                            })
            }
            response.json('Registration successful');
        });

        app.post('/pievienoProduktusKaaFirebaseVir', (request,response,next)=>{
            var db = client.db('AppDB');
            for(var i=51; i<101; i++){
                var prod_id = (100 + i);
                var prod_n = "Men's Product No. " + i.toString();
                var boole;
                var prod_price;
                if (i < 76) {
                    prod_price = 24.99 + i;
                    boole = true;
                } else {
                    prod_price = 99.49 - i;
                    boole = false;
                }
                var prod_desc = "Men's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Men" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"men"
                };
                db.collection('product')
                            .insertOne(insertJson,function(err,res){      
                               // response.json('Registration successful');
                                console.log('Registration successful');
                            })
            }
            response.json('Registration successful');
        });

        app.post('/pievienoProduktusKaaFirebaseSiev', (request,response,next)=>{
            var db = client.db('AppDB');
            for(var i=12001; i<13001; i++){ //1-51; 151-1101;1101-10101
                var prod_id = (100 + i);
                var prod_n = "Women's Product No. " + i.toString();
                var boole;
                var prod_price;
                // if (i < 26) {
                //     prod_price = 24.99 + i;
                //     boole = true;
                // } else {
                    prod_price = 49.49 + i;
                    boole = false;
                // }
                var prod_desc = "Women's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Women" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"women"
                };
                db.collection('product')
                            .insertOne(insertJson,function(err,res){      
                              //  response.json('Registration successful');
                                console.log('Registration successful');
                            })
            }
            response.json('Registration successful');
        });

        app.post('/registerStore', (request,response,next)=>{
            var db = client.db('AppDB');
            for(var i=251; i<301; i++){ //1-51; 151-1101;1101-10101
                var prod_id = (100 + i);
                var prod_n = "Women's Product No. " + i.toString();
                var boole;
                var prod_price;
                // if (i < 26) {
                //     prod_price = 24.99 + i;
                //     boole = true;
                // } else {
                    prod_price = 49.49 + i;
                    boole = false;
                // }
                var prod_desc = "Women's Product No. " + i.toString() + " is very comfortable and soft.";
                var prod_img = "Women" + i.toString() + ".png";
                var insertJson = {
                    'id': prod_id,
                    'name': prod_n,
                    'special_offer':boole,
                    'price':prod_price,
                    'description':prod_desc,
                    'image':prod_img,
                    'category':"women"
                };
                db.collection('store')
                            .insertOne(insertJson,function(err,res){      
                              //  response.json('Registration successful');
                                console.log('Registration successful');
                            })
            }
            response.json('Registration successful');
        });



        //Login
        app.post('/login', (request,response,next)=>{
            var post_data = request.body;

            console.log('Login Now!!!');

            var email = post_data.email;
            var userPassword = post_data.password;

            var db = client.db('AppDB');

            db.collection('user')
                .find({'email':email}).count(function(err,number){
                    if(number == 0){
                        response.json('Email not exists');
                        console.log('Email not exists');
                    }
                    else{
                        db.collection('user')
                            .findOne({'email':email},function(err,user){
                                var salt = user.salt;
                                var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                var encrypted_password = user.password;
                                if(hashed_password == encrypted_password){
                                    response.json('Login successful');
                                    console.log('Login successful');
                                } else {
                                    response.json('Wrong password');
                                    console.log('Wrong password');
                                }
                            })
                    }
                })
        });

        app.post('/makeOrder', (request,response,next)=>{
            var db = client.db('AppDB');
            var post_data = request.body;

            let d = new Date();
            var diena = ("0" + d.getDate()).slice(-2);
            var menesis  = ("0" + (d.getMonth() + 1)).slice(-2);
            var gads = d.getFullYear();
            var datums = diena + "." + menesis + "." + gads + ".";

                var user_id_st = post_data.user_id;
                var user_id = parseInt(user_id_st);
                var user_name = post_data.user_name;
                var user_email = post_data.email;
                var user_phone = post_data.phone;
                var user_address = post_data.address;
                var products_names = post_data.prod_names;
                var products_ids = post_data.prod_ids;
                var products_price_st = post_data.price;
                var products_price = parseFloat(products_price_st);
                var dat = datums;
                var statuss = false; //piegadats vai nepiegadats
                db.collection('order').countDocuments({}, function (err, count){

                    if (err){
                        console.log(err);
                    }else {
                        var order_id = (count+1);

                        var insertJson = {
                            'id': order_id,
                            'user_id': user_id,
                            'user_name': user_name,
                            'user_email': user_email,
                            'user_phone': user_phone,
                            'user_address': user_address,
                            'products_names': products_names,
                            'products_ids': products_ids,
                            'products_price': products_price,
                            'datums': dat,
                            'statuss': statuss
                        };
                    

                        db.collection('order')
                            .insertOne(insertJson,function(err,res){      
                               // response.json('Registration successful');
                                console.log('Registration successful');

                                db.collection("cart").deleteMany({'user_id':{$gte:user_id}}, function(err, result) {
                                    if(err) throw err;
                                    // res.send(result);
                                  });
                            })
                    }
                });
                
        });
        app.post('/makeOrderContinue', (request,response,next)=>{
            var db = client.db('AppDB');
            var post_data = request.body;
        });



        app.get('/getCartData',(req,res)=>{
            //Te:
            var db = client.db('AppDB');   
            var prod_id = "6";//post_data.id;
            prod_id = parseInt(prod_id);
            /*
            db.collection("cart").find({}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
              });*/

              db.collection("cart").find({'prod_id':{$gte:prod_id}}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
              });
        });

        //nakamo velak izdzest!!!
        app.post('/addProductsToStore',(req,res,next)=>{
            var prod_id = 4;
            var name = "Muskulu piedeva";
            var price = 24.49;
            var descrip = "Vitaminu komplekts, kas paredzets muskulu atslabinasanai pec slodzes. Ediena piedeva. Lietosana: 1 - 3 kausinji diena pie ediena.";
            var image = "id4.jpg";
            var db = client.db('AppDB');   


            db.collection('product').insertOne({
                'prod_id': prod_id,
                'prod_name': name,
                'price': price,
                'descrip': descrip,
                'image': image},
            );

        });

        app.get('/getProductsFromStore',(req,res)=>{
            var db = client.db('AppDB'); 
            db.collection("product").find({}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
              });  
        });

        app.get('/getUsersFromStore',(req,res)=>{
            var db = client.db('AppDB'); 
            db.collection("user").find({}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
                console.log("Esam pie useriem");
                // console.log(result.values);
              });  
        });


        //aplukot lietotaja grozu
        app.post('/getThisUserCart',(req,res)=>{
            var db = client.db('AppDB');   
            var user_id = post_data.user;
            user_id = parseInt(user_id);
              db.collection("cart").find({'user_id':{$gte:user_id}}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
              });
        });

        //so izmantojam!!!
        app.get('/getUserCart',(req,res)=>{

            // var db = client.db('AppDB'); 
            // db.collection("product").find({}).toArray((err, result) => {
            //     if(err) throw err
            //     res.send(result);
            //     // console.log(result.values);
            //   });


            var db = client.db('AppDB');   
            // var user_id = "2";//post_data.user;
            // user_id = parseInt(user_id);
              db.collection("cart").find({}).toArray((err, result) => {
                if(err) throw err
                res.send(result);
                // console.log(result);
              });
        });

        
        app.get('/getUserOrder',(req,res)=>{
            var db = client.db('AppDB');   
            // var user_id = "2";//post_data.user;
            // user_id = parseInt(user_id);
              db.collection("order").find().toArray((err, result) => {
                if(err) throw err
                res.send(result);
              });
        });

        //https://www.mongodb.com/docs/manual/tutorial/update-documents/

        app.post('/incProduct',(req,res,next)=>{
            var post_data = req.body;
           var user = post_data.user; 
           var prod_id = post_data.product;
           var produser = prod_id+user;
           produser = parseInt(produser);
            prod_id = parseInt(prod_id);
            user = parseInt(user);
            var amount = post_data.amount;
            amount = parseInt(amount);
            var db = client.db('AppDB');
            var skaitins = amount;
            console.log("inc prod " + skaitins + " " + produser + " " + prod_id + " " + user);
            console.log(skaitins);
                db.collection("cart").updateOne({'produser':{$gte:produser}}, {$set: {amount:skaitins}}, function(err, result) {
                    if(err) throw err;  
                    console.log("Palielinam skaitu pa 1!" + skaitins);    
                    res.json('');      
                });
        });

        app.post('/decProduct',(req,res,next)=>{
            var post_data = req.body;
            var user = post_data.user; 
            var prod_id = post_data.product;
            var produser = prod_id+user;
            produser = parseInt(produser);
            user = parseInt(user);
            prod_id = parseInt(prod_id);
            var amount = post_data.amount;
            amount = parseInt(amount);
            var db = client.db('AppDB');
            console.log("samazinam pa 1 " + produser);
                db.collection("cart").updateOne({'produser':{$gte:produser}}, {$set: {amount: amount}}, function(err, result) {
                    if(err) throw err;  
                    console.log("Samazinam skaitu pa 1!");          
                });
            res.json('');
       });

       //delete product from users cart
       app.post('/deleteProduct',(req,res,next)=>{        
        var post_data = req.body;
        var user = post_data.user; //s_id;
        var prod_id = post_data.product;
        var produser = prod_id+user;
        produser = parseInt(produser);
        user = parseInt(user);
        prod_id = parseInt(prod_id);
        var db = client.db('AppDB');
        console.log("dzesam_produktu");
        //deleteMany - ja jadzes visus kas atbilst noradijumiem iekavas
        db.collection("cart").deleteOne({'produser':{$gte:produser}}, function(err, result) {
            if(err) throw err;
            res.send(result);
          });

       });

       app.post('/deleteUser',(req,res,next)=>{ 
        var post_data = req.body;
        var user = post_data.user; //s_id;
        user = parseInt(user);
        
        // user = parseInt(user);
        var db = client.db('AppDB');
        //deleteMany - ja jadzes visus kas atbilst noradijumiem iekavas


        db.collection("cart").deleteMany({'user_id':{$gte:user}}, function(err, result) {
            if(err) throw err;
            // res.send(result);
            console.log("Carts has been deleted");

          });

          db.collection("order").deleteMany({'user_id':{$gte:user}}, function(err, result) {
            if(err) throw err;
            // res.send(result);
            console.log("Orders has been deleted");

          });

        db.collection("user").deleteOne({'id':{$gte:user}}, function(err, result) {
            if(err) throw err;
            // res.send(result);
            console.log("User has been deleted");
          });


        // var insertJson = {
        //     'id': user
        // };
        var insertJson = {
            'id':user,
            'email': "",
            'password': "",
            'passwordText':"",
            'salt':"",
            'name':"",
            'phone':"",
            'address':""
        };
        db.collection('user')
                    .insertOne(insertJson,function(err,res){      
                       // response.json('Registration successful');
                        console.log('Registration successful');
                    })


       });


       //vai delete sadi:
       app.delete('/deleteOneProdDifferently',(req,res)=>{
            // var post_data = req.body;
        
        var name = "Kecups";//post_data.name;
        var price = "1.49";//post_data.price;
        price = parseFloat(price);
        var user = "2";//post_data.user; //s_id;
        user = parseInt(user);
        // var description = post_data.description;
        var prod_id = "5";//post_data.id;
        prod_id = parseInt(prod_id);
        var amount = "4";//post_data.amount;
        amount = parseInt(amount);
        var db = client.db('AppDB');
        //deleteMany ja visus kas atbilst iekavam velamies izdzest
        db.collection("cart").deleteOne({'prod_id':{$gte:prod_id}, 'user_id':{$gte:user}}, function(err, result) {
            if(err) throw err;
            res.send(result);
          });
       });


        //add Product to users cart
       app.post('/addProductAgain',(req,res,next)=>{
        var post_data = req.body;
        
            var name = post_data.name;
            var price = post_data.price;
            price = parseFloat(price);
            var user = post_data.user; //s_id;
            // var description = post_data.description;
            var prod_id = post_data.id;
            var produser = prod_id+user;
            produser = parseInt(produser);
            prod_id = parseInt(prod_id);
            user = parseInt(user);
            var amount = post_data.amount;
            amount = parseInt(amount);

            var db = client.db('AppDB');
            console.log(user + " " + prod_id + " " + produser);
            


            db.collection('cart').insertOne({
                'produser':produser,
                'user_id': user,
                'prod_id': prod_id,
                'prod_name': name,
                'price': price,
                'amount': amount},
                
            );
            console.log("pievienojam");
            res.json('');

            /*db.collection('cart').countDocuments({'produser': {$gte:produser}}, function (err, count){
                if (count == 0){
                    console.log('This product not in this users cart!!! To add product!!!');
                    res.json('This product not in this users cart!!! To add product!!!');
                        // console.log('This product not in this users cart!!! To add product!!!');
                        //add product
                        db.collection('cart').insertOne({
                            'produser':produser,
                            'user_id': user,
                            'prod_id': prod_id,
                            'prod_name': name,
                            'price': price,
                            'amount': amount},
                            
                        );
                        console.log("pievienojam");

                }
                else {

                      var skaitins = 0;
                      db.collection("cart").find({'produser': {$gte:produser}}).toArray((err, result) => {
                        if(err) throw err;
                        
                        res.send(result);
                        skaitins = result[0].amount;
                        
                        // result.updateOne({})

                        if (skaitins>0){
                            console.log("updatojam" + result[0].amount);

                        
                      
                      skaitins = skaitins + 1;
                      console.log(skaitins);

                    //   amount = amount + 1;

                      db.collection("cart").updateOne({'produser': {$gte:produser}}, {$set: {amount: skaitins}}, function(err, result) {

                        if(err) throw err;
                        
                        // res.send(result);
                      });
                    } else {
                        db.collection('cart').insertOne({
                            'produser':produser,
                            'user_id': user,
                            'prod_id': prod_id,
                            'prod_name': name,
                            'price': price,
                            'amount': amount},
                            
                        );
                        console.log("pievienojam");

                    }

                    });

                      

                }
            });*/

       });

        app.post('/addProduct',(req,res,next)=>{
            var post_data = req.body;
        
            var name = post_data.name;
            var price = post_data.price;
            price = parseFloat(price);
            var user = post_data.user; //s_id;
            user = parseInt(user);
            // var description = post_data.description;
            var prod_id = post_data.id;
            prod_id = parseInt(prod_id);
            var amount = post_data.amount;
            amount = parseInt(amount);

            var db = client.db('AppDB');
            console.log(user + " " + prod_id);

            
            db.collection('cart').countDocuments({'user_id':{$gte:user}}, function (err, count){

                // if (err){
                //     console.log(err);
                // }else {
                    // if (count == 0){
                        console.log('User dont have any product in cart!!! To add product!!!');

                        res.json('User dont have any product in cart!!! To add product!!!');
                        //add product
                        db.collection('cart').insertOne({
                            'user_id': user,
                            'prod_id': prod_id,
                            'prod_name': name,
                            'price': price,
                            'amount': amount},
                        );

                    /* }
                    else {
                        //te to visu parejo
                        console.log('Useram ir produkti groza');

                        // db.collection('cart').aggregate([
                        //     {$match: {"prod_id": {$gte:prod_id}, 'user_id':{$gte:user}}},
                            
                        // ]);

                        // db.collection('cart').aggregate([
                        //    {$lookup: {
                        //     $match: {"prod_id": {$gte:prod_id}, 'user_id':{$gte:user}}
                        //    }}
                            
                        // ]);

                        var fiel = {
                            'prod_id':{$gte:prod_id}, 
                            'user_id':{$gte:user}
                        }

                        db.collection('cart').countDocuments(fiel, function (err, count){
                            if (count == 0){
                                console.log('This product not in this users cart!!! To add product!!!');
                                res.json('This product not in this users cart!!! To add product!!!');
                                    // console.log('This product not in this users cart!!! To add product!!!');
                                    //add product
                                    db.collection('cart').insertOne({
                                        'user_id': user,
                                        'prod_id': prod_id,
                                        'prod_name': name,
                                        'price': price,
                                        'amount': amount},
                                        
                                    );

                            }
                            else {

                                  var skaitins = 0;
                                  db.collection("cart").find({'prod_id':{$gte:prod_id}, 'user_id':{$gte:user}}).toArray((err, result) => {
                                    if(err) throw err;
                                    
                                    res.send(result);
                                    skaitins = result[0].amount;
                                    
                                    // result.updateOne({})
                                  
                                  skaitins = skaitins + 1;
                                  console.log(skaitins);

                                  db.collection("cart").updateOne({'prod_id':{$gte:prod_id}, 'user_id':{$gte:user}}, {$set: {amount: skaitins}}, function(err, result) {

                                    if(err) throw err;
                                    
                                    // res.send(result);
                                  });

                                });

                                  

                            }
                        });

                        
                    }*/
                // }
            });
        
        });



        //Start Web Server
        app.listen(3000, ()=>{
            console.log('Connected to MongoDB Server, WebService running on port 3000');
        })
    }
})