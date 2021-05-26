package com.example.wonbaeteamtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import static com.example.wonbaeteamtest.MainActivity.arraylist;

public class ShelterEdit extends AppCompatActivity {
    private EditText nameText;
    private EditText addressText;
    private EditText providerText;
    private ArrayList<String> Subject = new ArrayList<>();
    private Spinner mSpinner;
    private int subjectPosition;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;

    private Uri uri;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_edit);

        nameText = (EditText) findViewById(R.id.shtName);
        addressText = (EditText) findViewById(R.id.shtAdd);
        providerText = (EditText) findViewById(R.id.shtWho);
        imageView = (findViewById(R.id.shtImg));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        Intent intent = getIntent();
        uri=intent.getParcelableExtra("simg");
        imageView.setImageURI(uri);
        nameText.setText(intent.getStringExtra("name"));
        addressText.setText(intent.getStringExtra("address"));
        providerText.setText(intent.getStringExtra("provider"));

        Subject.add("지진");
        Subject.add("해일");
        Subject.add("화산");

        mSpinner = (Spinner) findViewById(R.id.editSubject);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Subject);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter1);

        //subjectPosition에 subject 값을 인텐트로 가져옴.
        subjectPosition = intent.getIntExtra("subject", -1);
        mSpinner.setSelection(subjectPosition);//초기값

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    uri = data.getData();
                    imageView.setImageURI(uri);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void mOnClick(View view) {
        Intent data = new Intent();

        switch (view.getId()) {
            case R.id.btnSave:
                //저장버튼을 눌렀을때, 문자열 값이 없으면 대피소이름을 입력하라고 toast출력.
                if (nameText.getText().toString().length() <= 0) {
                    Toast.makeText(this, "대피소 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                else{
                    if(uri==null){

                    }
                    MainActivity.putExtraInfo(data,uri, subjectPosition, nameText.getText().toString(),
                            addressText.getText().toString(), providerText.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                    break;
                }

            case R.id.btnCancel:
                super.onBackPressed();
                break;

        }
    }


}

