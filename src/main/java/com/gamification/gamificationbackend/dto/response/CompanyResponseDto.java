package com.gamification.gamificationbackend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String name;

    private Integer levelFormulaConstant;

    private Integer levelFormulaPower;
} 