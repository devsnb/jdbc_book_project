package com.bookapp.main;

import java.util.Scanner;

import com.bookapp.bean.Book;
import com.bookapp.dao.BookImpl;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class Client {

    public static void main(String[] args) {
        BookImpl library = new BookImpl();

        Scanner input = new Scanner(System.in);

//        for (int i = 0; i < 2; i++) {
//            System.out.println("Book Title :");
//            String title = input.next();
//
//            System.out.println("Author Name :");
//            String author = input.next();
//
//            System.out.println("Category :");
//            String category = input.next();
//
//            System.out.println("Book ID :");
//            int bookId = input.nextInt();
//
//            System.out.println("Book Price :");
//            int price = input.nextInt();
//
//            Book myBook = new Book(title, author, category, bookId, price);
//
//            library.addBook(myBook);
//        }

        while (true) {
            int choice;

            System.out.println("To print all books press 1");
            System.out.println("To get books by author press 2");
            System.out.println("To get books by category press 3");
            System.out.println("To get book by id press 4");
            System.out.println("To update a book press 5");
            System.out.println("To exit the program press 6");

            choice = input.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println(library.getAllBooks());
                    break;
                }
                case 2: {
                    try {
                        System.out.println("Enter Author Name");
                        String author = input.next();
                        System.out.println(library.getBookbyAuthor(author));
                    } catch (AuthorNotFoundException e) {
                        System.out.println("Author could not be found");
                    }
                    break;
                }
                case 3: {
                    try {
                        System.out.println("Enter category :");
                        String category = input.next();
                        System.out.println(library.getBookbycategory(category));
                    } catch (CategoryNotFoundException e) {
                        System.out.println("Category could not be found");
                    }
                    break;
                }
                case 4: {
                    System.out.println("Enter book Id:");
                    int bookid = input.nextInt();
                    try {
                        System.out.println(library.getBookById(bookid));
                    } catch (BookNotFoundException e) {
                        System.out.println("Requested book could not be found");
                    }
                }
                case 5: {
                    System.out.println("Enter book Id:");
                    int bookID = input.nextInt();
                    System.out.println("Enter Price");
                    int price = input.nextInt();
                    try {
                        library.updateBook(bookID, price);
                        System.out.println("Book updated successfully");
                    } catch (Exception e) {
                        System.out.println("Something went wrong!");
                    }
                }
                case 6: {
                    System.exit(1);
                }
                default:
                    System.out.println("Enter a valid input");
                    break;

            }
        }
    }
}
