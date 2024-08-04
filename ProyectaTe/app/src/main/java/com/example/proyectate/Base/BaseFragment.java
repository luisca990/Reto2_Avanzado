package com.example.proyectate.Base;

import static com.example.proyectate.Utils.Util.hideKeyboard;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.proyectate.R;
import com.example.proyectate.Utils.DialogueGenerico;
import com.example.proyectate.Utils.Util;

public class BaseFragment extends Fragment {

    private View view;

    public View getCustomView() {
        return view;
    }
    public void setCustomView(View view) {
        this.view = view;
    }

    public void dialogueFragment(int title, String detail, DialogueGenerico.TypeDialogue type) {
        DialogueGenerico.getInstance()
                .withTitle(title)
                .withText(detail)
                .withTypeDialogue(type)
                .withTextBtnAccept(R.string.btn_aceptar)
                .withActionBtnAccept(() -> Log.e("", ""));

        if (getContext() != null) {
            Util.showDialogueGenerico(getContext());
        }
    }

    public void hideKeyboardFragment(){
        hideKeyboard(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboardFragment();
            }
            return false; // Devuelve false para que otros eventos táctiles sigan siendo manejados
        });
    }
}
