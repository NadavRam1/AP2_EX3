const express = require('express');
const { checkToken } = require('../controllers/token.js')
const { receiveMessages, sendMessage } = require('../controllers/messages.js');
const router = express.Router();

router.get('/api/Chats/:id/Messages', checkToken, receiveMessages);
router.post('/api/Chats/:id/Messages', checkToken, sendMessage);
module.exports = router;