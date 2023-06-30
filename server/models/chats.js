const { MongoClient } = require('mongodb');

async function getChat(id, me) {
    const client = new MongoClient("mongodb://127.0.0.1:27017");
    try {
        await client.connect();
        const db = await client.db('Whatsapp');
        const chats = db.collection('chats');
        const chat = await chats.findOne({ id: id });
        if (!chat) {
            return 401;
        }
        return {
            id: chat.id,
            user: me.username === chat.users[0].username ? chat.users[1] : chat.users[0],
            messages: chat.messages,
            lastMessage: chat.lastMessage
        }
    } catch (error) {
        console.log('chats1' + error);
        return 500;
    } finally {
        await client.close();
    }
}

async function getChats(me) {
    const client = new MongoClient("mongodb://127.0.0.1:27017");
    try {
        await client.connect();
        const db = client.db('Whatsapp');
        const chats = db.collection('chats');
        const res = await chats.find({});
        if (!res) {
            return 401;
        }
        var allChats = [];
        await res.forEach((chat) => {
            if (chat.users.map((user) => { return user.username }).includes(me.username)) {
                allChats.push({
                    id: chat.id,
                    user: me.username === chat.users[0].username ? chat.users[1] : chat.users[0],
                    messages: chat.messages,
                    lastMessage: chat.lastMessage
                });
            }
        });
        return allChats;
    } catch (error) {
        console.log('chats2' + error);
        return 500;
    } finally {
        await client.close();
    }
}

async function postChat(username, me) {
    const client = new MongoClient("mongodb://127.0.0.1:27017");
    try {
        if (me.username === username.username) {
            return 403;
        }
        await client.connect();
        const db = await client.db('Whatsapp');
        const chats = db.collection('chats');
        const users = db.collection('users');
        const existingUser = await users.findOne(username);
        if (!existingUser) {
            return 400;
        }
        const existingChat = await chats.findOne({
            $or: [{
                users: [me,
                    {
                        ...username,
                        displayName: existingUser.displayName,
                        profilePic: existingUser.profilePic
                    }]
            },
            {
                users: [
                    {
                        ...username,
                        displayName: existingUser.displayName,
                        profilePic: existingUser.profilePic
                    }, me]
            }
            ]
        });
        if (existingChat) {
            return 409;
        }
        let nextId = (await chats.stats()).count;
        const chat = {
            id: nextId ? (nextId + 1) : 1,
            users: [me,
                {
                    ...username,
                    displayName: existingUser.displayName,
                    profilePic: existingUser.profilePic
                }],
            messages: [],
            lastMessage: null
        }
        await chats.insertOne(chat);
		const MyChat = {
			id: nextId ? (nextId + 1) : 1,
            user: {
                ...username,
                displayName: existingUser.displayName,
                profilePic: existingUser.profilePic
			},
            messages: [],
            lastMessage: null
        }
        return MyChat;
    } catch (error) {
        return 500;
    } finally {
        await client.close();
    }
}

async function deleteChat(id) {
    const client = new MongoClient("mongodb://127.0.0.1:27017");
    try {
        await client.connect();
        const db = client.db('Whatsapp');
        const chats = db.collection('chats');
        const res = await chats.findOne({ id: id });
        if (!res) {
            return 404;
        }
        await chats.deleteOne({ id: id });
        return 200;
    } catch (error) {
        return 500;
    } finally {
        await client.close();
    }
}

module.exports = {
    getChat,
    getChats,
    postChat,
    deleteChat
}