package com.kingdew.mrchefsl.Modles;

public class CategoryItem {

    String name,siname,id,image;

    public CategoryItem() {
    }

    public CategoryItem(String name, String id, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
