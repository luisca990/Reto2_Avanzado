package com.example.proyectate.Presentation.Dash.Home.Implementations;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.Home.Adapter.OnItemClickListenerProduct;
import com.example.proyectate.Presentation.Dash.Home.Adapter.RecyclerAdapterProducts;
import com.example.proyectate.Presentation.Dash.Home.Interfaces.IHomeView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private HomePresenter presenter;
    private SessionManager sessionManager;
    private List<Project> productsList;
    private RecyclerAdapterProducts adapter;
    private EditText search;
    private ImageView logout;
    private FloatingActionButton fabAdd, fabCar, fabHistory;
    private ProjectDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_home, container, false));
        // Inicialización de la base de datos y conexión
        productsList = new ArrayList<>();
        RecyclerView rv = getCustomView().findViewById(R.id.rvProducts);
        search = getCustomView().findViewById(R.id.searchView);
        logout = getCustomView().findViewById(R.id.iv_logout);
        fabAdd = getCustomView().findViewById(R.id.fab_add);
        fabCar = getCustomView().findViewById(R.id.fab_buy);
        fabHistory = getCustomView().findViewById(R.id.fab_history);

        dao = new ProjectDao(getContext());
        presenter = new HomePresenter(new listenerPresenter(), dao, getContext());
        sessionManager = new SessionManager(requireContext());

        adapter = new RecyclerAdapterProducts(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        displaySesion();
        textSearchProduct();
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllProductsSuccess();
        logout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), getString(R.string.el_usuario)+sessionManager.getUserEmail()+getString(R.string.se_deslogueo), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
        fabAdd.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_addUpdateFragment));
        fabCar.setOnClickListener(v->{
        });
        fabHistory.setOnClickListener(v-> {});
    }

    private void displaySesion(){
        if (!sessionManager.isLoggedIn()) {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
            Toast.makeText(getContext(), "El usuario " + sessionManager.getUserEmail() + " no esta logueado", Toast.LENGTH_SHORT).show();
        }
    }
    private void textSearchProduct(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    adapter.updateList(productsList);
                    hideKeyboardFragment();
                    return;
                }
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private class listenerPresenter implements IHomeView{
        @Override
        public void showGetAllProductsSuccess(List<Project> products) {
            productsList = products;
            adapter.updateList(products);
        }
    }

    private class listenerAdapter implements OnItemClickListenerProduct {
        @Override
        public void onItemClick(Project product) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Tag.PROJECT, product);
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}