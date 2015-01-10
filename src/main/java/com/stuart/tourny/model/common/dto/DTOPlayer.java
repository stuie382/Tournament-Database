package com.stuart.tourny.model.common.dto;

public class DTOPlayer {

    private final String name;

    public DTOPlayer (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder ("DTOPlayer{");
        sb.append ("name='").append (name).append ('\'');
        sb.append ('}');
        return sb.toString ();
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DTOPlayer)) {
            return false;
        }

        DTOPlayer dtoPlayer = (DTOPlayer) o;

        if (!name.equals (dtoPlayer.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode () {
        return name.hashCode ();
    }
}
