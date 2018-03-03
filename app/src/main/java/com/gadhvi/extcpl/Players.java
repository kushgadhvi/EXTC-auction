package com.gadhvi.extcpl;

/**
 * Created by gadhvi on 13/2/18.
 */

public class Players {
    public Players(String player, Long amount) {
        Player = player;
        Amount = amount;
    }
    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    private String Player;

    public Long getAmount() {
        return Amount;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    private Long Amount;

    public Players(){

    }





}
