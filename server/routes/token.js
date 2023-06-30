const express = require('express');
const { signToken } = require('../controllers/token');
const router = express.Router();

router.post('/api/Tokens', signToken);
module.exports = router;