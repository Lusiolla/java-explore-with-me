package ru.practicum.explorewm.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewm.comment.model.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        Collection<Comment> findByAuthorId(long userId);

        void deleteByAuthorId(long userId);


}
