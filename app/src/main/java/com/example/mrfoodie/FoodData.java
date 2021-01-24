package com.example.mrfoodie;

public class FoodData {
    private String itemName;
    private String itemDesc;
    private String itemPrice;
    private String itemImage;

    public FoodData()
    {

    }
    public FoodData(String itemName,String itemDesc, String itemPrice, String itemImage )
    {
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }
}
