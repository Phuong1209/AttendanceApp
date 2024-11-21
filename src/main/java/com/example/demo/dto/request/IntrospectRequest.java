package com.example.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class IntrospectRequest {
    String token;

    @Getter
    @Setter

    public static class DepartmentEditRequest {
        private String name;
        private Set<Long> jobTypeIds;
    }

    @Getter
    @Setter

    public static class ProjectEditRequest {
        private String name;
        private String code;
        private Set<Long> taskIds;
    }
}
