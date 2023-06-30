const { getMessages, postMessage } = require('../models/messages');
const { getData } = require('../models/token');
const { getUser } = require('../models/users');

const sendMessage = async (req, res) => {
    const me =  await getData(req.headers.authorization);
    const chat = await postMessage(req.body, parseInt(req.params.id), me);
    if (chat === 500) {
        return res.status(500).end();
    }
    return res.status(200).end();
}

const receiveMessages = async (req, res) => {
    const data = await getData(req.headers.authorization)
    const me = await getUser(data.username);
    const messages = await getMessages(req.params.id, me);
    if (messages === 500) {
        return res.status(500).end();
    }
    return res.status(200).end(JSON.stringify(messages));
}

module.exports = {
    sendMessage,
    receiveMessages,
}