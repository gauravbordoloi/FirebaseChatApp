const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.firestore
.document('conversation/{docId}')
.onCreate((snap, context) => {
    const chat = snap.data();

    let user = "gitlab";
    if (chat.to === "github") {
        user = "github";
    }

    admin.firestore().collection("meta_data").doc(user).get()
    .then(result => {
        let token = result.data().token;
        const payload = {
            data: {
                title: chat.from,
                body: chat.message
            }
        };

        return admin.messaging().sendToDevice(token, payload).then(result => {
            return console.log("Notification sent!");
        }).catch(e => {
            return console.log(error);    
        });
    })
    .catch(error => {
        console.log(error);
        return error;
    });

});