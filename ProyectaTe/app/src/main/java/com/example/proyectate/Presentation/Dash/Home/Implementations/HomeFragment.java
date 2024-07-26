package com.example.proyectate.Presentation.Dash.Home.Implementations;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.proyectate.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private HomePresenter presenter;
    private SessionManager sessionManager;
    private List<Project> productsList;
    private RecyclerAdapterProducts adapter;
    private ProjectDao dao;
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());
        // Inicialización de la base de datos y conexión
        productsList = new ArrayList<>();
        RecyclerView rv = binding.rvProjects;

        dao = new ProjectDao(getContext());
        presenter = new HomePresenter(new listenerPresenter(), dao, getContext());
        sessionManager = new SessionManager(requireContext());

        adapter = new RecyclerAdapterProducts(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        displaySesion();
        textSearchProduct();
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllProductsSuccess();
        binding.ivLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), getString(R.string.el_usuario)+sessionManager.getUserEmail()+getString(R.string.se_deslogueo), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
        binding.fabAdd.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_addUpdateFragment));
        binding.fabSynchronize.setOnClickListener(v-> {});
    }

    private void displaySesion(){
        if (!sessionManager.isLoggedIn()) {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
            Toast.makeText(getContext(), getString(R.string.el_usuario) + sessionManager.getUserEmail() + getString(R.string.no_esta_logueado), Toast.LENGTH_SHORT).show();
        }
    }
    private void textSearchProduct(){
        binding.searchView.addTextChangedListener(new TextWatcher() {
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