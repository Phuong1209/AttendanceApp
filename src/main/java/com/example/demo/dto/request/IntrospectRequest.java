package com.example.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectRequest {
    String token;

    @Getter
    @Setter

    public static class ProjectEditRequest {
        private String name;
        private String code;
        private Set<Long> taskIds;
    }
}
