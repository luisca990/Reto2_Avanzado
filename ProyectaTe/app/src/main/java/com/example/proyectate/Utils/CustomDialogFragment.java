package com.example.proyectate.Utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.proyectate.R;

public class CustomDialogFragment extends DialogFragment {

    private final String title;
    private final String description;
    private final Runnable onAccept;
    private final Runnable onCancel;

    public CustomDialogFragment(String title, String description, Runnable onAccept, Runnable onCancel) {
        this.title = title;
        this.description = description;
        this.onAccept = onAccept;
        this.onCancel = onCancel;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflar el diseño del diálogo
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        // Configurar título y descripción
        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        TextView descriptionTextView = dialogView.findViewById(R.id.dialog_description);

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // Configurar el diálogo
        return new AlertDialog.Builder(requireActivity())
                .setView(dialogView) // Establecer el diseño personalizado
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (onAccept != null) {
                        onAccept.run();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .create();
    }
}
