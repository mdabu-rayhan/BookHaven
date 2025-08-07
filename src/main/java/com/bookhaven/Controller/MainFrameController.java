package com.bookhaven.Controller;


import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;

import java.util.List;

public class MainFrameController {

    private BookService bookService;
    private MainFrame mainFrame;
    private LibraryController libraryController;

    public MainFrameController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.bookService = new BookService();
        this.libraryController = new LibraryController(mainFrame);
        attachListeners();

    }

    public void attachListeners(){
        mainFrame.getHomeButton().addActionListener( e ->{
            mainFrame.showView("WELCOME");
        });

        mainFrame.getLibraryButton().addActionListener(e -> {
            // Tell the LibraryController to do its job
            libraryController.loadBooksIntoView();
            mainFrame.showView("LIBRARY");

        });

        mainFrame.getReadingListButton().addActionListener(e ->{
           // here the functionalities to be written

            mainFrame.showView("READING_LIST");
        });


        mainFrame.getProfileButton().addActionListener(e->{
            // same

            mainFrame.showView("PROFILE");
        });
    }

}
