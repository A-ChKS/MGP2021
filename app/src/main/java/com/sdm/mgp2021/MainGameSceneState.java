package com.sdm.mgp2021;

// Tan Siew Lan

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
 
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
        StarEntity.Create();
        RenderTextEntity.Create();

        //SmurfEntity.Create(); //wk8<--Add Smurf

        PauseButton.Create(); 

        // Restart score here
        GameSystem.Instance.ResetScore();

        timer = 0.0f;

        int currScore = 0;
        GameSystem.Instance.SaveEditBegin();
        GameSystem.Instance.SetIntInSave("Score", currScore);
        GameSystem.Instance.SaveEditEnd();
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

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);

        _canvas.drawText(scoreText, 10, 220, paint);
    }

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



