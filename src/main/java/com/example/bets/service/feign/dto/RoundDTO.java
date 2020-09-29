package com.example.bets.service.feign.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoundDTO {
    /**
     * The unique ID of the round of this tournament
     */
    private Long roundId;

    /**
     * The TournamentID of the tournament
     */
    private Long tournamentId;

    /**
     * The round number of this round
     */
    private Integer number;

    /**
     * The date that this round is played
     */
    private String day;
}
