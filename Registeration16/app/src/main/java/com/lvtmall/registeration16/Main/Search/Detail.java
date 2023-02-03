package com.lvtmall.registeration16.Main.Search;

import android.graphics.Bitmap;

public class Detail {
    String m_no;
    String image;
    String price ;
    String item ;
    String main_image1;
    String main_image2;
    String main_image3;
    private Bitmap bitmap;
    //Getter and Setter 만들어 준다
    public String getm_no() {
        return m_no;
    }
    public void setm_no(String m_no) {
        this.price = m_no;
    }
    public String getprice() {
        return price;
    }
    public void setprice(String price) {
        this.price = price;
    }
    public String getitem() {
        return item;
    }
    public void setitem(String item) {
        this.item = item;
    }
    public String getmain_image1() {
        return main_image1;
    }
    public void setmain_image1(String main_image1) {
        this.main_image1 = main_image1;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getmain_image2() {
        return main_image2;
    }
    public void setmain_image2(String main_image2) {
        this.main_image2= main_image2;
    }
    public String getmain_image3() {
        return main_image3;
    }
    public void setmain_image3(String main_image3) {
        this.main_image3= main_image3;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    //이 각각의 변수에 대한 Constructor(생성자)를 만들어 준다. 순서대로 나옴
    public Detail(String m_no,String image,String price, String item, String main_image1, String main_image2,String main_image3) {
        this.m_no = m_no;
        this.image = image;
        this.price = price;
        this.item = item;
        this.main_image1 = main_image1;
        this.main_image2 = main_image2;
        this.main_image3 = main_image3;
    }


}
