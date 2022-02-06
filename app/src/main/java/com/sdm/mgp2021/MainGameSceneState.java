package com.sdm.mgp2021;

// Tan Siew Lan

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private int currScore = 0;
    private float speed = 1.f;
    private int ss = 50;
    private float lifetime;
    private int combo = 0;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create();
//        Ship.Create();
//        NextEntity.Create();
        SmurfEntityDraggable.Create(); //wk8 <-add draggable smurf
        StarEntity.Create();
        RenderTextEntity.Create();

        //SmurfEntity.Create(); //wk8<--Add Smurf

        PauseButton.Create(); 

        // Restart score here
        GameSystem.Instance.ResetScore();

        timer = 0.0f;

        GameSystem.Instance.SaveEditBegin();
        GameSystem.Instance.SetIntInSave("Score", currScore);
        GameSystem.Instance.SaveEditEnd();

        lifetime = 30.f; // 30.f
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
        EntityManager.Instance.Clean();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

        String scoreText = String.format("SCORE : %d", GameSystem.Instance.GetIntFromSave("Score"));
        String timeText = String.format("TIME : %f", lifetime);
        String comboText = String.format("COMBO : %d", GameSystem.Instance.GetCombo());

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);

        _canvas.drawText(scoreText, 10, 220, paint);
        _canvas.drawText(timeText, 750, 80, paint);
        _canvas.drawText(comboText, 750, 220, paint);
    }

    @Override
    public void Update(float _dt) {
        timer += _dt;

        if (GameSystem.Instance.GetScore() >= 50 && GameSystem.Instance.GetScore() < 100)
        {
            speed = 0.85f;
        }
        else if (GameSystem.Instance.GetScore() >= 100 && GameSystem.Instance.GetScore() < 150)
        {
            speed = 0.70f;
        }
        else if (GameSystem.Instance.GetScore() >= 150 && GameSystem.Instance.GetScore() < 200)
        {
            speed = 0.55f;
        }
        else if (GameSystem.Instance.GetScore() >= 200 && GameSystem.Instance.GetScore() < 250)
        {
            speed = 0.40f;
        }
        else if (GameSystem.Instance.GetScore() >= 250)
        {
            speed = 0.25f;
        }

        if (timer > 1.5 * speed)
        {
            StarEntity.Create();
            timer = 0.0f;
        }

        if (GameSystem.Instance.GetIsPaused()){return;}

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
            StateManager.Instance.ChangeState("Maingame");
        }

        lifetime -= _dt;

        if (GameSystem.Instance.GetCombo() > 3)  {
            lifetime ++;
        }
        if (lifetime < 0.0f) {
            StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



