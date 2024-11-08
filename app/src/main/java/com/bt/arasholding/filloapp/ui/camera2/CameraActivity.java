package com.bt.arasholding.filloapp.ui.camera2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView; // ImageView'ı ekliyoruz
import android.widget.LinearLayout; // LinearLayout'u ekliyoruz
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bt.arasholding.filloapp.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ImageView imageViewPreview; // Önizleme için ImageView
    private LinearLayout buttonContainer; // Butonları içeren LinearLayout

    ImageButton captureButton;
    String imagePath;

    private Bitmap scaledBitmap;

    private static final int REQUEST_STORAGE_PERMISSION = 101;

    String atf_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_camera);

        atf_id = getIntent().getStringExtra("id");

        previewView = findViewById(R.id.previewView);
        imageViewPreview = findViewById(R.id.imageViewPreview); // ImageView'ı bul
        buttonContainer = findViewById(R.id.buttonContainer); // Butonları bul
        captureButton = findViewById(R.id.captureButton);

        // Depolama iznini kontrol et
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startCamera(); // Scoped Storage ile çalışmak için doğrudan kamerayı başlat
        } else {
            if (checkStoragePermission()) {
                startCamera(); // Depolama izni varsa kamerayı başlat
            } else {
                requestStoragePermissions(); // Depolama izni iste
            }
        }

        captureButton.setOnClickListener(v -> {
            imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    super.onCaptureSuccess(image);

                    // ImageProxy'yi Bitmap'e çevirin
                    Bitmap bitmap = imageProxyToBitmap(image);
                    scaledBitmap = scaleBitmap(bitmap, 4); // 4 kat küçült

                    // Bitmap'i dosyaya kaydedin ve dosya yolunu alın
                    imagePath = saveBitmapToFile(scaledBitmap);

                    // Fotoğraf çekimi tamamlandığında, görünürlüğü ayarla
                    imageViewPreview.setImageBitmap(scaledBitmap); // Çekilen resmi göster
                    imageViewPreview.setVisibility(View.VISIBLE); // ImageView'ı görünür yap
                    buttonContainer.setVisibility(View.VISIBLE); // Butonları görünür yap
                    previewView.setVisibility(View.GONE);
                    captureButton.setVisibility(View.GONE);// PreviewView'ı gizle

                    // ImageProxy'yi serbest bırak
                    image.close();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    super.onError(exception);
                    // Hata işlemleri
                    Toast.makeText(CameraActivity.this, "Kamera hata: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Butonların tıklama olaylarını ayarla
        findViewById(R.id.buttonCancel).setOnClickListener(v -> {
            // İptal et butonuna tıklandığında uyarı mesajı göster
            imageViewPreview.setImageBitmap(null);
            Toast.makeText(CameraActivity.this, "Fotoğraf çekmeden çıktınız!", Toast.LENGTH_SHORT).show();
            finish(); // Aktiviteyi kapat
        });
        findViewById(R.id.buttonRetake).setOnClickListener(v -> retakeImage()); // Tekrar çek
        findViewById(R.id.buttonConfirm).setOnClickListener(v -> confirmImage(scaledBitmap)); // Onayla
    }


    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(this, permissions, REQUEST_STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera(); // Depolama izni verildi, kamerayı başlat
                } else {
                    Toast.makeText(this, "Depolama izni gereklidir.", Toast.LENGTH_SHORT).show();
                    finish(); // İzin verilmediyse aktiviteyi kapat
                }
                break;
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                imageCapture = new ImageCapture.Builder().build();

                // Önizleme oluşturun
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Kamerayı yaşam döngüsüne bağlayın
                cameraProvider.unbindAll(); // Önceden bağlanmış kamerayı çöz
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Kamera başlatma hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                finish(); // Hata durumunda aktiviteyi kapat
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ImageProxy.PlaneProxy[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        // Dönüş açısını hesapla
        int rotationDegrees = image.getImageInfo().getRotationDegrees();

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // Bitmap'i döndür
        return rotateBitmap(bitmap, rotationDegrees);
    }
    private Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0) {
            // Dönüş işlemi
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap; // Dönüş yoksa, orijinal bitmap'i döndür
    }
    private String saveBitmapToFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(null), "image_" + (atf_id != null ? atf_id : "default") + ".jpg");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out); // Resmi dosyaya yaz
            return file.getAbsolutePath(); // Dosya yolunu döndür
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Hata durumunda null döndür
        }
    }

    // Bitmap'in boyutunu küçülten metod
    private Bitmap scaleBitmap(Bitmap bitmap, int sampleSize) {
        int width = bitmap.getWidth() / sampleSize;
        int height = bitmap.getHeight() / sampleSize;
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private void retakeImage() {
        // Fotoğrafı tekrar çek
        imageViewPreview.setImageBitmap(null);
        imageViewPreview.setVisibility(View.GONE); // Önizlemeyi gizle
        buttonContainer.setVisibility(View.GONE); // Butonları gizle
        previewView.setVisibility(View.VISIBLE);
        captureButton.setVisibility(View.VISIBLE);// PreviewView'ı görünür yap
    }


    private void confirmImage(Bitmap bitmap) {
        // Onaylama işlemi
        Toast.makeText(this, "Fotoğraf onaylandı!", Toast.LENGTH_SHORT).show();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
        byte[] byteArray = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Fotoğraf dosya yolunu al
        imagePath = saveBitmapToFile(bitmap);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("confirmed_image", encodedImage); // Base64 string olarak gönderin
        resultIntent.putExtra("image_path", imagePath); // Dosya yolunu da ekleyin
        setResult(RESULT_OK, resultIntent);
        finish(); // Aktiviteyi kapat
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scaledBitmap != null) {
            scaledBitmap.recycle(); // Bitmap'i serbest bırak
            scaledBitmap = null;
        }
    }
}
