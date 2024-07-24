package com.example.proyectate.Presentation.AccessAcount.Login.Implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.proyectate.Base.BaseFragment;
import com.example.proyectate.Models.User;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginPresenter;
import com.example.proyectate.Presentation.AccessAcount.Login.Interfaces.ILoginView;
import com.example.proyectate.R;
import com.example.proyectate.Utils.Constants;
import com.example.proyectate.Utils.DialogueGenerico;

import java.util.Objects;

public class LoginFragment extends BaseFragment {
    private Button btnSeccion;
    private TextView btnTextRegister;
    private EditText editEmail;
    private EditText editPass;
    private ILoginPresenter presenter;
    private final actionListenerViewPresenter actionPresenter = new actionListenerViewPresenter();
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_login, container, false));

        btnSeccion = getCustomView().findViewById(R.id.btn_start_section);
        btnTextRegister = getCustomView().findViewById(R.id.tv_register_user);
        editEmail = getCustomView().findViewById(R.id.et_email_login);
        editPass = getCustomView().findViewById(R.id.et_Pass_Login);
        return getCustomView();
    }

    @Override
    public void onResume() {
        presenter = new LoginPresenter(requireContext(), actionPresenter);
        super.onResume();
        btnSeccion.setOnClickListener(v->{
            user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPass.getText().toString());
            presenter.startSection(user);
        });
        btnTextRegister.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private class actionListenerViewPresenter implements ILoginView {

        @Override
        public void responseLogin(@NonNull User user) {
            Toast.makeText(getContext(), getString(R.string.login_user), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            if ((Objects.equals(user.getEmail(), Constants.Tag.ADMIN))) {
                bundle.putString(Constants.Tag.USER, "admin");
            } else {
                bundle.putString(Constants.Tag.USER, "client");
            }
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment, bundle);
        }

        @Override
        public void credentialsIncorrect() {
            dialogueFragment(R.string.credentials_incorrect,
                    getString(R.string.details_credentials_incorrect),
                    DialogueGenerico.TypeDialogue.ERROR);
        }

        @Override
        public void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, getString(detail), type);
        }
    }
}