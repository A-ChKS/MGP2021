package com.sdm.mgp2021;

// Tan Siew Lan

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PauseButton implements EntityBase {
    private boolean isDone = false;
    private boolean isInit = false;
    private boolean Paused = false;

    private Bitmap bmpP = null;
    private Bitmap scaledbmpP = null;

    private Bitmap bmpUP = null;
    private Bitmap scaledbmpUP = null;

    private int renderLayer = 0;;

    private int xPos, yPos;

    private int ScreenWidth, ScreenHeight;


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

        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pausep);
        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.pausepress);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmpP = Bitmap.createScaledBitmap(bmpP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/7, true);
        scaledbmpUP = Bitmap.createScaledBitmap(bmpUP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/7, true);

        xPos = ScreenWidth - 150;
        yPos = 150;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if(TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown() && !Paused) {   // Check touch collision here
                float imgRadius = scaledbmpP.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)) {
                    Paused = true;

                    // Button got clicked show the popup dialog
                    if (PauseConfirmDialogFragment.IsShown)
                        return;

                    PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment();
                    newPauseConfirm.show(GamePage.Instance.getFragmentManager(), "PauseConfirm");

                    // AudioManager.Instance.PlayAudio(R.raw.clicksound);


                }
                // If just want a pause without the popup dialog
                GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
            }
        }
        else

            Paused = false;

    }

    @Override
    public void Render(Canvas _canvas) {
//        if (ShieldOn == false)
        if (Paused == false)
            _canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f, yPos - scaledbmpP.getHeight() * 0.5f, null);
        else
            _canvas.drawBitmap(scaledbmpUP, xPos - scaledbmpUP.getWidth() * 0.5f, yPos - scaledbmpUP.getHeight() * 0.5f, null);


    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        //return;
        renderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_PAUSE;}

    public static PauseButton Create() {
        PauseButton result = new PauseButton();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }


    public static PauseButton Create(int _layer){

        PauseButton result = new PauseButton();
        result.SetRenderLayer(_layer);
        return result;
    }

}

