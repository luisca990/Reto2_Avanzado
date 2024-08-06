package com.example.proyectate.Models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.Firebase.FirebaseHelper;
import com.example.proyectate.DataAccess.Firebase.interfaces.OnProjectsLoadedListener;
import com.example.proyectate.DataAccess.Firebase.interfaces.onListenerCallback;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.GetProjectsListener;
import java.util.List;

public class Project implements Parcelable {

    private int id;
    private String userId;
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

    public boolean validateFieldsProject() {
        return title != null && !title.isEmpty()
                && description != null && !description.isEmpty()
                && dateInit != null && !dateInit.isEmpty()
                && dateEnd != null&& !dateEnd.isEmpty();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //Metodos de consumos SQlite
    public int insertProject(ProjectDao dao){return (int) dao.insertProject(this);}
    public int updateProject(ProjectDao dao){return (int) dao.updateProject(this);}
    public Boolean deleteProject(FirebaseHelper firebase, ProjectDao dao){
        firebase.checkProjectExists(this, false, new onListenerCallback() {
            @Override
            public void onSuccessChecked(boolean exists) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        return dao.deleteProject(id);
    }
    public static List<Project> getListProject(ProjectDao dao, String userId){return dao.getListProjects(userId);}

    //Metodos de Firebase
    public static void synchronizeData(FirebaseHelper firebase, List<Project> list, onListenerCallback callback){
        firebase.syncProjects(list, new onListenerCallback() {
            @Override
            public void onSuccessChecked(boolean exists) {
                callback.onSuccessChecked(exists);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    public static void getProjectsFirebase(String userId, FirebaseHelper firebase, GetProjectsListener onList) {
        firebase.getProjectsForUser(userId, new OnProjectsLoadedListener() {
            @Override
            public void onProjectsLoaded(List<Project> projects) {
                onList.onListProjects(projects);
            }

            @Override
            public void onError(Exception e) {
                onList.onError(e);
            }
        });
    }

    // Parceo del modelo
    protected Project(Parcel in){
        id = in.readInt();
        userId = in.readString();
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
        parcel.writeString(userId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(dateEnd);
        parcel.writeString(dateInit);
        parcel.writeString(image);
    }
}