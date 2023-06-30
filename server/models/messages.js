const { MongoClient } = require('mongodb');
const { getUser } = require('./users');
const { getChat } = require('./chats');

async function postMessage(message, chatId, me) {
    const client = new MongoClient("mongodb://127.0.0.1:27017");
    try {
        const sender = await getUser(me.username);
        await client.connect();
        const db = client.db('Whatsapp');
        const chats = db.collection('chats');
        var today = new Date();
        var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
        var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
        var dateTime = date + ' ' + time;
		let nextId = Object.keys((await chats.findOne({id : chatId})).messages).length;
		nextId = nextId ? (nextId + 1) : 1
        await chats.updateOne(
            { id: chatId },
            {
                $push: {
                    messages: {
                        $each: [{ id: nextId, created: dateTime, sender: sender, content: message.msg }],
                        $position: 0
                    }
                },
                $set: { lastMessage: { id: nextId, created: dateTime, sender: sender, content: message.msg } }
            }

        )
        return 200;
    } catch (error) {
        return 500;
    } finally {
        await client.close();
    }
}

async function getMessages(chatId, me) {
    try {
        const chat = await getChat(parseInt(chatId), me);
        return chat.messages;
    } catch (error) {
        return 500;
    }
}

module.exports = {
    getMessages,
    postMessage,
}