package com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Implementations;

import static com.example.proyectate.Utils.Util.convertImageService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.example.proyectate.Utils.DialogueGenerico;

import java.util.Objects;

public class DetailFragment extends BaseFragment {
    private DetailPresenter presenter;
    private Project product;
    private ImageView arrow, image;
    private TextView name, description, count, valor;
    private Button delete, update;
    private ProjectDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_detail, container, false));
        image = getCustomView().findViewById(R.id.iv_image_detail);
        arrow = getCustomView().findViewById(R.id.iv_back_detail);
        name = getCustomView().findViewById(R.id.tv_name_detail);
        description = getCustomView().findViewById(R.id.tv_descript_detail);
        count = getCustomView().findViewById(R.id.tv_count_detail);
        valor = getCustomView().findViewById(R.id.tv_valor_detail);
        delete = getCustomView().findViewById(R.id.btn_delete_section);
        update = getCustomView().findViewById(R.id.btn_update_section);

        dao = new ProjectDao(getContext());
        presenter = new DetailPresenter(new listenerPresenter(), dao);

        if (getArguments() != null) {
            Project item = getArguments().getParcelable(Constants.Tag.PROJECT, Project.class);
            if (item != null) {
                this.product = item; // Show character details immediately
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        update.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Tag.PROJECT, product);
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_addUpdateFragment, bundle);
            Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();});
        delete.setOnClickListener(v->{
            Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();
            presenter.deleteProduct(product);
        });
        arrow.setOnClickListener(v->{
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_homeFragment);
        });
        completeProductData();
    }

    @SuppressLint("SetTextI18n")
    private void completeProductData(){
        if (product != null){
            convertImageService(product.getImage(), image, 300);
            name.setText(product.getTitle());
            description.setText(product.getDescription());
            count.setText(product.getDateEnd().toString());
            valor.setText(product.getDateInit().toString());
        }
    }

    private class listenerPresenter implements IDetailView{
        @Override
        public void showDeleteProduct(Boolean delete, String name) {
            if (delete){
                dialogueFragment(R.string.delete_product, "Se elimino correctamente el producto: "+name, DialogueGenerico.TypeDialogue.OK);
                Navigation.findNavController(requireView()).navigateUp();
            }else {
                dialogueFragment(R.string.delete_product, "No se elimino correctamente el producto, hubo un error", DialogueGenerico.TypeDialogue.OK);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}