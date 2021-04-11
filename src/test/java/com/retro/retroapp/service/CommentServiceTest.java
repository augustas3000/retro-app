package com.retro.retroapp.service;

import com.retro.retroapp.model.Comment;
import com.retro.retroapp.model.CommentType;
import com.retro.retroapp.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepositoryMock;

    private CommentService commentServiceUnderTest;

    @Before
    public void setUp() {
        commentServiceUnderTest = new CommentService(commentRepositoryMock);
    }

    @Test
    public void saveAll_HappyPath_ShouldSave2Comments() {
        //given
        Comment comment1 = new Comment();
        comment1.setComment("I am a comment");
        comment1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        comment1.setCreatedBy("Augustas");
        comment1.setType(CommentType.PLUS);

        Comment comment2 = new Comment();
        comment2.setComment("I am a comment too");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        comment2.setCreatedBy("Jon");
        comment2.setType(CommentType.DELTA);

        //and
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepositoryMock.saveAll(comments)).thenReturn(comments);

        //when
        List<Comment> commentsReturned = commentRepositoryMock.saveAll(comments);

        //then
        assertThat(commentsReturned).isEqualTo(comments);
        verify(commentRepositoryMock, times(1)).saveAll(comments);
    }

    @Test
    public void
    getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        //given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new
                Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment);
        LocalDate now = LocalDate.now();

        //and
        when(commentRepositoryMock.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(),
                now.getDayOfMonth())).thenReturn(comments);

        //when
        List<Comment> actualComments =
                commentServiceUnderTest.getAllCommentsForToday();

        //then
        verify(commentRepositoryMock,
                times(1)).findByCreatedYearAndMonthAndDay(now.getYear(),
                now.getMonth().getValue(), now.getDayOfMonth());
        assertThat(comments).isEqualTo(actualComments);
    }


}
