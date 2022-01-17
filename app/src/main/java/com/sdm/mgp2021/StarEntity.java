package com.sdm.mgp2021;

// Created by TanSiewLan20201
// Sample Entity 

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import java.util.Random;

public class StarEntity implements EntityBase, Collidable{

    private Bitmap bmp = null;

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private float screenWidth, screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;
    private float cspeed = 250;

    private int triesCount = 10;
    private boolean hasTouched = false;

    private Vibrator _vibrator;
    Random ranGen = new Random();

    private int currScore = 0;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // New method using our own resource manager : Returns pre-loaded one if exists
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.candy);
        isInit = true;

        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();
        xPos = screenWidth;
        yPos = ranGen.nextFloat() * screenHeight;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    public void startVibrte() {
        if (Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        }
        else {
            long pattern[] = {0, 50, 0};
            _vibrator.vibrate(pattern, -1);
        }
    }

    public void stopVibrate() {
        _vibrator.cancel();
    }

    @Override
    public void Update(float _dt) {


        if (GameSystem.Instance.GetIsPaused()) return;

        // Do nothing if it is not in the main game state
        if (StateManager.Instance.GetCurrentState() != "MainGame") return;

        if (xPos >= -bmp.getHeight() * 0.5f){
            xPos -= cspeed * _dt;
        }

        // Check out of screen
        else if (xPos <= -bmp.getHeight() * 0.5f){

            // Move it to another random pos again
            xPos = screenWidth;
            yPos = ranGen.nextFloat() * screenHeight;
        }

        float imgRadius1 = bmp.getWidth() * 0.5f;
        //Log.v("imgrad","s"+imgRadius1);
        if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius1) )
        {
            xPos = screenWidth;
            yPos = ranGen.nextFloat() * screenHeight;
            currScore += 10;
            GameSystem.Instance.SaveEditBegin();
            GameSystem.Instance.SetIntInSave("Score", currScore);
            GameSystem.Instance.SaveEditEnd();
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.STAR_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

    public static StarEntity Create()
    {
        StarEntity result = new StarEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "StarEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

   @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() !=  "SmurfEntity") {  // Another entity

//            AudioManager.Instance.PlayAudio(R.raw.correct, 0.9f);
//            startVibrate();
//            log.v(TAG, "hit");

            xPos = screenWidth;
            yPos = ranGen.nextFloat() * screenHeight;

            SetIsDone(true);
        }
    }
}
