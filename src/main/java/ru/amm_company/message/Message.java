/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.amm_company.message;

import java.sql.Time;
import java.util.*;
import java.io.*;

/**
 *
 * @author Андрей
 */
public class Message implements Serializable {

    private String nickname;
    private String textmsg;
    private Date time;
    
// Constructor for client
    public Message(String nickname, String textmsg){
        this.nickname = nickname;
        this.textmsg = textmsg;
        this.time = java.util.Calendar.getInstance().getTime();
    }

// Set new message    
    public void setTextmsg(String textmsg) {
        this.textmsg = textmsg;
    }

    public String getNickname() {
        return this.nickname + ":";
    }

    public String getTextmsg() {
        return this.textmsg;
    }

    public String getTime(){
        Time tm = new Time(this.time.getTime());
        return "[" + tm.toString() + "]";
    }
}
