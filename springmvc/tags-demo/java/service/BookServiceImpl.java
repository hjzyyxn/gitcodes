package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import domain.Book;
import domain.Category;

@Service
public class BookServiceImpl implements BookService {
	
	private List<Category> categories;
	private List<Book> books;
	
	public BookServiceImpl() {
		categories = new ArrayList<Category>();
		Category category1 = new Category(1, "Computer");
		Category category2 = new Category(2, "Travel");
		Category category3 = new Category(3, "Health");
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);
		
		books = new ArrayList<Book>();
		books.add(new Book(1L, "9781771970273",
				"Servlet & JSP: A Tutorial (2nd Edition)",
				category1, "Budi Kurniawan", new BigDecimal("54.99")));
		books.add(new Book(2L, "9781771970297",
				"C#: A Beginner's Tutorial (2nd Edition) ",
				category1, "jayden Ky", new BigDecimal("39.99")));
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categories;
	}

	@Override
	public Category getCategory(int id) {
		// TODO Auto-generated method stub
		for (Category category : categories) {
			if (id == category.getId()) {
				return category;
			}
		}
		return null;
	}

	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return books;
	}

	@Override
	public Book save(Book book) {
		// TODO Auto-generated method stub
		book.setId(getNextId());
		books.add(book);
		return book;
	}

	@Override
	public Book update(Book book) {
		// TODO Auto-generated method stub
		int bookCount = books.size();
		for (int i = 0; i < bookCount; i++) {
			Book savedBook = books.get(i);
			if (savedBook.getId() == book.getId()) {
				books.set(i, book);
				return book;
			}
		}
		return book;
	}

	@Override
	public Book get(long id) {
		// TODO Auto-generated method stub
		for (Book book : books) {
			if (id == book.getId()) {
				return book;
			}
		}
		return null;
	}

	@Override
	public long getNextId() {
		// TODO Auto-generated method stub
		long id = 0L;
		for (Book book : books) {
			long bookId = book.getId();
			if (bookId > id) {
				id = bookId;
			}
		}
		return id + 1;
	}

}
