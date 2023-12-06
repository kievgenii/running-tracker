package com.running.tracker.service;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@UtilityClass
public class SpecificationBuilder {

    public static Specification<Run> getRunSpecification(RunFilter filter) {
        Specification<Run> specification = Specification.where(null);

        Long userId = filter.getUserId();
        if (userId != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId));
        }

        LocalDateTime fromDateTime = filter.getFromDateTime();
        if (fromDateTime != null) {
            specification = specification.and(Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDateTime"), fromDateTime)));
        }

        LocalDateTime toDateTime = filter.getToDateTime();
        if (toDateTime != null) {
            specification = specification.and(Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), toDateTime)));
        }
        return specification;
    }
}
