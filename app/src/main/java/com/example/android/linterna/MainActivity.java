package com.example.android.linterna;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button radioBtnLinterna;
    private Boolean linternaON = false;
    private Boolean tieneflash = false;
    private Camera camera;//instancie Camera

    Camera.Parameters parametersCamera;//LLame los parametros disponibles para las cameras


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //validar si el cellular tiene flash
        tieneflash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);//verifique si tiene flash
        radioBtnLinterna = (Button) findViewById(R.id.radioBtnLinterna);//Busca el id del boton al cual le queremos dar una accion
        if (!tieneflash){
            radioBtnLinterna.setEnabled(false);//Si no tiene flash desabilita el boton
            return;//para matar el proceso
        }

        radioBtnLinterna.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (linternaON){// si la linterna esta prendida
                    apagarflash();//apaga
                }
                else{//si la linterna esta apagada
                    encenderflash();// prende el flash
                }
            }

        });

    }


    private void encenderflash() {// cre metodo encender flash
        if (!linternaON){
            if (camera == null){//si la camara no inicio
                try{
                    camera = Camera.open();// ba iniciar
                }
                catch (RuntimeException e){// si no consigue
                    Log.e("ERROR AL ABRIR CAMERA", e.getMessage());//tira este error
                    radioBtnLinterna.setEnabled(false);//desactiva el botn
                }
            }
            parametersCamera = camera.getParameters();//accede a las opciones de la camara
            parametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// va desir como comportarse el flash
            camera.setParameters(parametersCamera);//ejecutar flash
            camera.startPreview();//aca recien ejecuta el parametro

            linternaON = true;
        }
    }
    private void apagarflash() {//cree metodo para apagar flash
        if (linternaON){
            parametersCamera = camera.getParameters();//accede a las opciones de la camara
            parametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);// va desir como comportarse
            camera.setParameters(parametersCamera);//ejecutar flash
            camera.stopPreview();//aca recien para el parametro
            linternaON = false;
        }
    }
}
