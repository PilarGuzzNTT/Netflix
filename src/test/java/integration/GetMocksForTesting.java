package integration;

import java.util.ArrayList;
import java.util.List;

import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.json.TvShowRest;


public class GetMocksForTesting {
	
	public static List<CategoryRest> getMockListCategoryRest() {
		List<CategoryRest> categoryList = new ArrayList<>();
		categoryList.add(new CategoryRest(1L, "category 1"));
		categoryList.add(new CategoryRest(1L, "category 2"));
		categoryList.add(new CategoryRest(1L, "category 3"));
		categoryList.add(new CategoryRest(1L, "category 4"));
		return categoryList;
	}
	
	
	public static List<TvShowRest> getMockListTvShowRest() {
		List<TvShowRest> tvShowList = new ArrayList<>();
		tvShowList.add(new TvShowRest(1L, "tvshow 1"));
		tvShowList.add(new TvShowRest(1L, "tvshow 2"));
		tvShowList.add(new TvShowRest(1L, "tvshow 3"));
		tvShowList.add(new TvShowRest(1L, "tvshow 4"));
		return tvShowList;
	}

}
