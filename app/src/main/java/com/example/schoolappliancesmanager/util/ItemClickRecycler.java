package com.example.schoolappliancesmanager.util;

public interface ItemClickRecycler<T> {
    void onLongTouch(T item);
    void onClick(T item);
}
