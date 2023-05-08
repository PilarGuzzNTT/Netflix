package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AWARD")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Award implements Serializable {
	
	private static final long serialVersionUID = 8790223247477827142L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "YEAR")
	private Year year;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "awards")
    private List<TvShow> tvShows;
    
   

}
