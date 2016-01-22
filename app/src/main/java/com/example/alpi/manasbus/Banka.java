package com.example.alpi.manasbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Banka extends Activity{
    Button buton,buton1;
    TextView gelenad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banka);

        buton=(Button)findViewById(R.id.button2);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Banka.this,MainActivity.class);
                startActivity(i);
            }
        });
        buton1=(Button)findViewById(R.id.button3);
       gelenad=(TextView)findViewById(R.id.textView33);
        gelenad.setText(getIntent().getExtras().getString("mail"));
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(i.EXTRA_EMAIL, new String[]{gelenad.getText().toString().trim()});
                i.putExtra(i.EXTRA_SUBJECT, "MANASBUS");
                i.putExtra(i.EXTRA_TEXT, "KAYIT TAMAM");
                 startActivity(i);
            }
        });

    }


}
