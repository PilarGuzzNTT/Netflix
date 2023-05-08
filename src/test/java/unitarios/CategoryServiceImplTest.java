package unitarios;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = Category.class)
public class CategoryServiceImplTest {

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;

	@Mock
	CategoryRepository categoryRepository;

	private static Category category1;

	@BeforeEach
	public void setUp() {
		CategoryServiceImplTest.category1 = new Category(1L, "Categoria 1");
	}

	@Test
	public void testGetCategories() throws NetflixException {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category(1L, "categoria 1"));
		categoryList.add(new Category(2L, "categoria 2"));
		categoryList.add(new Category(3L, "categoria 3"));
		categoryList.add(new Category(4L, "categoria 4"));
		categoryList.add(new Category(5L, "categoria 5"));

		when(categoryRepository.findAll()).thenReturn((categoryList));

		List<CategoryRest> categoryRestList = categoryServiceImpl.getCategories();
		assertNotNull(categoryRestList);
		assertEquals(5, categoryRestList.size());
		assertEquals("categoria 4", categoryRestList.get(3).getName());
		assertNotEquals("categoria 4", categoryRestList.get(2).getName());
	}
}
