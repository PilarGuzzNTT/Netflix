package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TV_SHOWS")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TvShow implements Serializable {

	private static final long serialVersionUID = 4916713904971425156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SHORT_DESC", nullable = true)
	private String shortDescription;

	@Column(name = "LONG_DESC", nullable = true)
	private String longDescription;

	@Column(name = "YEAR")
	private Year year;

	@Column(name = "RECOMMENDED_AGE")
	private byte recommendedAge;

	@ManyToMany
	@JoinTable(name = "tvShow_categories", joinColumns = @JoinColumn(name = "idTvShow"), inverseJoinColumns = @JoinColumn(name = "idCategory"))
	private List<Category> categories;

	@Column(name = "ADVERTISING", nullable = true)
	private String advertising;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tvShow")
	private List<Season> seasons;

	@ManyToMany
	@JoinTable(name = "tvShow_awards", joinColumns = @JoinColumn(name = "idTvShow"), inverseJoinColumns = @JoinColumn(name = "idAwards"))
	private List<Award> awards;
	
	public TvShow(Long id, String name, String shortDescription, String longDescription, Year year, byte recommendedAge ) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.year = year;
        this.recommendedAge = recommendedAge;
    }

}
