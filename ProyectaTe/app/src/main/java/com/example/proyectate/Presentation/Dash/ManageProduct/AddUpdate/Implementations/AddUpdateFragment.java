package com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Implementations;

import static com.example.proyectate.Utils.Util.convertImageService;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdateView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.example.proyectate.Utils.DialogueGenerico;

public class AddUpdateFragment extends BaseFragment {
    private AddUpdatePresenter presenter;
    private Project product;
    private ImageView arrow, image;
    private TextView title;
    private EditText url, name, description, count, precio;
    private Button save;
    private ProjectDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_add_update, container, false));

        dao = new ProjectDao(getContext());
        presenter = new AddUpdatePresenter(new listenerPresenter(), getContext(), dao);

        arrow = getCustomView().findViewById(R.id.iv_back_add_update);
        title = getCustomView().findViewById(R.id.tv_title_add_update);
        image = getCustomView().findViewById(R.id.iv_image_add_update);
        url = getCustomView().findViewById(R.id.et_url);
        name = getCustomView().findViewById(R.id.et_name);
        description = getCustomView().findViewById(R.id.et_descript);
        count = getCustomView().findViewById(R.id.et_count);
        precio = getCustomView().findViewById(R.id.et_valor);
        save = getCustomView().findViewById(R.id.btn_add_update);

        if (getArguments() != null) {
            Project item = getArguments().getParcelable(Constants.Tag.PROJECT, Project.class);
            if (item != null) {
                this.product = item;
                fillDataFields();
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrow.setOnClickListener(v->
            Navigation.findNavController(requireView()).navigateUp());
        save.setOnClickListener(v->{
            Project item = new Project(
                    name.getText().toString(),
                    description.getText().toString(),
                    precio.getText().toString(),
                    count.getText().toString()
            );
            if (product == null){
                presenter.insertProduct(item);
                return;
            }

            item.setId(product.getId());
            product = item;
            presenter.updateProduct(item);
        });
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                convertImageService(s.toString(), image, 300);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void fillDataFields(){
        title.setText(getString(R.string.actualizar_producto));
        convertImageService(product.getImage(), image, 300);
        url.setText(product.getImage());
        name.setText(product.getTitle());
        description.setText(product.getDescription());
        count.setText(product.getDateEnd().toString());
        precio.setText(product.getDateInit().toString());
    }
    private void emptyFields(){
        name.setText("");
        description.setText("");
        count.setText("");
        precio.setText("");
    }

    private class listenerPresenter implements IAddUpdateView {

        @Override
        public void showInsertProduct(int id, String name) {
            dialogueFragment(R.string.insertar_usuario, getString(R.string.insert_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            emptyFields();
        }

        @Override
        public void showUpdateProduct(int id, String name) {
            dialogueFragment(R.string.update_usuario, getString(R.string.update_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            Navigation.findNavController(requireView()).navigate(R.id.action_addUpdateFragment_to_detailFragment, bundle);
        }

        @Override
        public void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, message, type);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}