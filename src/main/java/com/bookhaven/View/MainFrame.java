package com.bookhaven.View;

import com.bookhaven.Model.User;

import javax.swing.*;
import java.awt.*;

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

    // --- UI Components that need to be accessed by a Controller ---
    private CustomJButton homeButton;
    private CustomJButton libraryButton;
    private CustomJButton readingListButton;
    private CustomJButton profileButton;

    // --- References to the different View Panels ---
    private WelcomeView welcomeView;
    private LibraryView libraryView;
//    private JPanel readingListView; // Placeholder
    private ProfileView profileView;
    private ReaderView readerView;// Placeholder

    public MainFrame(User user) {
        // --- 1. Basic Window Setup ---
        this.userName = user.getFirstName();
        this.userId = user.getUserId();
        setTitle("BookHaven Library");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 2. Initialize the UI components ---
        initToolBar();
        initViews();
    }

    /**
     * Creates and configures the top navigation toolbar and its buttons.
     */
    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Initialize the button fields using CustomJButton ---
        homeButton = new CustomJButton("Home");
        libraryButton = new CustomJButton("Library");
        readingListButton = new CustomJButton("Reading List");
        profileButton = new CustomJButton("Profile");

        // Use an array to apply consistent styling easily
        CustomJButton[] buttons = {homeButton, libraryButton, readingListButton, profileButton};
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        for (CustomJButton button : buttons) {
            button.setFont(buttonFont);
            button.setFocusable(false);
        }

        // --- Add components to the toolbar for center alignment ---
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

    /**
     * Creates the central panel with CardLayout and adds all the different views.
     */
    private void initViews() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create instances of all the views this frame will manage
        welcomeView = new WelcomeView(userName);
        libraryView = new LibraryView();
        readerView = new ReaderView();

        // Placeholders for views to be developed later
        readingListView = new ReadingListView();

        bookDetailsView = new BookDetailsView();
        profileView = new ProfileView();
        profileView.add(new JLabel("This is your Profile."));

        // Add the views to the main panel with unique String keys
        mainPanel.add(welcomeView, "WELCOME");
        mainPanel.add(libraryView, "LIBRARY");
        mainPanel.add(readingListView, "READING_LIST");
        mainPanel.add(profileView, "PROFILE");
        mainPanel.add(readerView, "READER");
        mainPanel.add(bookDetailsView, "BOOK_DETAILS");
        mainPanel.add(readingListView, "READING_LIST");


        add(mainPanel, BorderLayout.CENTER);
    }

    // --- PUBLIC METHODS FOR THE CONTROLLER ---

    /**
     * Allows the controller to switch the visible panel.
     * This is the only "action" this class performs, and it's on command.
     */
    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }

    // --- GETTERS FOR COMPONENTS ---
    // These methods allow the controller to "get" the components it needs to control.

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

    // You can add getters for other views as controllers need them
}