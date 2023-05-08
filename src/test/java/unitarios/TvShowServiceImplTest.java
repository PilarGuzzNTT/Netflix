package unitarios;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.impl.TvShowServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TvShow.class)
public class TvShowServiceImplTest {

	@InjectMocks
	TvShowServiceImpl tvShowServiceImpl;
	
    @Mock
    TvShowRepository tvShowRepository;

    @Mock
    CategoryRepository categoryRepository;


    private ModelMapper modelMapper = new ModelMapper();




    @Test
    public void testGetTvShowById() throws NetflixException {
        List<TvShow> tvshowList = new ArrayList<>();
        TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series.",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);

		TvShow tvShow2 = new TvShow(2L, "Naruto",
				"It tells the story of Naruto Uzumaki, a young ninja who seeks recognition from his peers and "
						+ "dreams of becoming the Hokage, the leader of his village.",
				"The story is told in two parts—the first set in Naruto's pre-teen years, and the second in his teens. "
						+ "The series is based on two one-shot manga by Kishimoto: Karakuri (1995), and Naruto",
				Year.of(2002), (byte) 0);
		TvShow tvShow3 = new TvShow(3L, "Demon Slayer",
				"It follows teenage Tanjiro Kamado, who strives to become a Demon Slayer",
				"It follows teenage Tanjiro Kamado, who strives to become a Demon Slayer after his family was slaughtered and his younger sister, Nezuko, turned into a demon .",
				Year.of(2016), (byte) 12);
        tvshowList.add(tvShow1);
        tvshowList.add(tvShow2);
        tvshowList.add(tvShow3);



        when(tvShowRepository.getOne(1L)).thenReturn(tvShow1);


        TvShowRest tvShowRest = tvShowServiceImpl.getTvShowById(1L);
        assertNotNull(tvShowRest);
        assertEquals("Vikings", tvShowRest.getName());
        assertNotEquals("300", tvShowRest.getName());
        assertEquals("Vikings is a historical drama television series.", tvShowRest.getShortDescription());

    }


    @Test
    public void testInsertNewCategory() throws NetflixException {

    	TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series.",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);        Category ca = new Category(9L, "Premios Oscars");
        tvShow1.setCategories(new ArrayList<>());
        List<Category> listCaRest = new ArrayList<>();

        when(tvShowRepository.findById(tvShow1.getId())).thenReturn(Optional.of(tvShow1));
        when(categoryRepository.findById(ca.getId())).thenReturn(Optional.of(ca));
        when(tvShowRepository.save(tvShow1)).thenReturn(tvShow1);

        TvShowRest tvShow2 = tvShowServiceImpl.insertNewCategory(1L, ca.getId());
        listCaRest.add(ca);
        assertEquals(tvShow1.getCategories(), listCaRest);

    }

    @Test
    public void testUpdateName() throws NetflixException {
    	TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series.",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
        when(tvShowRepository.findById(tvShow1.getId())).thenReturn(Optional.of(tvShow1));
        when(tvShowRepository.save(tvShow1)).thenReturn(tvShow1);
        TvShowRest tvShow2 =tvShowServiceImpl.updateNameTvShow(1L, "LOS VIKINGOS");

        assertNotNull(tvShow2);
        assertEquals(modelMapper.map(tvShow1, TvShowRest.class), tvShow2);
        assertEquals(tvShow1.getName(), tvShow2.getName());

    }

    @Test
    public void testDeleteTvShow() {
    	TvShow tvShow1 = new TvShow(1L, "Vikings", "Vikings is a historical drama television series.",
				"The series is inspired by the tales of the Norsemen of early medieval Scandinavia. It broadly follows the exploits of the legendary Viking chieftain Ragnar Lothbrok and his crew, family and descendants",
				Year.of(2013), (byte) 14);
        when(tvShowRepository.getOne(1L)).thenReturn(null);
        tvShowRepository.deleteById(tvShow1.getId());

        TvShow tvShow2 = tvShowRepository.getOne(1L);

        assertNull(tvShow2);


    }




    }
