package com.example.parsearjson.SubDatos;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parsearjson.R;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
//import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.Arrays;



public class BioActivity2 extends AppCompatActivity {

    TextView Title,Date,Pdf;
    ImageView Imagen;
    Button btnDescargar,btnBuscarPortada;

    public Vision vision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio2);
        Vision.Builder visionBuilder = new Vision.Builder(new NetHttpTransport(),
                new AndroidJsonFactory(),  null);
        visionBuilder.setVisionRequestInitializer(new
                VisionRequestInitializer("AIzaSyCV_ADpVQp5_K1CQ98gc6KeOVq5p1sjqKQ"));
        vision = visionBuilder.build();

        String title=(getIntent().getExtras().getString("curTitle"));
        String volum=(getIntent().getExtras().getString("curDate"));
        final String Pdf=(getIntent().getExtras().getString("curPdf"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Title= (TextView) findViewById(R.id.txtTitleBio);
        Date=(TextView) findViewById(R.id.txtDateBio);
        Imagen=(ImageView)findViewById(R.id.txtImagenBio);
        btnDescargar=findViewById(R.id.BtnDescargar);
        btnBuscarPortada=findViewById(R.id.BtnCargarPortada);



        Title.setText(title);
        Date.setText(volum);
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(Pdf));
                request.setDescription("PDF Paper");
                request.setTitle("Pdf Artcilee");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.pdf");
                DownloadManager manager = (DownloadManager) BioActivity2.this.getSystemService(Context.DOWNLOAD_SERVICE);
                try {
                    manager.enqueue(request);        }
                catch (Exception e) {
                    Toast.makeText(BioActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBuscarPortada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //paso 1
                TextView tit=findViewById(R.id.txtTitleBio);
                //paso 2 Feature
                Feature desiredFeature = new Feature();
                desiredFeature.setType("WEB_DETECTION");

                // paso 3 arma la sulicitud(es)
                TextAnnotation request=new TextAnnotation();
                request.setText(tit.getText().toString());
                //AnnotateImageRequest request = new AnnotateImageRequest();
                //request.setImage(inputImage);

                //request.setFeatures(Arrays.asList(desiredFeature));
                BatchAnnotateImagesRequest batchRequest = new
                        BatchAnnotateImagesRequest();
                //batchRequest.setRequests(Arrays.asList(request));
                //paso 4 asignamos al control visionbuilder la solicitud

                try {

                    Vision.Images.Annotate  annotateRequest =
                            vision.images().annotate(batchRequest);
                    //paso 5 enviamos la solicitud
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse batchResponse =
                            annotateRequest.execute();

                    //paso 6 obtener la respuesta
                    WebDetection annotation = batchResponse.getResponses().get(0).getWebDetection();
                    //descripcion="";
                    List<WebEntity> arrayList=annotation.getWebEntities();
                    final String descripcion=arrayList.get(0).getDescription();
                    //LlamarWebService(descripcion);

                    final String resultado=descripcion;

                    //paso 7 asignar la UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageDetail = (ImageView) findViewById(R.id.txtImagenBio);
                            //imageDetail.(descripcion);
                        }
                    });
                    //return text.getText();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPermission(String permission){

        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }
}
