package com.example.mazeball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class Opciones extends AppCompatActivity {
    public static final String PREFS_NAME = "FitxerPrefs";

    Switch bSwitch;
    RadioButton eRB, mRB, hRB;
    Button SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);


        final SharedPreferences config = getSharedPreferences(PREFS_NAME, 0);

        final Intent i = getIntent();

        bSwitch= (Switch) findViewById(R.id.switchsonido);
        eRB = (RadioButton) findViewById(R.id.dif1);
        eRB.setChecked(true);
        mRB = (RadioButton) findViewById(R.id.dif2);
        //hRB = (RadioButton) findViewById(R.id.dif3);
        SaveButton = (Button) findViewById(R.id.saveOptions);
        RadioGroup rg = (RadioGroup) findViewById(R.id.RG1);


        bSwitch.setChecked(config.getBoolean("switchSound", true));
        eRB.setChecked(config.getBoolean("easy", true));
        mRB.setChecked(config.getBoolean("medium", false));
        //hRB.setChecked(config.getBoolean("hard", false));


        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(bSwitch.isChecked()){
                    Toast.makeText(Opciones.this, "Music ON", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Opciones.this, "Music OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.dif1:
                        eRB.setChecked(true);
                        mRB.setChecked(false);
                       // hRB.setChecked(false);
                        break;
                    case R.id.dif2:
                        eRB.setChecked(false);
                        mRB.setChecked(true);
                       // hRB.setChecked(false);
                        break;
                   /* case R.id.dif3:
                        eRB.setChecked(false);
                        mRB.setChecked(false);
                        hRB.setChecked(true);
                        break;*/
                }
            }
        });


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = config.edit();
                editor.putBoolean("switchSound", bSwitch.isChecked());
                editor.putBoolean("easy", eRB.isChecked());
                editor.putBoolean("medium", mRB.isChecked());
                //editor.putBoolean("hard", hRB.isChecked());
                editor.commit();

                Toast.makeText(Opciones.this, "Options saved. Changes will be aplied at return to menu", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
