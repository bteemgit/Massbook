package com.example.admin.mpassbook;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.mpassbook.modules.HomePage.Homepage;

public class ExpenseManager extends AppCompatActivity implements View.OnClickListener {

    LinearLayout limiselanious,lientertainment,lifood,lihousehold,limobilerecharge,lipersonal,lisavings,litransportation,liutility,lifooter;
    FloatingActionButton fabmiselanious,fabentertainment,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabutility;
    TextView tv_cancel,tv_submit;
    LinearLayout liback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_expense_manager);
        limiselanious=(LinearLayout)findViewById(R.id.limiselanious);
        lientertainment=(LinearLayout)findViewById(R.id.lientertainment);
        lifood=(LinearLayout)findViewById(R.id.lifood);
        lihousehold=(LinearLayout)findViewById(R.id.lihousehold);
        limobilerecharge=(LinearLayout)findViewById(R.id.limobilerecharge);
        lipersonal=(LinearLayout)findViewById(R.id.lipersonal);
        lisavings=(LinearLayout)findViewById(R.id.lisavings);
        litransportation=(LinearLayout)findViewById(R.id.litransportation);
        liutility=(LinearLayout)findViewById(R.id.liutility);
        lifooter=(LinearLayout)findViewById(R.id.lifooter);

        liback=(LinearLayout)findViewById(R.id.liback);
        liback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabmiselanious=(FloatingActionButton)findViewById(R.id.fabmiselanious);
        fabentertainment=(FloatingActionButton)findViewById(R.id.fabentertainment);
        fabfood=(FloatingActionButton)findViewById(R.id.fabfood);
        fabhousehold=(FloatingActionButton)findViewById(R.id.fabhouseholds);
        fabmobilerecharge=(FloatingActionButton)findViewById(R.id.fabmobilerecharge);
        fabpersonal=(FloatingActionButton)findViewById(R.id.fabpersonal);
        fabsavings=(FloatingActionButton)findViewById(R.id.fabsavings);
        fabtransportation=(FloatingActionButton)findViewById(R.id.fabtransportation);
        fabutility=(FloatingActionButton)findViewById(R.id.fabutility);

        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_submit=(TextView)findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);

        fabmiselanious.setOnClickListener(this);
        fabentertainment.setOnClickListener(this);
        fabfood.setOnClickListener(this);
        fabhousehold.setOnClickListener(this);
        fabmobilerecharge.setOnClickListener(this);
        fabpersonal.setOnClickListener(this);
        fabsavings.setOnClickListener(this);
        fabtransportation.setOnClickListener(this);
        fabutility.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fabmiselanious:
                fabclick(fabmiselanious,fabentertainment,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabutility);
                break;
            case R.id.fabentertainment:
                fabclick(fabentertainment,fabmiselanious,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabutility);

                break;
            case R.id.fabfood:
                fabclick(fabfood,fabmiselanious,fabentertainment,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabutility);

                break;
            case R.id.fabhouseholds:
                fabclick(fabhousehold,fabmiselanious,fabfood,fabentertainment,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabutility);

                break;
            case R.id.fabmobilerecharge:
                fabclick(fabmobilerecharge,fabmiselanious,fabfood,fabhousehold,fabentertainment,fabpersonal,fabsavings,fabtransportation,fabutility);

                break;
            case R.id.fabpersonal:
                fabclick(fabpersonal,fabmiselanious,fabfood,fabhousehold,fabmobilerecharge,fabentertainment,fabsavings,fabtransportation,fabutility);

                break;
            case R.id.fabsavings:
                fabclick(fabsavings,fabmiselanious,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabentertainment,fabtransportation,fabutility);

                break;
            case R.id.fabtransportation:
                fabclick(fabtransportation,fabmiselanious,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabentertainment,fabutility);

                break;
            case R.id.fabutility:
                fabclick(fabutility,fabmiselanious,fabfood,fabhousehold,fabmobilerecharge,fabpersonal,fabsavings,fabtransportation,fabentertainment);

                break;
            case R.id.tv_cancel:
                fabChangeColor("0");
                break;
            case R.id.tv_submit:
                fabChangeColor("1");
                break;
        }
    }

    private void fabChangeColor(String s) {
        lifooter.setVisibility(View.GONE);
        if (s.equals("0"))
        {
            fabmiselanious.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabentertainment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabfood.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabhousehold.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabmobilerecharge.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabpersonal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabsavings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabtransportation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            fabutility.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        }
        else
        {
            Intent intent=new Intent(ExpenseManager.this,Homepage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void fabclick(FloatingActionButton fabmiselanious, FloatingActionButton fabentertainment, FloatingActionButton fabfood, FloatingActionButton fabhousehold, FloatingActionButton fabmobilerecharge, FloatingActionButton fabpersonal, FloatingActionButton fabsavings, FloatingActionButton fabtransportation, FloatingActionButton fabutility) {
        lifooter.setVisibility(View.VISIBLE);
        fabmiselanious.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        fabentertainment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabfood.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabhousehold.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabmobilerecharge.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabpersonal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabsavings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabtransportation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        fabutility.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));



    }
}
