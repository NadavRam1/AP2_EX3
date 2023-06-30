const { getData } = require('../models/token');
const { getUser } = require('../models/users');
const { saveTokenInDB, sendNotiToUser } = require('../models/firebase');

const sendNotification = async (req, res) => {
    const data = await getData(req.headers.authorization)
    const user = await getUser(data.username);
    const code = await sendNotiToUser(user, req.body);
    if (code === 500) {
        return res.status(500).end();
    }
    return res.status(200).end();
}

const saveToken = async (req, res) => {
    const data = await getData(req.headers.authorization)
    const code = await saveTokenInDB(req.body.firebaseToken, data.username);
    if (code === 500) {
        return res.status(500).end();
    }
    return res.status(200).end();
}

module.exports = {
    saveToken,
    sendNotification,
}