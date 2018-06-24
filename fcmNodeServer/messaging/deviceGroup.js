var Settings = require('../keys/settings');

var request = require('request');

var TOKEN1 = Settings.TOKEN_MOTO;
var TOKEN2 = Settings.TOKEN_N27A;
var TOKEN3 = Settings.TOKEN_N26A;
var TOKEN4 = Settings.TOKEN_N26B;
var SENDERID = Settings.SENDERID
var SERVERKEY = Settings.SERVERKEY
//console.log("The unchangeable fruit is " + Settings.TOKEN_MOTO);
console.log("The unchangeable token1 is " + TOKEN1);


var headers = {
     'Authorization': 'key=AAAA5G_VOas:APA91bHrZxf73lRSsPudiVBB7KfKlWW35G_aPIlV0P6q0P4YVRqy0bD3DlCYGdZ7X7rKeLs-6EC9BlJwP7HRcvKNHd0BqP75hgl1YSE-ivdzw31ex5sOUSLugcszqu7eZJjeZwlwfq2x',
     'project_id': SENDERID,
     'Content-Type':     'application/json'
         }

var options = {
     url: 'https://android.googleapis.com/gcm/notification',
     method: 'POST',
     headers: headers,
     json: {'operation': 'create', 
                'notification_key_name': 'alabaoj@gmail.com',
                'registration_ids': [TOKEN1, TOKEN2, TOKEN3, TOKEN4]}
}
// alt options to add or remove devices
var optionsAdd = {
    url: 'https://android.googleapis.com/gcm/notification',
    method: 'POST',
    headers: headers,
    json: {'operation': 'create', 
               'notification_key_name': 'alabaoj@gmail.com',
               'registration_ids': [TOKEN4]}
}

var optionsDelete = {
    url: 'https://android.googleapis.com/gcm/notification',
    method: 'POST',
    headers: headers,
    json: {'operation': 'create', 
               'notification_key_name': 'alabaoj@gmail.com',
               'registration_ids': [TOKEN1]}
}
request(options, function (error, response, body) {
                  console.log(body)
})


