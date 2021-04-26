package com.example.schoolappliancesmanager.util;

public interface ItemClickRecycler<T> {
    void delete(T item);
    void edit(T item);
}
