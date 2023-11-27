/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class Message {
    private Integer id; 
    private String content;
    private int sendedHour;
    private int sendedMinute; 
    private boolean mine;

    public Message(Integer id, String content, int sendedHour, int sendedMinute, boolean mine) {
        this.id = id;
        this.content = content;
        this.sendedHour = sendedHour;
        this.sendedMinute = sendedMinute;
        this.mine = mine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSendedHour() {
        return sendedHour;
    }

    public void setSendedHour(int sendedHour) {
        this.sendedHour = sendedHour;
    }

    public int getSendedMinute() {
        return sendedMinute;
    }

    public void setSendedMinute(int sendedMinute) {
        this.sendedMinute = sendedMinute;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
     
}
