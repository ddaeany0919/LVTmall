package com.lvtmall.registeration16.Main.Cart;

import android.graphics.Bitmap;

public class Shoppingcart {
    String price ;

    String item;
    String m_no;
    String image;
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

    //이 각각의 변수에 대한 Constructor(생성자)를 만들어 준다. 순서대로 나옴
    public Shoppingcart(String m_no, String price,String item, String image) {
        this.m_no = m_no;
        this.price = price;
        this.item = item;
        this.image = image;
    }


}
