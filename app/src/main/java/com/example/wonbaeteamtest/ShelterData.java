package com.example.wonbaeteamtest;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

public class ShelterData {
    public Uri img;//이미지
    public int subject;//주제
    public String address;//대피소 주소
    public String name;//대피소 이름
    public String provider;//정보제공자

    public ShelterData(Uri img,int subject, String name, String provider,String address){
        this.img=img;
        this.subject=subject;
        this.name=name;
        this.address=address;
        this.provider=provider;
    }
}
