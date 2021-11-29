package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class PlayerEntity implements EntityBase{

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int ScreenWidth, ScreenHeight;

    public float xPos = 0;
    public float yPos = 0;

    private float yLimit = 0;
    private float yStart = 0;

    private float flapAmount = 0;
    private float gravityVec = 0;
    private boolean tapGuard = false;

    @Override
    public boolean IsDone(){ return isDone; }

    @Override
    public void SetIsDone(boolean _isDone){ isDone = _isDone; }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){

        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);

        // Setup all our variables
        xPos = _view.getWidth() * 0.1f;
        yStart = yPos = _view.getHeight() * 0.5f;
        yLimit = _view.getHeight() - bmp.getHeight() * 0.5f;
    }
    @Override
    public void Update(float _dt) {

        // Do nothing if it is not main game state
        if (StateManager.Instance.GetCurrentState() == "Default")
        {
            if (yStart < yPos)
                Flap();
        }

        // Handling the behaviour of the player entity here~

        // Flapping code
        if (TouchManager.Instance.IsDown() && tapGuard == false) {
            // Flap~
            Flap();
            tapGuard = true;
        }
        else {
            tapGuard = false;
        }

        // Gravity Stuff
        gravityVec += _dt * 10.0f;
        yPos += gravityVec;

        // Handle if our entity touches the edges of the screen~
        if (yPos <= bmp.getHeight() * 0.5f || yPos >= yLimit) {
            StateManager.Instance.ChangeState("Mainmenu");
        }
    }

    private void Flap(){
        gravityVec = -flapAmount;
    }

    @Override
    public void Render(Canvas _canvas){
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit(){ return bmp != null; }

    @Override
    public int GetRenderLayer(){ return LayerConstants.PLAYER_LAYER; }

    @Override
    public void SetRenderLayer(int _newLayer){ return; }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT; }

    public static PlayerEntity Create(){
        PlayerEntity result = new PlayerEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
