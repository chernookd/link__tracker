package edu.java.domain.dto;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("MemberName")
public class Link {
    private long id;
    private URI url;
    private OffsetDateTime update_time;
}
