package com.bookapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bookapp.bean.Book;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class BookImpl implements BookInter {
    public BookImpl() {
        Connection conn = ModelDAO.openConnection();

        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "books", null);

            if (!tables.next()) {
                String SQLQuery = "CREATE TABLE books(title VARCHAR(255), author VARCHAR(255), category VARCHAR(255), price INT, bookid INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(bookid));";

                conn.prepareStatement(SQLQuery).execute();
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Book book) {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "INSERT INTO books (title, author, category, bookid, price) VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setInt(4, book.getBookid());
            statement.setInt(5, book.getPrice());

            statement.execute();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteBook(int bookid) throws BookNotFoundException {
        Connection conn = ModelDAO.openConnection();
        String SQLSelectQuery = "SELECT * FROM books WHERE bookid=?";

        try {
            PreparedStatement selectStatement = conn.prepareStatement(SQLSelectQuery);
            selectStatement.setInt(1, bookid);

            ResultSet result = selectStatement.executeQuery();

            if (!result.next()) {
                throw new BookNotFoundException();
            }

            String SQLDeleteQuery = "DELETE FROM books WHERE bookid=?";
            PreparedStatement deleteStatement = conn.prepareStatement(SQLDeleteQuery);
            deleteStatement.setInt(1, bookid);
            deleteStatement.execute();

            System.out.println("Book was deleted successfully");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book getBookById(int bookId) throws BookNotFoundException {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "SELECT * FROM books WHERE bookid=?;";

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);
            statement.setInt(1, bookId);

            ResultSet result = statement.executeQuery();
            Book book = null;

            while (result.next()) {
                String title = result.getString("title");
                String author = result.getString("author");
                String category = result.getString("category");
                int bookid = result.getInt("bookid");
                int price = result.getInt("price");

                book = new Book(title, author, category, bookid, price);
            }

            if (book == null) {
                throw new BookNotFoundException();
            }

            conn.close();
            return book;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateBook(int bookid, int price) {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "UPDATE books SET price=? WHERE bookid=?";

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);

            statement.setInt(1, price);
            statement.setInt(2, bookid);

            statement.execute();

            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "SELECT * FROM books;";

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);

            ResultSet result = statement.executeQuery();

            List<Book> bookList = new ArrayList<>();

            while (result.next()) {
                String title = result.getString("title");
                String author = result.getString("author");
                String category = result.getString("category");
                int bookid = result.getInt("bookid");
                int price = result.getInt("price");

                Book firstBook = new Book(title, author, category, bookid, price);

                bookList.add(firstBook);
            }

            conn.close();

            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Book> getBookbyAuthor(String author) throws AuthorNotFoundException {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "SELECT * FROM books WHERE author=?;";

        List<Book> bookList = new ArrayList<>();

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);
            statement.setString(1, author);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String title = result.getString("title");
                String bAuthor = result.getString("author");
                String category = result.getString("category");
                int bookid = result.getInt("bookid");
                int price = result.getInt("price");

                Book foundBook = new Book(title, bAuthor, category, bookid, price);
                bookList.add(foundBook);
            }

            if (bookList.isEmpty()) {
                throw new AuthorNotFoundException();
            }

            conn.close();

            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBookbycategory(String category) throws CategoryNotFoundException {
        Connection conn = ModelDAO.openConnection();

        String SQLQuery = "SELECT * FROM books WHERE category=?;";

        List<Book> bookList = new ArrayList<>();

        try {
            PreparedStatement statement = conn.prepareStatement(SQLQuery);
            statement.setString(1, category);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String title = result.getString("title");
                String bAuthor = result.getString("author");
                String bCategory = result.getString("category");
                int bookid = result.getInt("bookid");
                int price = result.getInt("price");

                Book foundBook = new Book(title, bAuthor, bCategory, bookid, price);

                bookList.add(foundBook);
            }

            if (bookList.isEmpty()) {
                throw new CategoryNotFoundException();
            }

            conn.close();

            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
