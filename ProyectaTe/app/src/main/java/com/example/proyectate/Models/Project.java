package com.example.proyectate.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;

import java.util.List;

public class Project implements Parcelable {

    private int id;
    private int userId;
    private String title;
    private String description;
    private String dateInit;
    private String dateEnd;
    private String image;

    public Project(String title, String description, String dateInit, String dateEnd) {
        this.title = title;
        this.description = description;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
    }
    public Project(){}

    public boolean validateFieldsProduct() {
        return title != null && !title.isEmpty()
                && description != null && !description.isEmpty()
                && dateInit != null && dateInit.isEmpty()
                && dateEnd != null&& dateEnd.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateInit() {
        return dateInit;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //Metodos de consumos SQlite
    public int insertProject(ProjectDao dao){return (int) dao.insertProduct(this);}
    public int updateProject(ProjectDao dao){return (int) dao.updateProduct(this);}
    public Boolean deleteProject(ProjectDao dao){return dao.deleteProduct(id);}
    public static List<Project> getListProject(ProjectDao dao, int userId){
        return dao.getListProjects(userId);
    }

    // Parceo del modelo
    protected Project(Parcel in){
        id = in.readInt();
        userId = in.readInt();
        title = in.readString();
        description = in.readString();
        dateEnd = in.readString();
        dateInit = in.readString();
        image = in.readString();
    }
    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(dateEnd);
        parcel.writeString(dateInit);
        parcel.writeString(image);
    }
}