package com.retro.retroapp.repository;

import com.retro.retroapp.model.Comment;
import com.retro.retroapp.model.CommentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepositoryUnderTest;

    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Comment() {
        //given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        commentRepositoryUnderTest.save(comment);

        //when
        LocalDate now = LocalDate.now();
        List<Comment> comments =
                commentRepositoryUnderTest.findByCreatedYearAndMonthAndDay(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth());

        //then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue("comment", "Test");
    }

    @Test
    public void save_HappyPath_ShouldSave1Comment() {
        //given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        //when
        Comment save = commentRepositoryUnderTest.save(comment);

        //then
        Optional<Comment> optionalComment = commentRepositoryUnderTest.findById(comment.getId());
        assertThat(optionalComment)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isSameAs(comment);
                });

    }
}
