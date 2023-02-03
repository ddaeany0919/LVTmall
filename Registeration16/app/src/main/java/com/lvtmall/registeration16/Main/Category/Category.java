package com.lvtmall.registeration16.Main.Category;

import android.graphics.Bitmap;

public class Category {
    String price ;
    String explan ;
    String item;
    String m_no;
    String category;
    String image;
    private Bitmap bitmap;
    //Getter and Setter 만들어 준다
    public String getcategory() {
        return category;
    }
    public void setcategory(String category) {
        this.category = category;
    }
    public String getprice() {
        return price;
    }
    public void setprice(String price) {
        this.price = price;
    }
    public String getexplan() {
        return explan;
    }
    public void setexplan(String explan) {
        this.explan = explan;
    }
    public String getitem() {
        return item;
    }
    public void setitem(String item) {
        this.item = item;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getm_no() {
        return m_no;
    }
    public void setm_no(String m_no) {
        this.price = m_no;
    }

    //이 각각의 변수에 대한 Constructor(생성자)를 만들어 준다. 순서대로 나옴
    public Category(String category,String m_no, String price, String explan, String item, String image) {
        this.category = category;
        this.m_no = m_no;
        this.price = price;
        this.explan = explan;
        this.item = item;
        this.image = image;
    }


}
