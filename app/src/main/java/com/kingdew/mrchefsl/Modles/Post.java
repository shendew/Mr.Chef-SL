package com.kingdew.mrchefsl.Modles;

public class Post {

    String post_id,title,desc,image,final_image,video_link,category,ingredients;
    String Sititle,Sidesc,Siingredients;

    public Post(String post_id, String title, String desc, String image, String final_image, String video_link, String category, String ingredients, String sititle, String sidesc, String siingredients) {
        this.post_id = post_id;
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.final_image = final_image;
        this.video_link = video_link;
        this.category = category;
        this.ingredients = ingredients;
        Sititle = sititle;
        Sidesc = sidesc;
        Siingredients = siingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFinal_image() {
        return final_image;
    }

    public void setFinal_image(String final_image) {
        this.final_image = final_image;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSititle() {
        return Sititle;
    }

    public void setSititle(String sititle) {
        Sititle = sititle;
    }

    public String getSidesc() {
        return Sidesc;
    }

    public void setSidesc(String sidesc) {
        Sidesc = sidesc;
    }

    public String getSiingredients() {
        return Siingredients;
    }

    public void setSiingredients(String siingredients) {
        Siingredients = siingredients;
    }
}
