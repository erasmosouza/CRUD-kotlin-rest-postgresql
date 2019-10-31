package esp.quickstart.demo.book

import esp.quickstart.demo.category.Category
import esp.quickstart.demo.category.CategoryRepository
import esp.quickstart.demo.category.CategoryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val repository: BookRepository, private val categoryService: CategoryService) {

    fun all(): List<Book> = repository.findAll().toList()

    fun allByPagination(page: Int, size: Int): List<Book> {
        val pag: Pageable = PageRequest.of(page, size);
        return repository.findAll(pag).toList()
    }

    fun findById(id: Long): Optional<Book> = repository.findById(id)

    fun existsById(id: Long): Boolean = repository.existsById(id)

    fun save(book: Book): Book {


        println("=====> Valor do Form: ${book.category.id}")

        val category: Optional<Category> = categoryService.findById(book.category.id)
        book.category = category.get()

        return repository.save(book)
    }

    fun update(id: Long, book: Book): Book {

        val category: Optional<Category> = categoryService.findById(book.category.id)

        val safeBook: Book = repository.findById(id).get()

        safeBook.title = book.title
        safeBook.description = book.description
        safeBook.author = book.author
        safeBook.category = category.get()

        return this.save(safeBook)
    }

    fun deleteById(id: Long) = repository.deleteById(id)
}