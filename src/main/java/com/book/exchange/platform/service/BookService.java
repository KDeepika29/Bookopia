package com.book.exchange.platform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.book.exchange.platform.model.Book;
import com.book.exchange.platform.model.BookResponse;
import com.book.exchange.platform.model.Provider;

public class BookService {
	private int id = 1;
	private List<Book> bookList = new ArrayList<Book>();

	public BookResponse addBook(Book book, String email) {
		BookResponse bookResponse = new BookResponse();
		book.setId(id);
		bookList.add(book);
		id++;
		Provider provider = new Provider();
		provider.setEmail(email);
		book.setProvider(provider);
		bookResponse.setSuccess(true);
		bookResponse.setMessage("Added book successfully!!");
		return bookResponse;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public BookResponse deleteBook(int id) {
		Iterator<Book> iterator = bookList.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (book.getId() == id) {

				iterator.remove();
				break;
			}
		}
		BookResponse bookResponse = new BookResponse();
		bookResponse.setSuccess(true);
		bookResponse.setMessage("Book has been deleted successfully!!");
		return bookResponse;
	}

	public BookResponse updateBook(Book book) {
		for (Book updatedBook : bookList) {
			if (book.getId() == updatedBook.getId()) {
				if (null != book.getTitle()) {
					updatedBook.setTitle(book.getTitle());
				}
				if (null != book.getAuthor()) {
					updatedBook.setAuthor(book.getAuthor());
				}
				if (null != book.getGenre()) {
					updatedBook.setGenre(book.getGenre());
				}
				if (null != book.getCondition()) {
					updatedBook.setCondition(book.getCondition());
				}
				if (null != book.getOperationType()) {
					updatedBook.setOperationType(book.getOperationType());
				}

				updatedBook.setAvailability(book.isAvailability());

				break;
			}
		}
		BookResponse bookResponse = new BookResponse();
		bookResponse.setSuccess(true);
		bookResponse.setMessage("Book has been Updated successfully!!");
		return bookResponse;
	}
}
