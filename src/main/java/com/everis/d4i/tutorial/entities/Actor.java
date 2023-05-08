package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
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
@Table(name = "ACTOR")
@Data @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Actor implements Serializable {
	
	private static final long serialVersionUID = -1967725121596854001L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "AGE")
    private Integer age;
    
    @Column(name = "NATIONALITY")
    private String nationality;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<Chapter> chapters;

}
