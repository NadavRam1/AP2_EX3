const express = require('express');
const { checkToken } = require('../controllers/token.js')
const { saveToken, sendNotification } = require('../controllers/firebase.js');
const router = express.Router();

router.post('/api/firebase/saveToken', checkToken, saveToken);
router.post('/api/firebase/sendNotification', checkToken, sendNotification);
module.exports = router;