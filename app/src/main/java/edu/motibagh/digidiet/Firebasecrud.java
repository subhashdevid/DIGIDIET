package edu.motibagh.digidiet;

public class Firebasecrud {

    public String subject;
    public String author;
    public String filelocalPath ="";
    public String isStoredinDB = "n";
    public int fileId;
    public String semester;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilelocalPath() {
        return filelocalPath;
    }

    public void setFilelocalPath(String filelocalPath) {
        this.filelocalPath = filelocalPath;
    }

    public String getIsStoredinDB() {
        return isStoredinDB;
    }

    public void setIsStoredinDB(String isStoredinDB) {
        this.isStoredinDB = isStoredinDB;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String url;
    public String type;
    public String title;

    public Firebasecrud(){}
    public Firebasecrud(int fileId,String subject, String url, String isStoredinDB ,String filelocalPath, String author, String semester, String type, String title) {
        this.subject = subject;
        this.author = author;
        this.semester = semester;
        this.url = url;
        this.type = type;
        this.title = title;
        this.filelocalPath = filelocalPath;
        this.isStoredinDB = isStoredinDB;
        this.fileId = fileId;
    }

}