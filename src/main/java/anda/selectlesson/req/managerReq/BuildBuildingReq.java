package anda.selectlesson.req.managerReq;

import anda.selectlesson.req.BaseReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildBuildingReq extends BaseReq {
    private String building;
    private Integer floor;
    private Integer num;
}
