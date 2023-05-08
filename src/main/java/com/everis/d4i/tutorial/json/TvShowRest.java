package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TvShowRest implements Serializable {

	private static final long serialVersionUID = 4916713904971425156L;

	private Long id;
	private String name;
	private String shortDescription;
	private String longDescription;
	private Year year;
	private byte recommendedAge;
	private List<CategoryRest> categories;
	private String advertising;

	
	public TvShowRest(Long id, String name) {
		this.id = id;
		this.name = name;

	}

//	@Override
//	public String toString() {
//		return "TvShowRest [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription
//				+ ", longDescription=" + longDescription + ", year=" + year + ", recommendedAge=" + recommendedAge
//				+ ", advertising=" + advertising + "]";
//	}


}
