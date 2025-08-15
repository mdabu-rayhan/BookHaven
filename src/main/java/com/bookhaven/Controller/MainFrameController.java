package com.bookhaven.Controller;


import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;

import java.util.List;

public class MainFrameController {

    private BookService bookService;
    private MainFrame mainFrame;
    private LibraryController libraryController;
    private ReadingListController readingListController;

    public MainFrameController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.bookService = new BookService();
        this.libraryController = new LibraryController(mainFrame);
        this.readingListController = new ReadingListController(mainFrame,bookService,mainFrame.getUserId());
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
            new LibraryController(mainFrame).loadBooksIntoView();



        });
        

        mainFrame.getReadingListButton().addActionListener(e ->{
           // here the functionalities to be written

            mainFrame.showView("READING_LIST");
            readingListController.loadUserReadingList();
        });


        mainFrame.getProfileButton().addActionListener(e->{
            // same

            mainFrame.showView("PROFILE");
        });
    }

}
