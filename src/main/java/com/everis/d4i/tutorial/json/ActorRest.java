package com.everis.d4i.tutorial.json;

import java.io.Serializable;

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
public class ActorRest implements Serializable {

	private static final long serialVersionUID = 2562292635410148858L;
	
	

    private String name;
    private String surname;
    private Integer age;
    private String nationality;

	

}
