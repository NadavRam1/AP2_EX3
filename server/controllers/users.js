const { getUser, postUser } = require('../models/users.js');


const addUser = async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    const status = await postUser(req.body)
    if (status === 409) {
        return res.status(409).end("User already exists!");
    }
    return res.status(200).end();
}

const receiveUser = async (req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    const user = await getUser(req.params.username);
    if (user === 401) {
        return res.status(401).end();
    } else {
        return res.status(200).end(JSON.stringify(user));
    }
}

module.exports = {
    addUser,
    receiveUser,
}