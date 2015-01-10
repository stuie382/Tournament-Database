package com.stuart.tourny.model.dto;

public class DTOGame {

    public DTOGame() {
        // Empty constructor
    }
    private int gameId;
    private String homePlayer;
    private String awayPlayer;
    private int homeGoals;
    private int awayGoals;
    private boolean extraTime;
    private int homePens;
    private int awayPens;
    private String winner;
    private int tournamentId;

    public int getGameId () {
        return gameId;
    }

    public void setGameId (int gameId) {
        this.gameId = gameId;
    }

    public String getHomePlayer () {
        return homePlayer;
    }

    public void setHomePlayer (String homePlayer) {
        this.homePlayer = homePlayer;
    }

    public String getAwayPlayer () {
        return awayPlayer;
    }

    public void setAwayPlayer (String awayPlayer) {
        this.awayPlayer = awayPlayer;
    }

    public int getHomeGoals () {
        return homeGoals;
    }

    public void setHomeGoals (int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals () {
        return awayGoals;
    }

    public void setAwayGoals (int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public boolean isExtraTime () {
        return extraTime;
    }

    public void setExtraTime (boolean extraTime) {
        this.extraTime = extraTime;
    }

    public int getHomePens () {
        return homePens;
    }

    public void setHomePens (int homePens) {
        this.homePens = homePens;
    }

    public int getAwayPens () {
        return awayPens;
    }

    public void setAwayPens (int awayPens) {
        this.awayPens = awayPens;
    }

    public String getWinner () {
        return winner;
    }

    public void setWinner (String winner) {
        this.winner = winner;
    }

    public int getTournamentId () {
        return tournamentId;
    }

    public void setTournamentId (int tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder ("DTOGame{");
        sb.append ("gameId=").append (gameId);
        sb.append (", homePlayer='").append (homePlayer).append ('\'');
        sb.append (", awayPlayer='").append (awayPlayer).append ('\'');
        sb.append (", homeGoals=").append (homeGoals);
        sb.append (", awayGoals=").append (awayGoals);
        sb.append (", extraTime=").append (extraTime);
        sb.append (", homePens=").append (homePens);
        sb.append (", awayPens=").append (awayPens);
        sb.append (", winner='").append (winner).append ('\'');
        sb.append (", tournamentId=").append (tournamentId);
        sb.append ('}');
        return sb.toString ();
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DTOGame)) {
            return false;
        }

        DTOGame dtoGame = (DTOGame) o;

        if (awayGoals != dtoGame.awayGoals) {
            return false;
        }
        if (awayPens != dtoGame.awayPens) {
            return false;
        }
        if (extraTime != dtoGame.extraTime) {
            return false;
        }
        if (gameId != dtoGame.gameId) {
            return false;
        }
        if (homeGoals != dtoGame.homeGoals) {
            return false;
        }
        if (homePens != dtoGame.homePens) {
            return false;
        }
        if (tournamentId != dtoGame.tournamentId) {
            return false;
        }
        if (!awayPlayer.equals (dtoGame.awayPlayer)) {
            return false;
        }
        if (!homePlayer.equals (dtoGame.homePlayer)) {
            return false;
        }
        if (!winner.equals (dtoGame.winner)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode () {
        int result = gameId;
        result = 31 * result + homePlayer.hashCode ();
        result = 31 * result + awayPlayer.hashCode ();
        result = 31 * result + homeGoals;
        result = 31 * result + awayGoals;
        result = 31 * result + (extraTime ? 1 : 0);
        result = 31 * result + homePens;
        result = 31 * result + awayPens;
        result = 31 * result + winner.hashCode ();
        result = 31 * result + tournamentId;
        return result;
    }
}
