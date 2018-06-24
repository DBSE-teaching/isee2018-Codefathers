
var admin = require("firebase-admin");

var serviceAccount = require("/home/joal/AndroidStudioProjects/fcmNodeServer/keys/messaging103ServiceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://messaging103-76608.firebaseio.com"
});
var registrationToken = "eErQ9V4Ih0c:APA91bF_hElLT5bkrKCuzJpKKeEIm4JYtVOD4f7IHhNNVAmJWcuT_myRY0zEmA45q1oHKEVsRAUjeyYiQjQRHyrO9hGakts5aTbrtuJxTU2mDrDbfxnTf446AHFCbJOlJlnWFQM2ZL8e";

//notification, data or combination of the two can be use.
var payload = {
    notification: {
      title: "This is a Notification",
      body: "This is the body of the notification message."
    }
  };
  
   var options = {
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