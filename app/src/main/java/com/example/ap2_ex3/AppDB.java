package com.example.ap2_ex3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteCompat;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ap2_ex3.daos.ChatDao;
import com.example.ap2_ex3.daos.MessageDao;
import com.example.ap2_ex3.daos.UserDao;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.entities.UserName;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Chat.class, User.class, Message.class}, version = 13)
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();

    private static AppDB instance;

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "AppDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChatDao chatDao;

        private PopulateDbAsyncTask(AppDB db) {
            chatDao = db.chatDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            List<Message> messageList = new ArrayList<>();
            messageList.add(new Message(1, "hello my friend", "16:33", new UserName("nadav")));
            messageList.add(new Message(2, "hello to you too", "16:34", new UserName("ori")));
            messageList.add(new Message(3, "fuck Hemi", "16:33", new UserName("nadav")));
            messageList.add(new Message(4, "I agree", "16:33", new UserName("ori")));
            messageList.add(new Message(5, "Mother RUSSIA", "16:33", new UserName("nadav")));
            messageList.add(new Message(6, "YEET!", "16:33", new UserName("ori")));


            chatDao.insert(new Chat(1, new User("nadav1", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(2, new User("nadav2", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(3, new User("nadav3", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(4, new User("nadav4", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(5, new User("nadav5", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(6, new User("nadav6", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));
            chatDao.insert(new Chat(7, new User("nadav7", "pass", "nadavv", String.valueOf(R.drawable.ic_launcher_foreground)), messageList));

            return null;
        }
    }

}
