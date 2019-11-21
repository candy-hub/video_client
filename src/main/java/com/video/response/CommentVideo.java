package com.video.response;

import com.video.domain.Barrage;
import com.video.domain.Comment;
import com.video.domain.Video;
import lombok.Data;

@Data
public class CommentVideo {

    private Comment comment;

    private Video video;

    private Barrage barrage;
}
