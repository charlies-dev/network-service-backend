package com.infy.network.entity;



import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//create for composite key
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConnectionGroupMemberId implements Serializable {

    private Long groupId;
    private Long connectedUserId;
}
