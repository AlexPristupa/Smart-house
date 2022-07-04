package ru.prooftechit.smh.domain.search;

import java.util.Set;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.File_;
import ru.prooftechit.smh.domain.model.User;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Спецификация для получения файла с учетом прав пользователя на него.
 *
 * @author Andrey Kovalenko
 */
@Builder
public class FileSpecification implements Specification<File> {

    private static final long serialVersionUID = -6110984182176772012L;

    private User user;
    private UUID contentId;
    private Set<UUID> contentIds;
    private Long fileId;
    private boolean isPreview;
    private boolean isDeleteMode;

    @Override
    public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //Условия в основном запросе
        List<Predicate> predicates = new ArrayList<>();

        //Выборка по contentId (или соответствующего ему превью)
        //В случае превью обработка заканчивается, удаление по fileId для него не предусмотрено
        boolean hasContentId = contentId != null;
        boolean hasContentIds = contentIds != null && !contentIds.isEmpty();
        if (hasContentId || hasContentIds) {
            if (isPreview) {
                Subquery<File> subquery = query.subquery(File.class);
                Root<File> rootPreview = subquery.from(File.class);
                subquery.select(rootPreview.get(File_.PREVIEW))
                    .where(hasContentId
                           ? builder.equal(rootPreview.get(File_.CONTENT_ID), contentId)
                           : rootPreview.get(File_.CONTENT_ID).in(contentIds));
                return builder.in(root.get(File_.ID)).value(subquery.getSelection());
            } else {
                predicates.add(hasContentId
                               ? builder.equal(root.get(File_.CONTENT_ID), contentId)
                               : root.get(File_.CONTENT_ID).in(contentIds));
            }
        }

        //Выборка по fileId
        if (fileId != null) {
            predicates.add(builder.equal(root.get(File_.ID), fileId));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
