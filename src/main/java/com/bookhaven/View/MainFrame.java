package com.bookhaven.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.bookhaven.Model.User;

/**
 * The MainFrame is the primary window of the application after login.
 * It acts as a pure "View" component. Its only job is to display the UI.
 * It contains no event-handling logic itself.
 * It provides public getters for its interactive components so that a Controller
 * can access them and attach the necessary logic.
 */
public class MainFrame extends JFrame{
    private String userName;
    private int userId;

    // --- Layout and View Management ---
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BookDetailsView bookDetailsView;
    private ReadingListView readingListView;
    private ReaderView readerView;
    private ProfileView profileView;
    private WelcomeView welcomeView;
    private LibraryView libraryView;

    // the navigation button
    private CustomJButton homeButton;
    private CustomJButton libraryButton;
    private CustomJButton readingListButton;
    private CustomJButton profileButton;






    public MainFrame(User user) {
        // basic setup
        this.userName = user.getFirstName();
        this.userId = user.getUserId();
        setTitle("BookHaven Library");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        initToolBar();
        initViews();
    }


    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        homeButton = new CustomJButton("Home");
        libraryButton = new CustomJButton("Library");
        readingListButton = new CustomJButton("Reading List");
        profileButton = new CustomJButton("Profile");


        CustomJButton[] buttons = {homeButton, libraryButton, readingListButton, profileButton};
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        for (CustomJButton button : buttons) {
            button.setFont(buttonFont);
            button.setFocusable(false);
        }


        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(homeButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(libraryButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(readingListButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(profileButton);
        toolBar.add(Box.createHorizontalGlue());

        add(toolBar, BorderLayout.NORTH);
    }


    // adding all the view for the main franw
    private void initViews() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create instances of all the views this frame will manage
        welcomeView = new WelcomeView(userName);
        libraryView = new LibraryView();
        readerView = new ReaderView();
        readingListView = new ReadingListView();
        bookDetailsView = new BookDetailsView();
        profileView = new ProfileView();

        mainPanel.add(welcomeView, "WELCOME");
        mainPanel.add(libraryView, "LIBRARY");
        mainPanel.add(readingListView, "READING_LIST");
        mainPanel.add(profileView, "PROFILE");
        mainPanel.add(readerView, "READER");
        mainPanel.add(bookDetailsView, "BOOK_DETAILS");
        mainPanel.add(readingListView, "READING_LIST");


        add(mainPanel, BorderLayout.CENTER);
    }

    // switching the panels
    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }


    public CustomJButton getHomeButton() {
        return homeButton;
    }

    public CustomJButton getLibraryButton() {
        return libraryButton;
    }

    public CustomJButton getReadingListButton() {
        return readingListButton;
    }

    public CustomJButton getProfileButton() {
        return profileButton;
    }

    public LibraryView getLibraryView() {
        return libraryView;
    }
    public ReaderView getReaderView(){
        return readerView;
    }

    public BookDetailsView getBookDetailsView() {
        return bookDetailsView; }

    public ReadingListView getReadingListView() {
        return readingListView; }


    public int getUserId(){
        return  userId;
    }

    public ProfileView getProfileView() {
        return profileView; }


}