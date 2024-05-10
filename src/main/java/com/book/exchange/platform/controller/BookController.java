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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.book.exchange.platform.model.Book;
import com.book.exchange.platform.model.BookResponse;
import com.book.exchange.platform.service.BookService;
import com.book.exchange.platform.utils.JwtTokenGenerator;

@Path("/books")
public class BookController {

	private BookService bookService = new BookService();

	@POST
	@Path("/add-book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(@Context HttpHeaders headers, Book book) {
		String token = headers.getHeaderString("token");
		String email = JwtTokenGenerator.getSubjectFromJwtToken(token);
		BookResponse response = bookService.addBook(book, email);
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
	public Response getBooks(@Context HttpHeaders headers) {

		String email = null;
		if (null != headers) {
			String token = headers.getHeaderString("token");
			if (null != token) {
				email = JwtTokenGenerator.getSubjectFromJwtToken(token);
			}

		}

		List<Book> response = bookService.getBookList(email);
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

	@GET
	@Path("/search-book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBooksByFilters(@QueryParam("genre") List<String> genre, @QueryParam("author") String author,
			@QueryParam("title") String title, @QueryParam("location") String location,
			@QueryParam("availability") boolean availability) {
		List<Book> response = bookService.searchBooksByFilters(genre, author, title, location, availability);
		return Response.status(200).entity(response).build();
	}
}
