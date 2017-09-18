package com.example.tainingzhang.tinyproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Releasable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int a, b, c, anw, count, cor;
    private char op;
    private boolean Addition, Subtraction;
    private Button btnSubmit;
    private Button btnStart;
    private RadioButton rbtnAddition;
    private RadioButton rbtnSubtraction;
    private TextView edtMassage1;
    private TextView edtMassage2;
    private TextView edtMassage3;
    private EditText edtMassage4;
    private TextView txtMassage4;
    private TextView txtUser;
    private TextView txtUserEmail;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

//        txtUser = (TextView) findViewById(R.id.txtUser);
//        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
//        if(user != null) {
//            String userName = user.getDisplayName();
//            String userEmail = user.getEmail();
//            //txtUser.setText(userName);
//            //txtUserEmail.setText(userEmail);
//            //itmEmail.setTitle(userEmail);
//            //itmName.setTitle(userName);
//        }
       // NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View header = navView.getHeaderView(0);
        txtUser = (TextView) header.findViewById(R.id.txtUser);
        txtUserEmail = (TextView) header.findViewById(R.id.txtUserEmail);
        if(user != null) {
            String userName = user.getDisplayName();
            String userEmail = user.getEmail();
            txtUser.setText(userName);
            txtUserEmail.setText(userEmail);
            //itmEmail.setTitle(userEmail);
            //itmName.setTitle(userName);
        }
        // Game Code Part Start here ...
        btnStart = (Button) findViewById(R.id.btnStart);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        rbtnAddition = (RadioButton) findViewById(R.id.rbtnAddition);
        rbtnSubtraction = (RadioButton) findViewById(R.id.rbtnSubtraction);
        edtMassage1 = (TextView) findViewById(R.id.edtMassage1);
        edtMassage2 = (TextView) findViewById(R.id.edtMassage2);
        edtMassage3 = (TextView) findViewById(R.id.edtMassage3);
        txtMassage4 = (TextView) findViewById(R.id.txtMassage4);
        edtMassage4 = (EditText) findViewById(R.id.edtMassage4);

        rbtnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addition = true;
            }
        });
        btnStart.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
                mainlayout.setBackgroundColor(YELLOW);
                return true;
            }
        });
        rbtnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subtraction = true;
            }
        });
    }

    public void StartOnclick(View v){
        count = 0;
        cor = 0;
        if(!Addition&&!Subtraction){
            alert("You need choose at least one kind of operations.");
        }else {
            setnum();
        }
    }

    public void SubmitOnclick(View v){
        String answer = edtMassage4.getText().toString();
        if(answer.length()==0){
            alert("You have to enter an answer!");
        }else {
            int res = Integer.valueOf(answer);
            if (res == anw) cor++;
            edtMassage4.setText("");
            if (count == 10){
                alert("You answered " + cor + " questions corretly!");
                edtMassage3.setText("");
                edtMassage2.setText("");
                edtMassage1.setText("");
            }else {
                setnum();
            }
        }
    }

    public void setnum(){
        count++;
        txtMassage4.setText(Integer.toString(count)+"/10");
        Random r = new Random();
        a = r.nextInt(1000);
        b = r.nextInt(1000);
        if(Addition&&Subtraction){
            c = r.nextInt(2);
            if(c==0){
                op = '+';
                anw = a+b;
            }else{
                op = '-';
                int min = Math.min(a, b);
                int max = Math.max(a, b);
                a = max;
                b = min;
                anw = a-b;
            }
        }else if(Addition){
            op = '+';
            anw = a+b;
        }else{
            op = '-';
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            a = max;
            b = min;
            anw = a-b;
        }
        String sa = Integer.toString(a);
        String sb = Integer.toString(b);
        String sop = Character.toString(op);
        edtMassage1.setText(sa);
        edtMassage2.setText(sb);
        edtMassage3.setText(sop);
    }

    public void alert(String s){
        Toast toast = Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void signOut(){
        mAuth.signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
