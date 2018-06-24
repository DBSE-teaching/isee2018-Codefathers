import * as functions from 'firebase-functions';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

'use strict';


const admin = require("firebase-admin");

const serviceAccount = require("../keys/tripalertServiceAccountKey.json");

import { appUser } from "./model/appUser";
import  * as dbRef from "./model/dbRef";
import * as mSettings from "./keys/mSettings"

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://tripalert-18e7f.firebaseio.com"
});


// As an admin, the app has access to read and write all data, regardless of Security Rules
const db = admin.database();
const ref = db.ref("restricted_access/secret_document");
ref.once("value", function(snapshot) {
  console.log(snapshot.val());
});


const locationPath = "/users/user/track/lastKnownLocation";
const estimatedTimePath = "/users/user/track/estimatedTime";
const userPath= "/users/user";
const path = "/userId";

exports.hi1 = functions.database.ref("/users/userId/track/detination").onCreate((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();
      //console.log('Uppercasing', context.params.pushId, original);
      console.log("Location changed 1, a new user is added");
});
exports.hi2 = functions.database.ref("/users/userId/track/destination").onWrite((snapshot, context) => {
  // Grab the current value of what was written to the Realtime Database.
  const original = snapshot.after.val();
  //console.log('Uppercasing', context.params.pushId, original);
  console.log("Location changed 2, a new user is added");
  console.log(original);
});
exports.hi3 = functions.database.ref("/users/userId/track").onUpdate((snapshot, context) => {
  // Grab the current value of what was written to the Realtime Database.
  const original = snapshot.after.val();
  //console.log('Uppercasing', context.params.pushId, original);
  console.log("Location changed 3, a new user is added");
  console.log(original);
  //then send notification
  const registrationToken = mSettings.TOKEN_MOTO;

//notification, data or combination of the two can be use.
const payload = {
    notification: {
      title: "This is a Notification",
      body: "This is the body of the notification message."
    }
  };
  
   const options = {
    priority: "high",
    timeToLive: 60 * 60 *24
  };
  //single dev
  admin.messaging().sendToDevice(registrationToken, payload, options)
  .then(function(response) {
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });
});



exports.insertIntoDB = functions.https.onRequest((req, res) => {
  const text = req.query.text;
  admin.database().ref("/test").push({text: text}).then(snapshot => {
      res.redirect(303, snapshot.ref);
  })
});


/*
// Sends a notifications to all users when a new message is posted.
exports.sendNotifications = functions.database.ref('/messages/{messageId}').onCreate(snapshot => {
  // Notification details.
  const text = snapshot.val().text;
  const payload = {
    notification: {
      title: `${snapshot.val().name} posted ${text ? 'a message' : 'an image'}`,
      body: text ? (text.length <= 100 ? text : text.substring(0, 97) + '...') : '',

    }
  };

  let tokens = []; // All Device tokens to send a notification to.
  // Get the list of device tokens.
  return admin.database().ref('fcmTokens').once('value').then(allTokens => {
    if (allTokens.val()) {
      // Listing all tokens.
      tokens = Object.keys(allTokens.val());

      // Send notifications to all tokens.
      return admin.messaging().sendToDevice(tokens, payload);
    }
    return {results: []};
  }).then(response => {
    // For each notification we check if there was an error.
    const tokensToRemove = {};
    response.results.forEach((result, index) => {
      const error = result.error;
      if (error) {
        console.error('Failure sending notification to', tokens[index], error);
        // Cleanup the tokens who are not registered anymore.
        if (error.code === 'messaging/invalid-registration-token' ||
            error.code === 'messaging/registration-token-not-registered') {
          tokensToRemove[`/fcmTokens/${tokens[index]}`] = null;
        }
      }
    });
    return admin.database().ref().update(tokensToRemove);
  }).then(() => {
    console.log('Notifications have been sent and tokens cleaned up.');
    return null;
  });
});
*/