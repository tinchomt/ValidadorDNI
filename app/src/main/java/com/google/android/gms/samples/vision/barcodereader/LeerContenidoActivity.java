package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class LeerContenidoActivity extends AppCompatActivity implements View.OnClickListener{

    private SurfaceView cameraView;
    private TextView barcodeValue;
    private CameraSource cameraSource;
    String resultado_ocr;
    Map<String, String> mapaResultados = new TreeMap<String, String>();
    String resultados;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_leer_contenido);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        barcodeValue = (TextView) findViewById(R.id.text_value);

        Intent intent = getIntent();
        resultados = intent.getStringExtra("mapaValores");

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                Log.d("Main", "receiveDetections");
                final SparseArray<TextBlock> items = detections.getDetectedItems();


                if (items.size() != 0) {
                    barcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder value = new StringBuilder();
                            for (int i = 0; i < items.size(); ++i) {
                                TextBlock item = items.valueAt(i);
                                value.append(item.getValue());
                                System.out.println("Valor "+i+": "+item.getValue());
                                value.append("\n");
                            }
                            //update text block content to TextView
                            barcodeValue.setText(value.toString());

                            System.out.println("Block: "+ value.toString());
                            resultado_ocr = value.toString();
                        }
                    });
                }

            }
        });

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available.");
        }

        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    //noinspection MissingPermission
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }

    public void retornarResultado(View v){

        Intent intent = new Intent();

        System.out.println("retornarResultado: "+resultado_ocr);

        System.out.println("retornarMapa: "+resultados);

        //intent.putExtra("resultado_ocr",resultado_ocr);
        //setResult(RESULT_OK, intent);
        //finish();
    }

    @Override
    public void onClick(View view) {

    }
}
