package com.example.byc.phonesharescreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {

    private Button btn_sharescreen_to;
    private Button btn_sharescreen_from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sharescreen_from = (Button) findViewById(R.id.btn_sharescreen_from);
        btn_sharescreen_to = (Button)findViewById(R.id.btn_sharescreen_to);

        btn_sharescreen_to.setOnClickListener(new View.OnClickListener(){

        });
    }
}
