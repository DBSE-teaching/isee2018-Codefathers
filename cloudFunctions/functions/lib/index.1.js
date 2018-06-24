"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const functions = require("firebase-functions");
// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
'use strict';
const admin = require("firebase-admin");
const serviceAccount = require("../keys/tripalertServiceAccountKey.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://tripalert-18e7f.firebaseio.com"
});
const locationPath = "/users/user/track/lastKnownLocation";
const estimatedTimePath = "/users/user/track/estimatedTime";
const userPath = "/users/user";
// Adds a message that welcomes new users into the chat.
exports.addWelcomeMessages = functions.auth.user().onCreate(user => {
    console.log('A new user signed in for the first time.');
    const fullName = user.displayName || 'Anonymous';
    // Saves the new welcome message into the database
    // which then displays it in the FriendlyChat clients.
    return admin.database().ref('messages').push({
        name: 'Firebase Codefathers',
        //photoUrl: '/images/firebase-logo.png', // Firebase logo
        text: `${fullName} signed in for the first time! Welcome!`,
    }).then(() => {
        console.log('Welcome message written to database.');
    });
});
/*
exports.makeUppercase = functions.database.ref(userPath)
    .onWrite((change, context) => {
      // Only edit data when it is first created.
      if (change.before.exists()) {
        return null;
      }
      // Exit when the data is deleted.
      if (!change.after.exists()) {
        return null;
      }
      // Grab the current value of what was written to the Realtime Database.
      const original = change.after.val();
      console.log('Uppercasing', context.params.pushId, original);
      const uppercase = original.toUpperCase();
      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
      return change.after.ref.parent.child('uppercase').set(uppercase);
    });
    */
exports.simpleDbFunction = functions.database.ref(userPath)
    .onCreate((snap, context) => {
    if (context.authType === 'ADMIN') {
        // do something
    }
    else if (context.authType === 'USER') {
        console.log(snap.val(), 'written by', context.auth.uid);
    }
});
exports.sendWelcomeEmail = functions.auth.user().onCreate((user) => {
    // ...
});
exports.sendByeEmail = functions.auth.user().onDelete((user) => {
    // ...
});
// Listens for new messages added to /messages/:pushId/original and creates an
// uppercase version of the message to /messages/:pushId/uppercase
const path = "/userId/track/destination";
//exports.makeUppercase = functions.database.ref('/messages/{pushId}/original')
exports.makeUppercase = functions.database.ref(path)
    .onCreate((snapshot, context) => {
    // Grab the current value of what was written to the Realtime Database.
    const original = snapshot.val();
    console.log('Uppercasing', context.params.latitude, original);
    const uppercase = original.toUpperCase();
    // You must return a Promise when performing asynchronous tasks inside a Functions such as
    // writing to the Firebase Realtime Database.
    // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
    return snapshot.ref.parent.child('uppercase').set(uppercase);
});
exports.makeUppercase1 = functions.database.ref('/userId')
    .onUpdate((change, context) => {
    console.log('change in longlat occurred Uppercasing');
    // Only edit data when it is first created.
    if (change.before.exists()) {
        return null;
    }
    // Exit when the data is deleted.
    if (!change.after.exists()) {
        return null;
    }
    // Grab the current value of what was written to the Realtime Database.
    const original = change.after.val();
    console.log('Uppercasing', context, original);
    const uppercase = original + original;
    // You must return a Promise when performing asynchronous tasks inside a Functions such as
    // writing to the Firebase Realtime Database.
    // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
    return change.after.ref.parent.child('uppercase').set(uppercase);
});
exports.makeUppercase2 = functions.database.ref('/userId')
    .onUpdate(event => {
    console.log('change in longlat occurred Uppercasing');
});
/*Subscribe to topic on behalf of another user
perform write operations on the user's behalf.
!???Make sure to delete the app instance as shown below in order to prevent concurrency issues:
*/
exports.impersonateMakeUpperCase = functions.database.ref('/messages/{pushId}/original')
    .onCreate((snap, context) => {
    const appOptions = JSON.parse(process.env.FIREBASE_CONFIG);
    appOptions.databaseAuthVariableOverride = context.auth;
    const app = admin.initializeApp(appOptions, 'app');
    const uppercase = snap.val().toUpperCase();
    const ref = snap.ref.parent.child('uppercase');
    const deleteApp = () => app.delete().catch(() => null);
    return app.database().ref(ref).set(uppercase).then(res => {
        // Deleting the app is necessary for preventing concurrency leaks
        return deleteApp().then(() => res);
    }).catch(err => {
        return deleteApp().then(() => Promise.reject(err));
    });
});
//# sourceMappingURL=index.1.js.map