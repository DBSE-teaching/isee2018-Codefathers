var Settings = require('../keys/settings');


var admin = require("firebase-admin");

var admin = require("firebase-admin");

var serviceAccount = require("/home/joal/AndroidStudioProjects/fcmNodeServer/keys/messaging103ServiceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://messaging103-76608.firebaseio.com"
});
var registrationToken = "eErQ9V4Ih0c:APA91bF_hElLT5bkrKCuzJpKKeEIm4JYtVOD4f7IHhNNVAmJWcuT_myRY0zEmA45q1oHKEVsRAUjeyYiQjQRHyrO9hGakts5aTbrtuJxTU2mDrDbfxnTf446AHFCbJOlJlnWFQM2ZL8e";

var notificationKey = Settings.NOTIFICATION_KEY;

var payload = {
  notification: {
    title: "News Update",
    body: "FIFA Mundial rescheduled."
  }
};

admin.messaging().sendToDeviceGroup(notificationKey, payload)
  .then(function(response) {
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });