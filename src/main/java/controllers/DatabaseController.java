package controllers;

import data.Data;
import definitions.Role;
import definitions.Twit;
import definitions.User;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("Duplicates")
class DatabaseController {
    private static HashMap<String, String> query = new HashMap<>();
    private static ArrayList<JSONObject> result;

    static boolean isUserCorrect(String username, String password) {
        query = new HashMap<>();
        query.put("UserName", username);
        query.put("Password", password);
        result = sendData("Login");
        JSONObject user = result.get(0);
        if((Boolean) user.get("State")) {
            Data.user = new User(user.getString("UserName"), user.getString("Name"), user.getString("LastName"), user.getString("Email"), user.getString("Phone"), user.getString("Bio"));
            Data.user.setRole(Role.values()[Integer.valueOf(user.getString("Role"))]);
            Data.user.setId(Integer.valueOf(user.getString("ID")));
            System.out.println(Data.user.toString());
            return true;
        }
        else {
            System.out.println("Error");
            return false;
        }
    }

//    static ArrayList<User> getUserPostEveryDay() {
//        SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
//        result = sendData("q11-1");
//        ArrayList<User> users = new ArrayList<>();
//        for (JSONObject object : result) {
//            User user = getUserBasicData(Integer.parseInt(object.getString("UserID")));
//            try {
//                user.setDate(sqlDate.parse(object.getString("signUpDate")));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date now = new Date();
//
//        }
//    }

