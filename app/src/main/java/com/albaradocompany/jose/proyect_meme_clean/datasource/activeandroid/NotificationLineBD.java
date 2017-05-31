package com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jose on 31/05/2017.
 */
@Table(name = "Lines")
public class NotificationLineBD extends Model {
    @Column(name = "lineId")
    public String lineId;
    @Column(name = "userId")
    public String userId;
    @Column(name = "imagePath")
    public String imagePath;
    @Column(name = "message")
    public String message;
    @Column(name = "title")
    public String title;
    @Column(name = "time")
    public String time;
    @Column(name = "state")
    public String state;

    public NotificationLineBD() {
        super();
    }

    public NotificationLineBD(String lineId, String userId, String imagePath, String message, String title, String time, String state) {
        this.lineId = lineId;
        this.userId = userId;
        this.imagePath = imagePath;
        this.message = message;
        this.title = title;
        this.time = time;
        this.state = state;
    }
}
