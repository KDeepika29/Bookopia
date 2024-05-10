package com.book.exchange.platform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.book.exchange.platform.dao.BookDao;
import com.book.exchange.platform.model.Book;
import com.book.exchange.platform.model.BookResponse;
import com.book.exchange.platform.model.Provider;

public class BookService {
	private int id = 1;
	private List<Book> bookList = new ArrayList<Book>();

	private BookDao bookDao = new BookDao();

//	public BookResponse addBook(Book book, String email) {
//		BookResponse bookResponse = new BookResponse();
//		book.setId(id);
//		bookList.add(book);
//		id++;
//		Provider provider = new Provider();
//		provider.setEmail(email);
//		book.setProvider(provider);
//		book.setAvailability(true);
//		bookResponse.setSuccess(true);
//		bookResponse.setMessage("Added book successfully!!");
//		return bookResponse;
//	}
//
//	public List<Book> getBookList(String email) {
//		List<Book> booksList = new ArrayList<>();
//		if (null == email) {
//			return bookList;
//		}
//		if (null != bookList && !bookList.isEmpty()) {
//			for (Book book : bookList) {
//				if (null != book.getProvider() && null != book.getProvider().getEmail()
//						&& book.getProvider().getEmail().equalsIgnoreCase(email)) {
//					booksList.add(book);
//				}
//			}
//		}
//		return booksList;
//	}
//
//	public BookResponse deleteBook(int id) {
//		Iterator<Book> iterator = bookList.iterator();
//		while (iterator.hasNext()) {
//			Book book = iterator.next();
//			if (book.getId() == id) {
//
//				iterator.remove();
//				break;
//			}
//		}
//		BookResponse bookResponse = new BookResponse();
//		bookResponse.setSuccess(true);
//		bookResponse.setMessage("Book has been deleted successfully!!");
//		return bookResponse;
//	}
//
//	public BookResponse updateBook(Book book) {
//		for (Book updatedBook : bookList) {
//			if (book.getId() == updatedBook.getId()) {
//				if (null != book.getTitle()) {
//					updatedBook.setTitle(book.getTitle());
//				}
//				if (null != book.getAuthor()) {
//					updatedBook.setAuthor(book.getAuthor());
//				}
//				if (null != book.getGenre()) {
//					updatedBook.setGenre(book.getGenre());
//				}
//				if (null != book.getCondition()) {
//					updatedBook.setCondition(book.getCondition());
//				}
//				if (null != book.getOperationType()) {
//					updatedBook.setOperationType(book.getOperationType());
//				}
//
//				updatedBook.setAvailability(book.isAvailability());
//
//				break;
//			}
//		}
//		BookResponse bookResponse = new BookResponse();
//		bookResponse.setSuccess(true);
//		bookResponse.setMessage("Book has been Updated successfully!!");
//		return bookResponse;
//	}
//
//	public List<Book> searchBooksByFilters(List<String> genres, String author, String title, String location,
//			boolean availability) {
//		List<Book> filteredBooks = new ArrayList<>();
//
//		for (Book book : bookList) {
//			boolean match = true;
//			if (genres != null && !genres.isEmpty()) {
//	            boolean genreMatch = false;
//	            for (String genre : genres) {
//	                if (book.getGenre().contains(genre)) {
//	                    genreMatch = true;
//	                    break;
//	                }
//	            }
//	            if (!genreMatch) {
//	                match = false;
//	            }
//	        }
//			if (author != null && !author.isEmpty() && !author.equalsIgnoreCase(book.getAuthor())) {
//				match = false;
//			}
//			if (title != null && !title.isEmpty() && !book.getTitle().toLowerCase().contains(title.toLowerCase())) {
//				match = false;
//			}
//			if (null != book.getProvider() && null != book.getProvider().getLocation() && !location.isEmpty()
//					&& !location.equalsIgnoreCase(book.getProvider().getLocation())) {
//				match = false;
//			}
//			if (availability && !book.isAvailability()) {
//				match = false;
//			}
//
//			// Add book to the filtered list if all filters match
//			if (match) {
//				filteredBooks.add(book);
//			}
//		}
//
//		return filteredBooks;
//	}

	public BookResponse addBook(Book book, String email) {
	    BookResponse bookResponse = new BookResponse();
	    Provider provider = new Provider();
	    provider.setEmail(email);
	    book.setProvider(provider);
	    book.setAvailability(true);
	    
	    // Call the method to add the book to the database using bookDao
	    bookDao.addBook(book);
	    
	    bookResponse.setSuccess(true);
	    bookResponse.setMessage("Added book successfully!!");
	    return bookResponse;
	}
	public List<Book> getBookList(String email) {
	    return bookDao.getBooksByEmail(email);
	}

	public BookResponse deleteBook(int id) {
	    bookDao.deleteBook(id);
	    
	    BookResponse bookResponse = new BookResponse();
	    bookResponse.setSuccess(true);
	    bookResponse.setMessage("Book has been deleted successfully!!");
	    return bookResponse;
	}

	public BookResponse updateBook(Book book) {
	    bookDao.updateBook(book);
	    
	    BookResponse bookResponse = new BookResponse();
	    bookResponse.setSuccess(true);
	    bookResponse.setMessage("Book has been updated successfully!!");
	    return bookResponse;
	}

	public List<Book> searchBooksByFilters(List<String> genre, String author, String title, String location,
			boolean availability) {
		return bookDao.searchBooksByFilters(genre, author, title, location, availability);
	}
}
