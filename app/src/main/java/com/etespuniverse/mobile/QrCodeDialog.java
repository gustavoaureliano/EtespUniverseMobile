package com.etespuniverse.mobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class QrCodeDialog extends DialogFragment {

    private TextView lblDesc, lblIdIngresso;
    private ImageView imgQrCode;
    private Ingresso ingresso = new Ingresso();
    private Bitmap qrCode;
    private View dialogView;
    private Button btnClose;

    public QrCodeDialog(Ingresso ingresso, Bitmap qrCode) {
        this.ingresso = ingresso;
        this.qrCode = qrCode;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("TAG", "onCreateDialog");
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext(), getTheme());
        dialogView = this.onCreateView(LayoutInflater.from(requireContext()), null, savedInstanceState);
        builder.setView(dialogView);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return dialogView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_qrcode, container, false);

        lblDesc = view.findViewById(R.id.lblDesc);
        lblIdIngresso = view.findViewById(R.id.lblIdIngresso);
        imgQrCode = view.findViewById(R.id.imgQrCode);
        btnClose = view.findViewById(R.id.btnClose);

        lblDesc.setText(ingresso.getDescricao());
        lblIdIngresso.setText("#"+ingresso.getIdIngresso());
        //lblIdIngresso.setText("#123");
        imgQrCode.setImageBitmap(qrCode);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("TAG", "Dismissed");
    }
}
