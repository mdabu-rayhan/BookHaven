package com.bookhaven.Controller;



import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;


public class MainFrameController {

    private final BookService bookService;
    private final MainFrame mainFrame;
    private final LibraryController libraryController;
    private final ReadingListController readingListController;
    private final ProfileController profileController;

    public MainFrameController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.bookService = new BookService();
        this.libraryController = new LibraryController(mainFrame);
        this.readingListController = new ReadingListController(mainFrame,mainFrame.getUserId());
        this.profileController = new ProfileController(mainFrame);
        attachListeners();

    }

    public void attachListeners(){
        mainFrame.getHomeButton().addActionListener( e -> mainFrame.showView("WELCOME"));

        mainFrame.getLibraryButton().addActionListener(e -> {
            libraryController.loadBooksIntoView();
            mainFrame.showView("LIBRARY");
            new LibraryController(mainFrame).loadBooksIntoView();

        });
        

        mainFrame.getReadingListButton().addActionListener(e ->{

            readingListController.loadUserReadingList();
            mainFrame.showView("READING_LIST");
            new ReadingListController(mainFrame, mainFrame.getUserId());

        });


        mainFrame.getProfileButton().addActionListener(e->{
            profileController.loadProfile();
            mainFrame.showView("PROFILE");
        });
    }

}
