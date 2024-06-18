package com.gamification.gamificationbackend.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelFormulaRequestDto {

    private Integer levelFormulaConstant;

    private Integer levelFormulaPower;
} 