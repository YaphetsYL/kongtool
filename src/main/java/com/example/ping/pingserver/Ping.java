package com.example.ping.pingserver;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/10 - 05 - 10 - 15:18
 */

@Getter
@Setter
public class Ping {

    @NonNull
    private String host;

    public Ping() {
    }

    public Ping(String host) {
        this.host = host;
    }

}
