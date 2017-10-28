package com.example.abo_nayel.nearbyplaces;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SignUp extends AppCompatActivity {

    Button signIn, signUp,addphoto;
    EditText name, mail, pass, birthday;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = (EditText) findViewById(R.id.nameedt);
        mail = (EditText) findViewById(R.id.mailedt);
        pass = (EditText) findViewById(R.id.passtxt);
        birthday = (EditText) findViewById(R.id.birthedt);
        addphoto = (Button)findViewById(R.id.addbtn);
        signIn = (Button) findViewById(R.id.signinbtn);
        signUp = (Button) findViewById(R.id.signupbtn);
        image = (ImageView) findViewById(R.id.profile_image);


        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH)+1;
        final int day = c.get(Calendar.DAY_OF_MONTH);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        birthday.setText(y+"-"+m+"-"+d);
                    }
                }, year, month, day);
                datePicker.setTitle("select date");
                datePicker.show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
//                SharedPreferences prefs =SignUp.this.getSharedPreferences("User_Data",MODE_PRIVATE);
//
//                Toast.makeText(SignUp.this,prefs.getString("user0",null),Toast.LENGTH_SHORT).show();

//                if(users.size()>0){
//                Toast.makeText(SignUp.this,users.get(0).toString(),Toast.LENGTH_SHORT).show();
//                editor.remove("user0");
//                users.remove(0);}
//                Intent i = new Intent(SignUp.this,Login.class);
//                startActivity(i);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               User user= new User(name.getText().toString(), mail.getText().toString(), pass.getText().toString());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                User user = new User(name.getText().toString(), mail.getText().toString(), pass.getText().toString());
                try { user = new User(name.getText().toString(), mail.getText().toString(), pass.getText().toString(),
                            image.getDrawingCache(), format.parse(birthday.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(SignUp.this,user.getBirth().toString(),Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("User_Data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                Gson gson = new Gson();

                String theUser = gson.toJson(user);
                Toast.makeText(SignUp.this, theUser, Toast.LENGTH_SHORT).show();

                editor.putString(user.getName(), theUser);
                editor.apply();

            }
        });


    }
    public void openGallery(View v)
    {
        image = (ImageView) findViewById(R.id.imageView);
        Intent gallery = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,0);
    }
    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap =(Bitmap) data.getExtras().get("data");
        image.setImageBitmap(bitmap);
//        Uri imageuri = data.getData();
//        image.setImageURI(imageuri);
    }
}
