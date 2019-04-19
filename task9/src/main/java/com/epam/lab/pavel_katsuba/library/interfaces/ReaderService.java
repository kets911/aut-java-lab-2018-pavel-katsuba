package com.epam.lab.pavel_katsuba.library.interfaces;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;

import java.util.List;

public interface ReaderService {

    Reader getReaderById(int id);

    void takeBooks(int id, List<Integer> bookIds);

    void returnBook(int id, int[] checkboxValues);

    List<Reader> getAllReaders();

    void deleteReader(int id);

    void changeReader(int id, Reader reader);

    void createReader(Reader build);

    Reader getReaderByName(String username);
}