    static ArrayList<User> getFollowers(User user) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(user.getId()));
        ArrayList<JSONObject> data = sendData("GetFollowers");
        ArrayList<User> users = new ArrayList<>();
        for (JSONObject object : data)
            users.add(getUserBasicData(Integer.parseInt(object.getString("UserID"))));
        return users;
    }

    static ArrayList<User> getFollowing(User user) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(user.getId()));
        ArrayList<JSONObject> data = sendData("GetFollowing");
        ArrayList<User> users = new ArrayList<>();
        for (JSONObject object : data)
            users.add(getUserBasicData(Integer.parseInt(object.getString("UserID"))));
        return users;
    }

    static ArrayList<User> getFollowingFollowBack() {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<JSONObject> data = sendData("SearchUsersFollowingFollowbacked");
        System.out.println(data.size());
        for (JSONObject object : data)
            users.add(getUserBasicData(Integer.parseInt(object.getString("following"))));
        return users;
    }

    static void like(Twit twit) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(Data.user.getId()));
        query.put("post_id", String.valueOf(twit.getId()));
        if(Data.depth == 0)
            sendData("Like");
        else if(Data.depth == 1)
            sendData("CommentLike");
        else
            sendData("ReplyLike");
    }

    static void unlike(Twit twit) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(Data.user.getId()));
        query.put("post_id", String.valueOf(twit.getId()));
        sendData("UnLike");
    }

    static void getComments(Twit twit) {
        query = new HashMap<>();
        query.put("postID", String.valueOf(twit.getId()));
        ArrayList<JSONObject> comments = sendData("GetComments");
        for (JSONObject object : comments) {
            User user = getUserBasicData(Integer.parseInt(object.getString("UserID")));
            Twit comment = new Twit(user, object.getString("Text"));
            comment.setId(Integer.parseInt(object.getString("ID")));
            comment.setReplyCount(Integer.parseInt(object.getString("ReplyCount")));
            twit.getReplies().add(comment);
        }
    }

    static void getReplies(Twit twit) {
        query = new HashMap<>();
        if (Data.depth == 2) {
            query.put("CommentID", String.valueOf(twit.getId()));
            ArrayList<JSONObject> comments = sendData("GetReplies");
            for (JSONObject object : comments) {
                User user = getUserBasicData(Integer.parseInt(object.getString("UserID")));
                Twit comment = new Twit(user, object.getString("Text"));
                comment.setId(Integer.parseInt(object.getString("ID")));
                comment.setReplyCount(Integer.parseInt(object.getString("ReplyCount")));
                twit.getReplies().add(comment);
            }
        }
        else if(Data.depth == 3) {
            query.put("ReplyID", String.valueOf(twit.getId()));
            ArrayList<JSONObject> comments = sendData("GetReplyReplies");
            for (JSONObject object : comments) {
                User user = getUserBasicData(Integer.parseInt(object.getString("UserID")));
                Twit comment = new Twit(user, object.getString("Text"));
                comment.setId(Integer.parseInt(object.getString("ID")));
                twit.getReplies().add(comment);
            }
        }
    }

    static boolean checkQuestion(String uname, int pqNum, String ans) {
        query = new HashMap<>();
        query.put("UserName", uname);
        query.put("Question", String.valueOf(pqNum));
        query.put("Answer", ans);
        result = sendData("checkQuestion");
        JSONObject userInfo = result.get(0);
        boolean res = Boolean.parseBoolean(userInfo.getString("state"));
        System.out.println(res);
        if (res) {
            Data.user = getUserBasicData(Integer.parseInt(userInfo.getString("ID")));
        }
        return res;
    }

    static void addComment(Twit twit, Twit comment) {
        query = new HashMap<>();
        query.put("UserID", String.valueOf(Data.user.getId()));
        query.put("FreeText", String.valueOf(comment.getText()));
        Date now = new Date();
        query.put("Date", new java.sql.Date(now.getTime()).toString());
        query.put("Time", new java.sql.Time(now.getTime()).toString());
        if (Data.depth == 1) {
            query.put("ExpressID", String.valueOf(twit.getId()));
            sendData("AddComment");
        } else if (Data.depth == 2) {
            query.put("CommentID", String.valueOf(twit.getId()));
            query.put("ReplyID", "null");
            sendData("AddReply");
        } else {
            query.put("CommentID", "null");
            query.put("ReplyID", String.valueOf(twit.getId()));
            sendData("AddReply");
        }
    }

    static void getUserData(User user) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(user.getId()));
        result = sendData("UserInfo");
        JSONObject userInfo = result.get(0);
        user.setEmail(userInfo.getString("Email"));
        user.setPhoneNumber(userInfo.getString("Phone"));
        user.setBiography(userInfo.getString("Bio"));
        user.setFollowersNum(Integer.parseInt(userInfo.getString("FollowersNum")));
        user.setFollowingNum(Integer.parseInt(userInfo.getString("FollowingNum")));
        user.setTwitsNum(Integer.parseInt(userInfo.getString("TwitsNum")));
    }

    private static User getUserBasicData(int userID) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(userID));
        if(Data.user != null)
            query.put("myID", String.valueOf(Data.user.getId()));
        result = sendData("UserBasicInfo");
        JSONObject userInfo = result.get(0);
        User user = new User(userInfo.getString("UserName"), userInfo.getString("Name"), userInfo.getString("LastName"));
        user.setFollowStatus(userInfo.getString("status"));
        user.setId(userID);
        //TODO: add picture
        return user;
    }

    static void getUserTwits(User user) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(user.getId()));
        result = sendData("100LastUserExpress");
        ArrayList<Twit> twits = new ArrayList<>();
        for (JSONObject object : result) {
            Twit twit = new Twit(user, object.getString("text"));
            twit.setId(Integer.parseInt(object.getString("postID")));
            twit.setLikeCount(Integer.parseInt(object.getString("likeNum")));
            twit.setReplyCount((Integer.parseInt(object.getString("commentNum"))));
            twit.setDoILike(Boolean.parseBoolean(object.getString("doILike")));
            twits.add(twit);
        }
        user.setTwits(twits);
    }

    static ArrayList<User> getUserCheating() {
        SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
        result = sendData("q11-1");
        ArrayList<User> lessPost = new ArrayList<>();
        for (JSONObject object : result) {
            User user = getUserBasicData(Integer.parseInt(object.getString("UserID")));
            try {
                user.setDate(sqlDate.parse(object.getString("signUpDate")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            user.setTwitsNum(Integer.parseInt(object.getString("count")));
            Date now = new Date();
            long diff = now.getTime() - user.getDate().getTime();
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            System.out.println(new java.sql.Date(now.getTime()).toString());
            System.out.println(new java.sql.Date(user.getDate().getTime()).toString());
            if (user.getTwitsNum() / (double) diffDays < 0.1)
                lessPost.add(user);
        }
        //TODO: get half liked!
        return lessPost;
    }

    static void getUserShowTwits(User user) {
        query = new HashMap<>();
        query.put("ID", String.valueOf(user.getId()));
        ArrayList<JSONObject> firstResult = sendData("100LastExpress");
        ArrayList<Twit> twits = new ArrayList<>();
        for (JSONObject object : firstResult) {
            int userID = Integer.parseInt(object.getString("userID"));
            User anotherUser = getUserBasicData(userID);
            Twit twit = new Twit(anotherUser, object.getString("text"));
            twit.setId(Integer.parseInt(object.getString("postID")));
            twit.setLikeCount(Integer.parseInt(object.getString("likeNum")));
            twit.setReplyCount((Integer.parseInt(object.getString("commentNum"))));
            //FIXME: doILike Query Failed!
            twit.setDoILike(Boolean.parseBoolean(object.getString("doILike")));
            twits.add(twit);
        }
        user.setShowTwits(twits);
    }

    static void addUser(User user) {
        query = new HashMap<>();
        query.put("UserName", user.getUsername());
        query.put("Password", user.getPassword());
        query.put("Email", user.getEmail());
        query.put("Phone", user.getPhoneNumber());
        query.put("Name", user.getFirstName());
        query.put("LastName", user.getLastName());
        query.put("Bio", user.getBiography());
        query.put("Role", String.valueOf(user.getRoleIndex()));
        query.put("Question", String.valueOf(user.getPersonalQuestionNumber()));
        query.put("Answer", user.getPersonalQuestionAnswer());
        query.put("Pic", Data.user.getUsername() + "image");
        query.put("SignUpDate", new java.sql.Date(Data.user.getDate().getTime()).toString());
        sendData("SignUp");
    }

    static void addTwit(Twit twit) {
        query = new HashMap<>();
        int userID = Data.user.getId();
        String text = twit.getText();
        Date now = new Date();
        query.put("UserID", String.valueOf(userID));
        query.put("FreeText", text);
        query.put("Date", new java.sql.Date(now.getTime()).toString());
        query.put("Time", new java.sql.Time(now.getTime()).toString());
        query.put("hash1", twit.getHashtags().get(0));
        query.put("hash2", twit.getHashtags().get(1));
        System.out.println("Time " + new java.sql.Time(now.getTime()) + " | Date " + new java.sql.Date(now.getTime()));
        sendData("postExpress");
    }

    static ArrayList<Twit> searchHashtag(String search) {
        query = new HashMap<>();
        query.put("search", "#" + search);
        query.put("ID", String.valueOf(Data.user.getId()));
        result = sendData("HashtagSimpleSearch");
        ArrayList<Twit> twits = new ArrayList<>();
        for (JSONObject object : result) {
            User user = getUserBasicData(Integer.parseInt(object.getString("userID")));
            Twit twit = new Twit(user, object.getString("text"));
            twit.setId(Integer.parseInt(object.getString("expressID")));
            twits.add(twit);
        }

        return twits;
    }

    static ArrayList<User> searchUser(String search) {
        query = new HashMap<>();
        query.put("UserName", search);
        query.put("ID", String.valueOf(Data.user.getId()));
        result = sendData("SearchUserName");
        ArrayList<User> users = new ArrayList<>();
        for (JSONObject object : result) {
            User user = new User(object.getString("UserName"), object.getString("Name"), object.getString("LastName"));
            user.setId(Integer.valueOf(object.getString("ID")));
            user.setFollowStatus(object.getString("status"));
            //TODO: add user image
            users.add(user);
        }
        return users;
    }

    static ArrayList<Twit> getTrends() {
        query = new HashMap<>();
        ArrayList<Twit> result = new ArrayList<>();
        //TODO: get trends twit from DB
        return result;
    }

    static void addFollowing(User user) {
        query = new HashMap<>();
        query.put("Follower", String.valueOf(Data.user.getId()));
        query.put("Following", String.valueOf(user.getId()));
        result = sendData("Follow");
    }

    static void removeFollowing(User user) {
        query = new HashMap<>();
        query = new HashMap<>();
        query.put("Follower", String.valueOf(Data.user.getId()));
        query.put("Following", String.valueOf(user.getId()));
        result = sendData("UnFollow");
    }

    private static ArrayList<JSONObject> sendData(String pageAddress) {
        ArrayList<JSONObject> objects = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8080/twitter/" + pageAddress + ".php");
            URLConnection connection = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) connection;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            PrintStream ps = new PrintStream(connection.getOutputStream());

            for(Map.Entry<String, String> e : query.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                ps.print("&" + key + "=" + value);
            }

            ps.close();


            //------------ get Data

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.charAt(0) == '{') {
                    JSONObject object = new JSONObject(line);
                    objects.add(object);
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }


}
