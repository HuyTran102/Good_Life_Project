package com.example.goodlife;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentDiary extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_diary, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);

        List<DiaryItem> items = new ArrayList<>();

        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));
        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));
        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));
        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));
        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));
        items.add(new DiaryItem("Sữa chưa TH việt quất", 100, 64, 11, 1.6, 1.6, "(g)", "Khối lượng"));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DiaryViewAdapter(getContext(), items));

    }
}