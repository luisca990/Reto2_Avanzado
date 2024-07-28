package com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Implementations;

import static android.app.Activity.RESULT_OK;
import static com.example.proyectate.Utils.Util.showDatePickerDialog;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.DataAccess.DatabaseSQLite.Daos.ProjectDao;
import com.example.proyectate.DataAccess.SharedPreferences.SessionManager;
import com.example.proyectate.Models.Project;
import com.example.proyectate.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdateView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.example.proyectate.Utils.DialogueGenerico;
import com.example.proyectate.Utils.Util;
import com.example.proyectate.databinding.FragmentAddUpdateBinding;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddUpdateFragment extends BaseFragment {
    private AddUpdatePresenter presenter;
    private Project project;
    private ProjectDao dao;
    private String image;
    private FragmentAddUpdateBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUpdateBinding.inflate(getLayoutInflater());
        setCustomView(binding.getRoot());
        dao = new ProjectDao(getContext());
        presenter = new AddUpdatePresenter(new listenerPresenter(), getContext(), dao);

        if (getArguments() != null) {
            Project item = getArguments().getParcelable(Constants.Tag.PROJECT);
            if (item != null) {
                this.project = item;
                fillDataFields();
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.ivBackAddUpdate.setOnClickListener(v->Navigation.findNavController(requireView()).navigateUp());
        binding.buttonDatePickerInit.setOnClickListener(v-> showDatePickerDialog(getContext(), 0, new listenerDatePicker()));
        binding.buttonDatePickerEnd.setOnClickListener(v-> showDatePickerDialog(getContext(), 1, new listenerDatePicker()));
        binding.ivCameraAddUpdate.setOnClickListener(v->checkAndroidVersionAndOpenGallery());
        binding.btnAddUpdate.setOnClickListener(v->{
            SessionManager sessionManager = new SessionManager(requireContext());
            Project item = new Project(
                    binding.etTitle.getText().toString(),
                    binding.etDescript.getText().toString(),
                    binding.buttonDatePickerInit.getText().toString(),
                    binding.buttonDatePickerEnd.getText().toString()
            );
            item.setUserId(sessionManager.getUseId());
            item.setImage(image);
            if (project == null){
                presenter.insertProject(item);
                return;
            }

            item.setId(project.getId());
            project = item;
            presenter.updateProject(item);
        });
    }

    @SuppressLint("SetTextI18n")
    private void fillDataFields(){
        binding.tvTitleAddUpdate.setText(getString(R.string.actualizar_producto));
        binding.etTitle.setText(project.getTitle());
        binding.etTitle.setFocusable(false);
        if (project.getImage() != null) {
            try {
                Uri uri = Uri.parse(project.getImage());
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.ivImageAddUpdate.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            } catch (Exception e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            }
        }
        binding.etDescript.setText(project.getDescription());
        binding.buttonDatePickerInit.setText(project.getDateEnd());
        binding.buttonDatePickerEnd.setText(project.getDateInit());
    }

    private void emptyFields(){
        binding.etTitle.setText("");
        binding.etDescript.setText("");
        binding.buttonDatePickerInit.setText("");
        binding.buttonDatePickerEnd.setText("");
    }

    private void checkAndroidVersionAndOpenGallery() {
        // Verifica si la versión es igual o superior a Android 11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Para Android 11 o superior
            checkPermissionAndOpenGallery();
        } else {
            // Para versiones anteriores
            checkPermissionAndOpenGalleryLower();
        }
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                openGallery();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                startActivity(intent);
            }
        }
    }

    private void checkPermissionAndOpenGalleryLower() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), getString(R.string.permiso_denegado), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                binding.ivImageAddUpdate.setImageURI(selectedImageUri);
                image = selectedImageUri.toString();
            }
        }
    }

    private class listenerPresenter implements IAddUpdateView {

        @Override
        public void showInsertProduct(int id, String name) {
            dialogueFragment(R.string.insertar_usuario, getString(R.string.insert_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            emptyFields();
            Navigation.findNavController(requireView()).navigateUp();
        }

        @Override
        public void showUpdateProduct(int id, String name) {
            dialogueFragment(R.string.update_usuario, getString(R.string.update_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Tag.PROJECT, project);
            Navigation.findNavController(requireView()).navigate(R.id.action_addUpdateFragment_to_detailFragment, bundle);
        }

        @Override
        public void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, message, type);
        }
    }

    private class listenerDatePicker implements Util.OnDateSelectedListener {
        @Override
        public void onDateSelected(String date, int type) {
            if (type == 0) {
                binding.buttonDatePickerInit.setText(date);
                return;
            }
            binding.buttonDatePickerEnd.setText(date);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}