package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.BookDetailsView;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReadingListView;

import javax.swing.*;
import java.util.List;

// BookDetailsController: wires buttons in details view to ReadingListService (-> DAO) and updates views
public class BookDetailsController {

    private BookDetailsView bookDetailsView;
    private MainFrame mainFrame;
    private ReadingListService readingListService;
    private int userId;

    public BookDetailsController(MainFrame mainFrame, int userId){
        this.mainFrame = mainFrame;
        this.bookDetailsView = mainFrame.getBookDetailsView();
        this.readingListService = new ReadingListService();
        this.userId = mainFrame.getUserId();
        attachListeners();
    }

    private void attachListeners(){
        bookDetailsView.addToReadingListListener(e -> {
            Book book = bookDetailsView.getCurrentBook();
            boolean success = readingListService.addBookToReadingList(userId, book.getBookId());
            if(success){
                bookDetailsView.setInReadingList(true);
                List<Book> readingList = readingListService.getUserReadingList(userId);
                ReadingListView readingListView = mainFrame.getReadingListView();
                readingListView.displayBooks(readingList);
            } else{
                JOptionPane.showMessageDialog(mainFrame, "Could not add book to the reading list.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bookDetailsView.removeFromReadingListListener(e -> {
            Book book = bookDetailsView.getCurrentBook();
            boolean success = readingListService.removeBookFromReadingList(userId, book.getBookId());
            if(success){
                bookDetailsView.setInReadingList(false);
                List<Book> readingList = readingListService.getUserReadingList(userId);
                mainFrame.getReadingListView().displayBooks(readingList);
            } else {
                JOptionPane.showMessageDialog(mainFrame,"Could not remove book from the reading list.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
