package com.example.alpi.manasbus;



        import android.app.DatePickerDialog;
        import android.app.ProgressDialog;
        import android.app.TimePickerDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonGet;
    private ListView textViewResult;
    private Spinner Spinner1,Spinner2;

    Button tarihButton;
    TextView tarihTextView;
TextView ucret,saat1,g,g2;

    private ProgressDialog loading;

    ArrayList<HashMap<String, String>> arrList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView i=(ImageView)findViewById(R.id.imageView);
        i.setImageResource(R.drawable.kirgizistan);
        i.setVisibility(View.VISIBLE);
        arrList = new ArrayList<HashMap<String, String>>();
//Tarih ayari


        tarihButton = (Button) findViewById(R.id.button1);
        tarihTextView = (TextView) findViewById(R.id.tarih);
        tarihButton.setOnClickListener(new View.OnClickListener() {//tarihButona Click Listener ekliyoruz
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
                int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz

                DatePickerDialog datePicker;//Datepicker objemiz
                datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        tarihTextView.setText( year + "/" + monthOfYear+ "/"+dayOfMonth);//Ayarla butonu tıklandığında textview'a yazdırıyoruz

                    }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
                datePicker.show();

            }
        });
//Tarih ve saat ayari son

        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewResult = (ListView) findViewById(R.id.textViewResult);
        Spinner1= (Spinner) findViewById(R.id.spinner1);
        Spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> categories = new ArrayList<String>();
        categories.add("Seciniz...");
        categories.add("Naryn");
        categories.add("Karakol");
        categories.add("Calal-Abad");
        categories.add("Colpon-Ata");
        categories.add("Osh");
        categories.add("Bishkek");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1.setAdapter(dataAdapter);
        Spinner2.setAdapter(dataAdapter);
        ucret = (TextView) findViewById(R.id.ucret);
        saat1 = (TextView) findViewById(R.id.saat1);
        g = (TextView) findViewById(R.id.g);
        g2 = (TextView) findViewById(R.id.g2);
        buttonGet.setOnClickListener(this);
        textViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String name = ((TextView) view.findViewById(R.id.vc)).getText().toString();
                ucret.setText(name);
                String saat = ((TextView) view.findViewById(R.id.tarih)).getText().toString();
                saat1.setText(saat);
                getData1();
                Intent intocan = new Intent(MainActivity.this, yenisayfa.class);
                startActivity(intocan);

            }
        });

    }

    private void getData() {
                String a=tarihTextView.getText().toString().trim();

        if (a.equals("Tarih")) {
            Toast.makeText(this, "Bos Gecmeyiniz", Toast.LENGTH_LONG).show();
            return;
        }
            loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = "http://alperen.esy.es/login.php?id=" + Spinner1.getSelectedItem().toString() + "&id1=" + Spinner2.getSelectedItem().toString() + "&tarih=" + tarihTextView.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }
    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            if(result.length()==0){Toast.makeText(this, "Boyle Bir Sefer Bulunmamaktadir.", Toast.LENGTH_LONG).show();}
            else {
                for (int i = 0; i < result.length(); i++) {

                    JSONObject collegeData = result.getJSONObject(i);


                    HashMap<String, String> map1 = new HashMap<String, String>();


                    map1.put("name", collegeData.getString("name"));
                    map1.put("address", collegeData.getString("address"));
                    map1.put("fiyat", collegeData.getString("fiyat"));
                    map1.put("saat", collegeData.getString("saat"));


                    arrList.add(map1);


                }
            }

            }catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
            }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrList,
                R.layout.list_item,
                new String[] {"name","fiyat","address","saat" }, new int[] { R.id.name, R.id.vc, R.id.address,R.id.tarih});
        textViewResult.setAdapter(simpleAdapter);




    }




    @Override
    public void onClick(View v) {
        getData();

    }
    private void getData1() {
if(Spinner1.getSelectedItem().toString()=="Naryn"){g.setText("1");}
        else if(Spinner1.getSelectedItem().toString()=="Karakol" ){ g.setText("2");}
        else if(Spinner1.getSelectedItem().toString()=="Calal-Abad"){ g.setText("5");}
        else if (Spinner1.getSelectedItem().toString()=="Colpon-Ata"){ g.setText("6");}
        else if(Spinner1.getSelectedItem().toString()=="Osh"){ g.setText("3");}
        else if(Spinner1.getSelectedItem().toString()=="Bishkek"){ g.setText("4");}

        if(Spinner2.getSelectedItem().toString()=="Naryn"){g2.setText("1");}
        else if(Spinner2.getSelectedItem().toString()=="Karakol" ){ g2.setText("2");}
        else if(Spinner2.getSelectedItem().toString()=="Calal-Abad"){ g2.setText("5");}
        else if (Spinner2.getSelectedItem().toString()=="Colpon-Ata"){ g2.setText("6");}
        else if(Spinner2.getSelectedItem().toString()=="Osh"){ g2.setText("3");}
        else if(Spinner2.getSelectedItem().toString()=="Bishkek"){ g2.setText("4");}


        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://alperen.esy.es/aaaa.php?id=" + g.getText().toString().trim()+"&id1="+g2.getText().toString().trim()+"&tarih="+tarihTextView.getText().toString().trim()+"&fiyat="+ucret.getText().toString().trim()+"&saat="+saat1.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}