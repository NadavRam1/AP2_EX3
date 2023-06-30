const FCM = require('fcm-node');
var serverKey = 'AAAA1epsdA0:APA91bEMZGJkHdk2GTkVaasA6drN5qbrmmms4XPg6PrnfNygHahSBkHrNs4VDGJZLpPJkNQ-7cDSdE-S8iLgOwHFTkn-27yc9HUtr-mNvreLYlsdKf4e34JE3aIlS4OcPZwaZXZ-4kDk'
var fcm = new FCM(serverKey);
var tokens = {};

async function sendNotiToUser(me, chat) {
	console.log(chat);
	console.log(me);
	token = tokens[chat.user.username];
	var noti = {
		to: token,
		notification: {
			title: me.displayName,
			body: chat.messages[chat.messages.length - 1].content,
		},
	};
	fcm.send(noti, (err, response) => {
		if (err) {
			console.log("Firebase failed: " + err);
			console.log("Response:" + response);
		}
	});
	return 200;
}

async function saveTokenInDB(token, username) {
	tokens[username] = token;
	return 200;
}

module.exports = {
	sendNotiToUser,
	saveTokenInDB,
}