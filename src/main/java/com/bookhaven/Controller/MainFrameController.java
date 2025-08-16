package com.bookhaven.Controller;



import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;


public class MainFrameController {

    private final BookService bookService;
    private final MainFrame mainFrame;
    private final LibraryController libraryController;
    private final ReadingListController readingListController;

    public MainFrameController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.bookService = new BookService();
        this.libraryController = new LibraryController(mainFrame);
        this.readingListController = new ReadingListController(mainFrame,mainFrame.getUserId());
        attachListeners();

    }

    public void attachListeners(){
        mainFrame.getHomeButton().addActionListener( e -> mainFrame.showView("WELCOME"));

        mainFrame.getLibraryButton().addActionListener(e -> {
            // Tell the LibraryController to do its job
            libraryController.loadBooksIntoView();
            mainFrame.showView("LIBRARY");
            new LibraryController(mainFrame).loadBooksIntoView();



        });
        

        mainFrame.getReadingListButton().addActionListener(e ->{
           // here the functionalities to be written




            readingListController.loadUserReadingList();
            mainFrame.showView("READING_LIST");
            new ReadingListController(mainFrame, mainFrame.getUserId());

        });


        mainFrame.getProfileButton().addActionListener(e->{


            mainFrame.showView("PROFILE");
        });
    }

}
