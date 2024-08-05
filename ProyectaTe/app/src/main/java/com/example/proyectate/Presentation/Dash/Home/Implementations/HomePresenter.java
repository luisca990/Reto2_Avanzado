package com.example.proyectate.Presentation.Dash.Home.Implementations;

import static com.example.proyectate.Utils.Util.isNetworkAvailable;

import android.content.Context;
import android.widget.Toast;

import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.Firebase.FirebaseHelper;
import com.example.proyectate.DataAccess.Firebase.interfaces.onListenerCallback;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.GetProjectsListener;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.IHomePresenter;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.IHomeView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;

import java.util.List;

public class HomePresenter implements IHomePresenter {
    private final IHomeView view;
    private final ProjectDao dao;
    private final Context context;
    private final SessionManager sessionManager;
    private final FirebaseHelper firebase;

    public HomePresenter(IHomeView view, ProjectDao dao, Context context) {
        this.view = view;
        this.dao = dao;
        this.context = context;
        this.dao.openDb();
        sessionManager = new SessionManager(context);
        firebase = new FirebaseHelper();
    }

    @Override
    public void getAllProjectsSuccess() {
        view.showGetAllProductsSuccess(Project.getListProject(dao, sessionManager.getUseId()));
    }

    @Override
    public void synchronizeData(List<Project> list) {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(context, context.getText(R.string.not_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        Project.synchronizeData(firebase, list, new onListenerCallback() {
            @Override
            public void onSuccessChecked(boolean exists) {
                view.showDialogFragment(R.string.sincronizacion, context.getString(R.string.sincronizacion_exitosa), DialogueGenerico.TypeDialogue.OK);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    public void getProjectsFirebase() {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(context, context.getText(R.string.not_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        Project.getProjectsFirebase(sessionManager.getUseId(), firebase, new listenerGetFirebase());
    }

    private class listenerGetFirebase implements GetProjectsListener {
        @Override
        public void onListProjects(List<Project> projects) {
            view.showGetAllProductsSuccess(projects);
        }

        @Override
        public void onError(Exception e) {

        }
    }
}
