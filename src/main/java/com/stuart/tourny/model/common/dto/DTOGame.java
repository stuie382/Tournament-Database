package com.stuart.tourny.model.common.dto;

public class DTOGame {

    public DTOGame () {
        // Empty constructor
    }

    private long gameId;
    private String homePlayer;
    private String awayPlayer;
    private long homeGoals;
    private long awayGoals;
    private boolean extraTime;
    private long homePens;
    private long awayPens;
    private String winner;
    private long tournamentId;
    private boolean knockOut;
    private int hashCode;

    public long getGameId () {
        return gameId;
    }

    public void setGameId (long gameId) {
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

    public long getHomeGoals () {
        return homeGoals;
    }

    public void setHomeGoals (long homeGoals) {
        this.homeGoals = homeGoals;
    }

    public long getAwayGoals () {
        return awayGoals;
    }

    public void setAwayGoals (long awayGoals) {
        this.awayGoals = awayGoals;
    }

    public boolean isExtraTime () {
        return extraTime;
    }

    public void setExtraTime (boolean extraTime) {
        this.extraTime = extraTime;
    }

    public long getHomePens () {
        return homePens;
    }

    public void setHomePens (long homePens) {
        this.homePens = homePens;
    }

    public long getAwayPens () {
        return awayPens;
    }

    public void setAwayPens (long awayPens) {
        this.awayPens = awayPens;
    }

    public String getWinner () {
        return winner;
    }

    public void setWinner (String winner) {
        this.winner = winner;
    }

    public long getTournamentId () {
        return tournamentId;
    }

    public void setTournamentId (long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public boolean isKnockOut () {
        return knockOut;
    }

    public void setKnockOut (boolean knockOut) {
        this.knockOut = knockOut;
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
        if (hashCode != dtoGame.hashCode) {
            return false;
        }
        if (homeGoals != dtoGame.homeGoals) {
            return false;
        }
        if (homePens != dtoGame.homePens) {
            return false;
        }
        if (knockOut != dtoGame.knockOut) {
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
        int result = (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + homePlayer.hashCode ();
        result = 31 * result + awayPlayer.hashCode ();
        result = 31 * result + (int) (homeGoals ^ (homeGoals >>> 32));
        result = 31 * result + (int) (awayGoals ^ (awayGoals >>> 32));
        result = 31 * result + (extraTime ? 1 : 0);
        result = 31 * result + (int) (homePens ^ (homePens >>> 32));
        result = 31 * result + (int) (awayPens ^ (awayPens >>> 32));
        result = 31 * result + winner.hashCode ();
        result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
        result = 31 * result + (knockOut ? 1 : 0);
        result = 31 * result + hashCode;
        return result;
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
        sb.append (", knockOut=").append (knockOut);
        sb.append (", hashCode=").append (hashCode);
        sb.append ('}');
        return sb.toString ();
    }

    public int getHashCode () {
        return hashCode;
    }

    public void setHashCode (int hashCode) {
        this.hashCode = hashCode;
    }

}
