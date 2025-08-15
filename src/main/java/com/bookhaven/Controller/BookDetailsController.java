package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.BookDetailsView;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReadingListView;

import javax.swing.*;
import java.util.List;

public class BookDetailsController {

    private BookDetailsView bookDetailsView;
    private MainFrame mainFrame;
    private BookService bookService;
    private int userId;

    public BookDetailsController(MainFrame mainFrame, int userId){
        this.mainFrame = mainFrame;
        this.bookDetailsView = mainFrame.getBookDetailsView();
        this.bookService = new BookService();
        this.userId = mainFrame.getUserId();

        attachListeners();
    }



    private void attachListeners(){
        bookDetailsView.addAddToReadingListListener(e->{
            System.out.println("Working Properly");
            //getting the book object for the current book
            Book book = bookDetailsView.getCurrentBook();

            // adding books to the reading list database
            boolean success = bookService.addBookToUserList(userId,book.getBookId());


            if(success){
                bookDetailsView.setInReadingList(true);
                List <Book> readingList = bookService.getBooksForUser(userId);
                // creating a list for the user from the table reading list
                ReadingListView readingListView = mainFrame.getReadingListView();
                //fetching the readinglist view and adding the booklist to the view
                mainFrame.getReadingListView().displayBooks(readingList);


            } else{
                JOptionPane.showMessageDialog(mainFrame, "Could not add book to the reading list.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bookDetailsView.addRemoveFromReadingListListener( e->{
            Book book = bookDetailsView.getCurrentBook();
            boolean success = bookService.removeBookFromUserList(userId,book.getBookId());



            if(success){
                bookDetailsView.setInReadingList(false);
                List <Book> readlingList = bookService.getBooksForUser(userId);
                mainFrame.getReadingListView().displayBooks(readlingList);
            } else {
                JOptionPane.showMessageDialog(mainFrame,"Could not remove book from the readding list. ",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
