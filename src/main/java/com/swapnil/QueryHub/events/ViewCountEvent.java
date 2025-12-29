package com.swapnil.QueryHub.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountEvent {

    private String targetId;
    private String targetType;
    private Instant timestamp;
}
