package com.example.alpi.manasbus;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class yenisayfa extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog loading;
    EditText ad1,soyad1,mail1,tel1;
    Button buton;
    CheckBox erkek,kiz;

    TextView cinsiyet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yenisayfa);
        ad1 = (EditText) findViewById(R.id.editText);
        soyad1 = (EditText) findViewById(R.id.editText2);
        mail1 = (EditText) findViewById(R.id.editText3);
        tel1 = (EditText) findViewById(R.id.editText4);
        buton = (Button) findViewById(R.id.button);

        erkek= (CheckBox)findViewById(R.id.radioButton2);
        kiz= (CheckBox)findViewById(R.id.radioButton);
        cinsiyet=(TextView)findViewById(R.id.textView11);

        buton.setOnClickListener(this);

        erkek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                     cinsiyet.setText("1");
                                                 if(erkek.isChecked()==true)
                                                 { kiz.setEnabled(false);
                                                     cinsiyet.setText("1");
                                                 }
                                                 else{ kiz.setEnabled(true);
                                                     cinsiyet.setText("");}


                                             }
                                         }
        );
        kiz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                                 if(kiz.isChecked()==true)
                                                 { erkek.setEnabled(false);
                                                     cinsiyet.setText("0");
                                                 }
                                                 else{ erkek.setEnabled(true);
                                                     cinsiyet.setText("");}

                                             }
                                         }
        );
    }

    private void getData1() {


        loading  = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = "http://alperen.esy.es/bbbb.php?ad1=" +ad1.getText().toString().trim()+"&soyad1="+soyad1.getText().toString().trim()+"&mail1="+mail1.getText().toString().trim()+"&tel1="+tel1.getText().toString().trim()+"&cinsiyet="+cinsiyet.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(yenisayfa.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




        String smsNumber = tel1.getText().toString().trim();
        String smsText = "ManasBus tarafindan rezervasyonunuz yapilmistir.3 gun icinde ariyarak biletinizi alabilirsiniz.";
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> parcala_bol=manager.divideMessage(smsText);
        manager.sendMultipartTextMessage(smsNumber, null, parcala_bol, null, null);
if(ad1.length()==0) {
    ad1.requestFocus();
    ad1.setError("Ad boş geçilemez!");
}else if(soyad1.length()==0)
{
    soyad1.requestFocus();
    soyad1.setError("Soyad boş geçilemez!");
}else if(mail1.length()==0){
    mail1.requestFocus();
    mail1.setError("Mail boş geçilemez!");
}
        else if(tel1.length()==0){
    tel1.requestFocus();
    tel1.setError("Tel boş geçilemez!");
}else{

    Intent i = new Intent(yenisayfa.this,Banka.class);
    i.putExtra("mail",mail1.getText().toString().trim());
    startActivity(i);
}


    }

    @Override
    public void onClick(View v) {


getData1();
    }
}