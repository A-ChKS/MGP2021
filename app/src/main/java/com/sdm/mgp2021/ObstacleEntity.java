package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.view.SurfaceView;

import java.util.Random;

public class ObstacleEntity implements EntityBase{

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int ScreenWidth, ScreenHeight;

    public float xStart = 0, xPos = 0, yPos = 0;

    private float yLimit = 0, yStart = 0;

    private float speed = 0,flapAmount = 0, gravityVec = 0;
    private boolean tapGuard = false;

    @Override
    public boolean IsDone(){ return isDone; }

    @Override
    public void SetIsDone(boolean _isDone){ isDone = _isDone; }

    // For us to intialize or load resource eg: images
    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.mipmap.ic_launcher);

        // Randomize a location to spawn on screen
        Random ranGen = new Random();
        xStart = xPos = _view.getWidth();
        ScreenHeight = _view.getHeight();
        yPos = ranGen.nextInt() % ScreenHeight;

        // Set a speed to cross the screen
        speed = _view.getWidth() * 0.5f;
    }

    @Override
    public void Update(float _dt) {
        // check if current state is where?
        //if (StateManager.Instance.GetCurrentState() != "MainGame");
            //return;

        xPos -= speed * _dt;
        // Check if we are out of the screen
        if (xPos <= -bmp.getHeight() * 0.5f)
        {
            // Move it to another location
            xPos = xStart;
            Random ranGen = new Random();
            yPos = ranGen.nextInt() % ScreenHeight;
        }

        // Check collision player or another object
        //if (Collision.SphereToSphere(xPos, yPos, bmp.getWidth() * 0.5f,GameSystem.Instance.Ship.xPos, GameSystem.Instance.Ship.yPos,GameSystem.Instance.Ship.bmp.getWidth() * 0.5f));
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null); // 1st image
        _canvas.drawBitmap(scaledbmp, xPos + ScreenWidth, yPos, null); // 2nd image (to show its continuous)
        //_canvas.drawBitmap(scaledbmp, tfx, null);
    }

    @Override
    public boolean IsInit(){ return bmp != null; }

    @Override
    public int GetRenderLayer(){ return LayerConstants.PLAYER_LAYER; }

    @Override
    public void SetRenderLayer(int _newLayer){ return; }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){ return EntityBase.ENTITY_TYPE.ENT_DEFAULT; }
}
