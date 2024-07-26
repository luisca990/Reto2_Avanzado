package com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Implementations;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.DetailAdmin.Interfaces.IDetailView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.example.proyectate.Utils.CustomDialogFragment;
import com.example.proyectate.Utils.DialogueGenerico;
import com.example.proyectate.databinding.FragmentDetailBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailFragment extends BaseFragment {
    private DetailPresenter presenter;
    private Project project;
    private ProjectDao dao;
    private FragmentDetailBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());

        dao = new ProjectDao(getContext());
        presenter = new DetailPresenter(new listenerPresenter(), dao);

        if (getArguments() != null) {
            Project item = getArguments().getParcelable(Constants.Tag.PROJECT);
            if (item != null) {
                this.project = item;
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.btnUpdateSection.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Tag.PROJECT, project);
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_addUpdateFragment, bundle);});
        binding.btnDeleteSection.setOnClickListener(v-> showCustomDialog());
        binding.ivBackDetail.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_homeFragment));
        completeProductData();
    }

    @SuppressLint("SetTextI18n")
    private void completeProductData(){
        if (project != null){
            try {
                Uri uri = Uri.parse(project.getImage());
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.ivImageDetail.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            } catch (Exception e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            }
            binding.tvTitle.setText(project.getTitle());
            binding.tvDescriptDetail.setText(project.getDescription());
            binding.dateEnd.setText(project.getDateEnd());
            binding.dateInit.setText(project.getDateInit());
        }
    }

    private class listenerPresenter implements IDetailView{
        @Override
        public void showDeleteProduct(Boolean delete, String name) {
            if (delete){
                dialogueFragment(R.string.delete_product, getString(R.string.text_delete)+name, DialogueGenerico.TypeDialogue.OK);
                Navigation.findNavController(requireView()).navigateUp();
            }else {
                dialogueFragment(R.string.delete_product, getString(R.string.no_text_delete), DialogueGenerico.TypeDialogue.OK);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }

    public void showCustomDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment(
                "¿Seguro que quieres Eliminar?",
                "",
                () -> {
                    // Manejar evento de "Aceptar"
                    presenter.deleteProduct(project);
                },
                () -> {
                    Toast.makeText(getContext(), "Esta bien no se elimina", Toast.LENGTH_SHORT).show();
                }
        );
        dialog.show(requireActivity().getSupportFragmentManager(), "CustomDialogFragment");
    }
}