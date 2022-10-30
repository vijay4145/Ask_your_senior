package com.example.askyoursenior.model;

public class ProjectDetailModel {

    String projectName, postedBy, projectDescription, imageUrl, linkedInIdLink, githubIdLink, whatsappNumber;

    public ProjectDetailModel() {
    }

    public ProjectDetailModel(String projectName, String postedBy, String projectDescription, String imageUrl, String linkedInIdLink, String githubIdLink, String whatsappNumber) {
        this.projectName = projectName;
        this.postedBy = postedBy;
        this.projectDescription = projectDescription;
        this.imageUrl = imageUrl;
        this.linkedInIdLink = linkedInIdLink;
        this.githubIdLink = githubIdLink;
        this.whatsappNumber = whatsappNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }


    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkedInIdLink() {
        return linkedInIdLink;
    }

    public void setLinkedInIdLink(String linkedInIdLink) {
        this.linkedInIdLink = linkedInIdLink;
    }

    public String getGithubIdLink() {
        return githubIdLink;
    }

    public void setGithubIdLink(String githubIdLink) {
        this.githubIdLink = githubIdLink;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }
}
