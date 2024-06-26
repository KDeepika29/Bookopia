package com.book.exchange.platform.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.book.exchange.platform.model.Book;
import com.book.exchange.platform.model.BookResponse;
import com.book.exchange.platform.service.BookService;

@Path("/books")
public class BookController {

	private BookService bookService = new BookService();

	@POST
	@Path("/add-book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(Book book) {
		BookResponse response = bookService.addBook(book);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(400).entity("Unable to add the book").build();
		}

	}

	@GET
	@Path("/book-list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks() {
		List<Book> response = bookService.getBookList();
		return Response.status(200).entity(response).build();
	}

	@PUT
	@Path("/update-book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(Book book) {
		BookResponse response = bookService.updateBook(book);
		return Response.status(200).entity(response).build();
	}

	@DELETE
	@Path("/delete-book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBook(@QueryParam("id") int id) {
		BookResponse response = bookService.deleteBook(id);
		return Response.status(200).entity(response).build();
	}
}
