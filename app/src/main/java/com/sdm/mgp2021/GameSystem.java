package com.sdm.mgp2021;

// Tan Siew Lan

import android.content.SharedPreferences;
import android.view.SurfaceView;


public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // For Scoring
    public static final String SHARED_PREF_ID = "GameSaveFile";

    // for pause
    private boolean isPaused = false;

    // For scoring
    private int currScore = 0;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // for combos
    public int currCombo = 0;

    // Better to place in a getter/setter.
    public PlayerEntity playerEntityInstance = null;

    // Singleton Pattern : Blocks others from creating // U can edit or add more
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new IntroState());
        StateManager.Instance.AddState(new ResultScreenState());
        StateManager.Instance.AddState(new Gameover());


        // Get our shared preferences (Save file)
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID,0);
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

     // For scoring
    public int GetScore(){
        return currScore;
    }

    public int GetCombo() { return currCombo;}

    public void AddCombo() { currCombo++;}

    public void ResetCombo() { currCombo = 0;}

    public void AddScore(){
        AddScore(1);
    }

    public void AddScore (int _amt){
        currScore += _amt;
    }

    public void ResetScore(){
        currScore = 0;
    }

    public void SaveEditBegin()
    {
        // Safety check, only allow if not already editing
        if (editor != null)
            return;

        // Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        // Check if has editor
        if (editor == null)
            return;

        editor.commit();
        editor = null; // Safety to ensure other functions will fail once commit done
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 10);
    }

}
