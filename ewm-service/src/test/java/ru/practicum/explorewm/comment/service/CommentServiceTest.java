package ru.practicum.explorewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.comment.dto.CommentDto;
import ru.practicum.explorewm.comment.dto.CommentFull;
import ru.practicum.explorewm.comment.dto.CommentUpdate;
import ru.practicum.explorewm.comment.model.Comment;
import ru.practicum.explorewm.event.location.model.Location;
import ru.practicum.explorewm.event.model.Event;
import ru.practicum.explorewm.event.state.State;
import ru.practicum.explorewm.requests.model.ParticipationRequest;
import ru.practicum.explorewm.requests.status.Status;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentServiceTest {

    private final CommentService service;

    private final EntityManager em;


    @Test
    public void shouldSaveNewComment() {
        User user1 = new User(
                null,
                "name1@ya.ru",
                "name1"
        );

        User user2 = new User(
                null,
                "name2@ya.ru",
                "name2"
        );
        em.persist(user1);
        em.persist(user2);
        em.flush();

        Category category1 = new Category(null, "category1");

        em.persist(category1);
        em.flush();

        Location location1 = new Location(null, 10.0f, 50.0f);

        em.persist(location1);
        em.flush();

        Event event1 = new Event(
                null,
                "annotation1",
                category1,
                0,
                LocalDateTime.now(),
                "description1",
                LocalDateTime.now().plusDays(3),
                user1,
                location1,
                false,
                0,
                null,
                true,
                State.PUBLISHED,
                "title1",
                0,
                null,
                null
        );

        em.persist(event1);
        em.flush();

        ParticipationRequest request1 = new ParticipationRequest(
                null,
                LocalDateTime.now(),
                event1,
                user2,
                Status.CONFIRMED
        );

        em.persist(request1);
        em.flush();

        Comment comment1 = new Comment(
                null,
                user2,
                event1.getId(),
                "text1",
                LocalDateTime.of(2022, 10, 19, 17, 0, 0),
                State.PUBLISHED,
                null
        );

        em.persist(comment1);
        em.flush();

        CommentDto commentDto = new CommentDto(
                comment1.getId(),
                comment1.getAuthor().getName(),
                comment1.getEventId(),
                comment1.getText(),
                comment1.getCreatedOn()
        );

        CommentDto comment = service.add(comment1, user2.getId(), event1.getId());

        assertEquals(comment, commentDto);
    }

    @Test
    public void shouldUpdateComment() {
        User user3 = new User(
                null,
                "name3@ya.ru",
                "name3"
        );

        User user4 = new User(
                null,
                "name4@ya.ru",
                "name4"
        );
        em.persist(user3);
        em.persist(user4);
        em.flush();

        Category category2 = new Category(null, "category2");

        em.persist(category2);
        em.flush();

        Location location2 = new Location(null, 11.0f, 51.0f);

        em.persist(location2);
        em.flush();

        Event event2 = new Event(
                null,
                "annotation1",
                category2,
                0,
                LocalDateTime.now(),
                "description1",
                LocalDateTime.now().plusDays(3),
                user3,
                location2,
                false,
                0,
                null,
                true,
                State.PUBLISHED,
                "title1",
                0,
                null,
                null
        );

        em.persist(event2);
        em.flush();

        ParticipationRequest request1 = new ParticipationRequest(
                null,
                LocalDateTime.now(),
                event2,
                user4,
                Status.CONFIRMED
        );

        em.persist(request1);
        em.flush();

        Comment comment2 = new Comment(
                null,
                user4,
                event2.getId(),
                "text2",
                LocalDateTime.of(2022, 10, 19, 17, 0, 0),
                State.PENDING,
                null
        );

        em.persist(comment2);
        em.flush();

        CommentUpdate updateComment = new CommentUpdate(
                comment2.getId(),
                "textupd"
        );

        CommentDto commentDto = new CommentDto(
                comment2.getId(),
                comment2.getAuthor().getName(),
                comment2.getEventId(),
                updateComment.getText(),
                comment2.getCreatedOn()
        );

        CommentDto comment = service.update(updateComment, user4.getId());

        assertEquals(comment, commentDto);
    }

    @Test
    public void shouldGetByUserAndDeleteComment() {
        User user5 = new User(
                null,
                "name5@ya.ru",
                "name5"
        );

        User user6 = new User(
                null,
                "name6@ya.ru",
                "name6"
        );
        em.persist(user5);
        em.persist(user6);
        em.flush();

        Category category3 = new Category(null, "category3");

        em.persist(category3);
        em.flush();

        Location location2 = new Location(null, 12.0f, 52.0f);

        em.persist(location2);
        em.flush();

        Event event3 = new Event(
                null,
                "annotation1",
                category3,
                0,
                LocalDateTime.now(),
                "description1",
                LocalDateTime.now().plusDays(3),
                user5,
                location2,
                false,
                0,
                null,
                true,
                State.PUBLISHED,
                "title1",
                0,
                null,
                null
        );

        em.persist(event3);
        em.flush();

        ParticipationRequest request1 = new ParticipationRequest(
                null,
                LocalDateTime.now(),
                event3,
                user6,
                Status.CONFIRMED
        );

        em.persist(request1);
        em.flush();

        Comment comment3 = new Comment(
                null,
                user6,
                event3.getId(),
                "text3",
                LocalDateTime.of(2022, 10, 19, 17, 0, 0),
                State.PENDING,
                null
        );

        em.persist(comment3);
        em.flush();

        Collection<CommentFull> commentsBeforeDelete = service.getUserComments(user6.getId());
        service.delete(user6.getId(), comment3.getId());
        Collection<CommentFull> commentsAfterDelete = service.getUserComments(user6.getId());

        assertEquals(commentsBeforeDelete.size(), 1);
        assertEquals(commentsAfterDelete.size(), 0);
    }

    @Test
    public void shouldPublishComment() {
        User user7 = new User(
                null,
                "name7@ya.ru",
                "name7"
        );

        User user8 = new User(
                null,
                "name8@ya.ru",
                "name8"
        );
        em.persist(user7);
        em.persist(user8);
        em.flush();

        Category category4 = new Category(null, "category4");

        em.persist(category4);
        em.flush();

        Location location4 = new Location(null, 13.0f, 53.0f);

        em.persist(location4);
        em.flush();

        Event event4 = new Event(
                null,
                "annotation4",
                category4,
                0,
                LocalDateTime.now(),
                "description4",
                LocalDateTime.now().plusDays(3),
                user7,
                location4,
                false,
                0,
                null,
                true,
                State.PUBLISHED,
                "title4",
                0,
                null,
                null
        );

        em.persist(event4);
        em.flush();

        ParticipationRequest request1 = new ParticipationRequest(
                null,
                LocalDateTime.now(),
                event4,
                user8,
                Status.CONFIRMED
        );

        em.persist(request1);
        em.flush();

        Comment comment4 = new Comment(
                null,
                user8,
                event4.getId(),
                "text4",
                LocalDateTime.of(2022, 10, 19, 17, 0, 0),
                State.PENDING,
                null
        );

        em.persist(comment4);
        em.flush();

        CommentFull commentDto = new CommentFull(
                comment4.getId(),
                comment4.getAuthor().getId(),
                comment4.getEventId(),
                comment4.getText(),
                comment4.getCreatedOn(),
                comment4.getState(),
                comment4.getPublishedOn()
        );

        CommentFull comment = service.publish(event4.getId(), comment4.getId());

        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getState(), State.PUBLISHED);
        assertNotNull(comment.getPublishedOn());
    }

    @Test
    public void shouldRejectComment() {
        User user9 = new User(
                null,
                "name9@ya.ru",
                "name9"
        );

        User user10 = new User(
                null,
                "name10@ya.ru",
                "name10"
        );
        em.persist(user9);
        em.persist(user10);
        em.flush();

        Category category5 = new Category(null, "category5");

        em.persist(category5);
        em.flush();

        Location location5 = new Location(null, 14.0f, 54.0f);

        em.persist(location5);
        em.flush();

        Event event5 = new Event(
                null,
                "annotation5",
                category5,
                0,
                LocalDateTime.now(),
                "description5",
                LocalDateTime.now().plusDays(3),
                user9,
                location5,
                false,
                0,
                null,
                true,
                State.PUBLISHED,
                "title5",
                0,
                null,
                null
        );

        em.persist(event5);
        em.flush();

        ParticipationRequest request1 = new ParticipationRequest(
                null,
                LocalDateTime.now(),
                event5,
                user10,
                Status.CONFIRMED
        );

        em.persist(request1);
        em.flush();

        Comment comment5 = new Comment(
                null,
                user10,
                event5.getId(),
                "text5",
                LocalDateTime.of(2022, 10, 19, 17, 0, 0),
                State.PENDING,
                null
        );

        em.persist(comment5);
        em.flush();

        CommentFull commentDto = new CommentFull(
                comment5.getId(),
                comment5.getAuthor().getId(),
                comment5.getEventId(),
                comment5.getText(),
                comment5.getCreatedOn(),
                comment5.getState(),
                comment5.getPublishedOn()
        );

        CommentFull comment = service.reject(comment5.getId());

        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getState(), State.CANCELED);
        assertNull(comment.getPublishedOn());
    }
}
