package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.d4i.tutorial.controllers.impl.TvShowControllerImpl;
import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.impl.TvShowServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TvShowControllerTestWithMockitoRunner {

	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private TvShowControllerImpl tvShowControllerImpl;

	@Mock
	private TvShowServiceImpl tvShowServiceImpl;

	@BeforeClass
	public static void runBeforeAllTestOfThisClass() {
		System.out.println("Initialicing Tests");
	}

	@Before
	public void setUpMockMvc() {
		MockitoAnnotations.openMocks(this);
		MockMvcBuilders.standaloneSetup(tvShowControllerImpl).build();
	}

	@Before
	public void runBeforeIndividualTestOfThisClass() throws Exception {
		System.out.println("Initialicing test by test");

	}

	@Test
	public void buscarPorIdTest() throws Exception {
		// GIVEN:
		TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
		TvShowRest tvRest = modelMapper.map(tvShow1, TvShowRest.class);

		// WHEN:
		when(tvShowServiceImpl.getTvShowById(1L)).thenReturn(tvRest);
		NetflixResponse<TvShowRest> nr = tvShowControllerImpl.getTvShowById(1L);

		// THEN:
		assertAll(() -> assertEquals("Vikings", nr.getData().getName()));
	}

	@Test
	public void addCategoryTest() throws Exception {
		// GIVEN:
		TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
		TvShowRest tvRest = modelMapper.map(tvShow1, TvShowRest.class);
		
		Category ca = new Category(5L, "Thriller");
		List<CategoryRest> listCategories = new ArrayList<>();
		listCategories.add(modelMapper.map(ca, CategoryRest.class));
		tvRest.setCategories(listCategories);

		// WHEN:
		when(tvShowServiceImpl.insertNewCategory(1L, 5L)).thenReturn(tvRest);
		NetflixResponse<TvShowRest> nr = tvShowControllerImpl.addCategoryInTvShow(1L, 5L);

		// THEN:
		assertAll(() -> assertEquals(listCategories, nr.getData().getCategories()));
	}
	
	@Test
	public void updateNameTest() throws Exception {
		// GIVEN:
		TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
		TvShowRest tvRest = modelMapper.map(tvShow1, TvShowRest.class);
		tvRest.setName("Los Vikingos");

		// WHEN:
		when(tvShowServiceImpl.updateNameTvShow(1L, "Los Vikingos" )).thenReturn(tvRest);
		NetflixResponse<TvShowRest> nr = tvShowControllerImpl.updateNameTvShow(1l, "Los Vikingos");

		// THEN:
		assertAll(() -> assertEquals("Los Vikingos", nr.getData().getName()));
	}
	
	@Test
	public void deleteTvShowTest() throws Exception {
		// GIVEN:
		TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
		TvShowRest tvRest = modelMapper.map(tvShow1, TvShowRest.class);

		// WHEN:
		when(tvShowServiceImpl.getTvShowById(1L)).thenReturn(tvRest);
		NetflixResponse<TvShowRest> nr1 = tvShowControllerImpl.getTvShowById(1L);

		// THEN:
		assertAll(() -> assertEquals("Vikings", nr1.getData().getName()));
	
		NetflixResponse<TvShowRest> nr = tvShowControllerImpl.deleteTvShow(1L);
		assertEquals("204 NO_CONTENT", nr.getCode());		
	}
	


	

}
