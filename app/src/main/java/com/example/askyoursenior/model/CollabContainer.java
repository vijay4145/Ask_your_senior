package com.example.askyoursenior.model;

public class CollabContainer {

    String imageurl , username ,projectdescription,by ,projectname;

    public CollabContainer() {
    }

    public CollabContainer(String username, String projectdescription, String by, String projectname) {

        this.username = username;
        this.projectdescription = projectdescription;
        this.by = by;
        this.projectname = projectname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectdescription() {
        return projectdescription;
    }

    public void setProjectdescription(String projectdescription) {
        this.projectdescription = projectdescription;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
