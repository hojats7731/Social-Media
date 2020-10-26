package definitions;

import java.util.ArrayList;
import java.util.Date;

public class Twit {
    private User user;
    private String text;
    private int likeCount = 0, replyCount = 0, id;
    private ArrayList<String> hashtags = new ArrayList<>();
    private ArrayList<Twit> replies = new ArrayList<>();
    private Date date;
    private boolean doILike = false;

    public Twit(User user, String text) {
        this.user = user;
        this.text = text;
        addHashtags(text);
    }

    private void addHashtags(String text) {
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '#') {
                int end = i + 1;
                while (end < text.length() && text.charAt(end) != ' ' && text.charAt(end) != '#')
                    end++;
                hashtags.add(text.substring(i, end));
                i = end;
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<Twit> getReplies() {
        return replies;
    }

    public void addReply(Twit twit) {
        replies.add(twit);
    }

    public void setReplies(ArrayList<Twit> replies) {
        this.replies = replies;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void minusLikeCount() {
        if(likeCount > 0)
            this.likeCount--;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDoILike() {
        return doILike;
    }

    public void setDoILike(boolean doILike) {
        this.doILike = doILike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
