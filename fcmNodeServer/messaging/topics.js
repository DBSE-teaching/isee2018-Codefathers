
var admin = require("firebase-admin");

var serviceAccount = require("/home/joal/AndroidStudioProjects/fcmNodeServer/keys/messaging103ServiceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://messaging103-76608.firebaseio.com"
});
var registrationToken = "eErQ9V4Ih0c:APA91bF_hElLT5bkrKCuzJpKKeEIm4JYtVOD4f7IHhNNVAmJWcuT_myRY0zEmA45q1oHKEVsRAUjeyYiQjQRHyrO9hGakts5aTbrtuJxTU2mDrDbfxnTf446AHFCbJOlJlnWFQM2ZL8e";
//notification, data or combination of the two can be use.
 // subscribe to topic; multi
var registrationTokens = [ "<registration token one>",
		           "<registration token two>" ];
var topic = "finance";

admin.messaging().subscribeToTopic(registrationTokens, topic)
  .then(function(response) {
    console.log("Successfully subscribed to topic:", response);
  })
  .catch(function(error) {
    console.log("Error subscribing to topic:", error);
  });
//----------
var payload = {
    notification: {
      title: "NASDAQ News",
      body: "The NASDAQ climbs for the second day. Closes up 0.60%."
    }
  };
  //sending topic; wildcard could be used, conditional also
  var topic = "finance";
  //var topic = "news_*";
  // var condition = "'news' in topics && ('finance' in topics || 'politics' in topics')";
  
  admin.messaging().sendToTopic(topic, payload)
    .then(function(response) {
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      console.log("Error sending message:", error);
    });

 //   var condition = "'news' in topics && ('finance' in topics || 'politics' in topics')";
// alt using condition
// admin.messaging().sendToCondition(condition, payload)
//     .then(function (response) {
//         console.log("Successfully sent message:", response);
//     })
//     .catch(function (error) {
//         console.log("Error sending message:", error);
//     });