package com.yl.ping.pingserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/10 - 05 - 10 - 15:18
 */

@Getter
@Setter
@ApiModel
public class Ping {

    @NotNull
    @ApiModelProperty("host ip or domain")
    private String host;

    public Ping() {
        //do not delete
    }

    public Ping(String host) {
        this.host = host;
    }

}
