package edu.android.appgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.android.appgame.Prevention.PreventionMainActivity;
import edu.android.appgame.test.TestSelectActivity;
import static edu.android.appgame.LoginActivity.LOGIN;
import static edu.android.appgame.LoginActivity.LOGIN_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "tag";
    final Context context = this;

    public static String currentMemberId; // 현재 로그인 되어있는 사용자 아이디
    public static boolean isLogin;    // 현재 로그인 되어있는지 여부

    private ImageButton btnLoginMain;
    private DrawerLayout drawer;
    private TextView textDoLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoginMain = findViewById(R.id.btnLogInMain);

        // 첫 실행에만 로딩 뜨도록
        if(savedInstanceState == null) {
            // 로딩화면 구현
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // 인텐트로 넘어온 값 저장
        // 로그인 성공 - true / 실패 - false
        isLogin = getIntent().getBooleanExtra(LOGIN, false);
        // 로그인 시 사용된 아이디 값 현재 사용자 아이디에 저장
        currentMemberId = getIntent().getStringExtra(LOGIN_ID);

        if(isLogin){
            // 받아온 로그인 여부가 true 이면, 로그인 버튼 비활성화
//            btnLoginMain.setEnabled(false);
        } // end if
    } // end onCreate()




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, FacilityInfoActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_chart) {

            if(isLogin) {
                Intent intent = new Intent(this, ChartMainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(this, "로그인 해야 사용 가능한 기능입니다", Toast.LENGTH_SHORT).show();
            }


            return true;
        } else if( id == R.id.action_map){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_test) {
            Intent intent = new Intent(this, TestSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_game) {
            Intent intent = new Intent (this, GameActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_prevention) {
            Intent intent= new Intent(this, PreventionMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_chart){
            if(isLogin) {
                Intent intent = new Intent(this, ChartMainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(this, "로그인 해야 사용 가능한 기능입니다", Toast.LENGTH_SHORT).show();
            }
        }else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_list) {
            Intent intent = new Intent(this, FacilityInfoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } // end onNavigationItemSelected()

    public void onClickBtnTest(View view) {
        final Intent intent = new Intent(this, TestSelectActivity.class);
        // 검사 시작 전 경고창 띄우기
        switch (view.getId()) {
            case R.id.btnTest:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("치매 검사 시작");
                alertDialogBuilder.setMessage("검사 시작하시겠습니까? (이 검사는 100% 정확하지 않은 검사이므로 결과에 너무 의존하지 마십시오)")
                        .setCancelable(false)
                        .setPositiveButton("시작하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("안하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            default:
                break;
        }


    } // end onClickBtnTest()


    public void onClickBtnGame(View view) {
        Intent intent = new Intent (this, GameActivity.class);
        startActivity(intent);
    } // end onClickBtnGame

    public void onClickBtnLogin(View view) {
        if(isLogin){
            Toast.makeText(context, "이미 로그인 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }else {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    } // end onClickBtnLogin()

    public void onClickBtnHome(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);

    }
} // end class MainActivity

