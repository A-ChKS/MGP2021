package com.sdm.mgp2021;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Mainmenu extends Activity implements OnClickListener {

    private Button btn_start;
    private Button btn_back;

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);  // Set Listener to this button --> Start Button

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);  // Set Listener to this button --> Back Button
    }

    @Override
    //Invoke a callback event in the view
    public void onClick (View v){

        //Intent = action to be performed
        //Intent is an object provides runtime binding
        //You have to new an instance of this object to use it

        //Becos we need to check if start or back button is clicked/ touched on the screen, then after
        //what do we want to do.
        //If start button is clicked, we go to Splash page.
        //If back button is clicked, we go to main menu.

        Intent intent = new Intent();

        if (v == btn_start){
            //intent -> to set to another class which is another page or screen to be launch.
            //Equal to change screen
            intent.setClass(this,Splashpage.class);
        }
        else if (v == btn_back){
            intent.setClass(this, Mainmenu.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
