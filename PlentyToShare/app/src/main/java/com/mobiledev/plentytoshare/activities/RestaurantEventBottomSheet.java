package com.mobiledev.plentytoshare.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobiledev.plentytoshare.R;

public class RestaurantEventBottomSheet extends BottomSheetDialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.bottom_sheet_update_event, container, false);
    }
}
