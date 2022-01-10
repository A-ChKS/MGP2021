package com.sdm.mgp2021;

// Tan Siew Lan

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class IntroState implements StateBase {
    private float timer = 0.0f;

    @Override
    public String GetName() {
        return "Intro";
    }

    @Override
    public void OnEnter(SurfaceView _view) {}

    @Override
    public void OnExit() {}

    @Override
    public void Render(Canvas _canvas) {}

    @Override
    public void Update(float _dt) {
        timer += _dt;

//        if (timer > spawnTime)
//        {
//            //PlayerEntity.Create();  //Example: Sprite
//            //NextEntity.Create();
//            timer = 0.0f;
//        }

        if (GameSystem.Instance.GetIsPaused()){return;}

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {

            StateManager.Instance.ChangeState("Maingame");
        }
    }
}



