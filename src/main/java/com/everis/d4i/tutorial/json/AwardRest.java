package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;

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
public class AwardRest implements Serializable {

	private static final long serialVersionUID = -925399715611284812L;


	private Long id;
	private String name;
	private Year year;

}
