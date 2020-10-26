package controllers;

import com.jfoenix.controls.JFXTextArea;
import data.Data;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import definitions.Twit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class TwitController extends ListCell<Twit> {
    @FXML private Label firstName, username, likeCount, commentCount, shareCount;
    @FXML private JFXTextArea text;
    @FXML private ImageView image;
    @FXML private AnchorPane aPane;
    @FXML private Pane pane;
    @FXML private FontAwesomeIconView like, share;
    @FXML private MaterialDesignIconView reply;
    @FXML private Rectangle rect;
    private boolean liked = false, shared = false;
    private Twit twit;

    @Override
    protected void updateItem(Twit twit, boolean empty) {
        super.updateItem(twit, empty);

        if(empty || twit == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader mLLoader = new FXMLLoader(getClass().getResource("/fxml/Twit.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setInfo(twit);
            checkHeight();
            setText(null);
            setGraphic(getPane());
        }
    }

    private void checkHeight() {
        int row = text.getText().length() / 80;
        if(row > 3) {
            double rowHeight = 73;
            text.setPrefHeight(row * rowHeight);
            double heightDiff = (row - 3) * rowHeight;
            aPane.setPrefHeight(aPane.getPrefHeight() + heightDiff);
            pane.setPrefHeight(pane.getPrefHeight() + heightDiff);
            rect.setHeight(rect.getHeight() + heightDiff);
            like.setLayoutY(like.getLayoutY() + heightDiff);
            reply.setLayoutY(reply.getLayoutY() + heightDiff);
            share.setLayoutY(share.getLayoutY() + heightDiff);
            likeCount.setLayoutY(likeCount.getLayoutY() + heightDiff);
            commentCount.setLayoutY(commentCount.getLayoutY() + heightDiff);
            shareCount.setLayoutY(shareCount.getLayoutY() + heightDiff);
        }
    }

    private AnchorPane getPane() {
        return aPane;
    }

    private void setInfo(Twit twit) {
        this.twit = twit;
        firstName.setText(twit.getUser().getFirstName());
        username.setText("@" + twit.getUser().getUsername());
        image.setImage(twit.getUser().getImage());
        PageController.clipImage(image);
        text.setText(twit.getText());
        likeCount.setText(twit.getLikeCount() + "");
        commentCount.setText(twit.getReplyCount() + "");
        shareCount.setText("0");
        if(twit.isDoILike()) {
            like.setIcon(FontAwesomeIcon.HEART);
            like.setFill(Color.web("#d63838"));
        } else {
            like.setIcon(FontAwesomeIcon.HEART_ALT);
            like.setFill(Color.web("#dbc5c5"));
        }
    }

    @FXML private void like() {
        if(twit.getUser().getId() == Data.user.getId())
            return;
        twit.setDoILike(!twit.isDoILike());
        if(twit.isDoILike()) {
            like.setIcon(FontAwesomeIcon.HEART);
            like.setFill(Color.web("#d63838"));
            twit.addLikeCount();
            DatabaseController.like(twit);
        } else {
            like.setIcon(FontAwesomeIcon.HEART_ALT);
            like.setFill(Color.web("#dbc5c5"));
            twit.minusLikeCount();
            DatabaseController.unlike(twit);
        }
        likeCount.setText(twit.getLikeCount() + "");
    }

    @FXML private void reply() {
        Data.depth ++;
        reply.setFill(Color.web("#4bc55b"));
        PageController.goToTwitPage(twit);
        commentCount.setText(twit.getReplies().size() + "");
    }

    @FXML private void share() {
        if(!shared) {
            share.setFill(Color.web("#694fff"));
        } else {
            share.setFill(Color.web("#dbc5c5"));
        }
        shared = !shared;
    }

    @FXML
    private void goToProfile() {
        PageController.goToProfile(twit.getUser());
    }

}
