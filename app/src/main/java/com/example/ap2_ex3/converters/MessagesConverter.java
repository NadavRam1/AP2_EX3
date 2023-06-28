package com.example.ap2_ex3.converters;

import androidx.room.TypeConverter;

import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.Messages;
import com.example.ap2_ex3.entities.UserName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesConverter {
    @TypeConverter
    public List<Message> StringToMessages(String value) {
        List<Message> messages = new ArrayList<>();

        // Define the pattern to match each message inside curly braces
        Pattern pattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {
            String messageString = matcher.group(1);
            String[] components = messageString.split(",");

            int id = Integer.parseInt(components[0]);
            String time = components[1];
            String content = components[2];
            String sender = components[3];

            Message message = new Message(id, time, content, new UserName(sender));
            messages.add(message);
        }

        return messages;
    }

    @TypeConverter
    public String MessagesToString(List<Message> messages) {
        return messages.toString();
    }
}
