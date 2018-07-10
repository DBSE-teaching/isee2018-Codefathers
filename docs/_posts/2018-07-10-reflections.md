---
layout: post
title: "Reflections"
date: 2018-07-10
---



This semester started and the projects were soon to follow. Alaba and Ejas knew each other from previous work, and Dimitris and Ilias were students in the same class back in their home University. With no opportunity or much getting-to-know time the two groups introduced each other and a team was assembled to work on the project of this year’s ISEE.

Surprisingly that was the last obstacle when it came to external parameters. Technological problems were non existant, as apps like WhatsApp, Messenger or Discord are well established social-media territory with little to no interuption to their smooth services.Working on Software, though, was another thing. Although all of us had from little to big experience regarding programming and developing, there were still many technologies that were a complete mystery. 

![Steps image]({{site.baseurl}}/images/reflections/2.jpg " ")

To begin with Android developing itself was a subject that most of us didn't have the simplest clue of how it works, not to mention that even Java itself was quite a struggle, given that some of us came from a functional programming background like functional Javascript, or scripting background and the only relation we had with Object Oriented Approaches were some old University projects on Windows Forms with .net. What is more noone else had experience with git, at least not at the colaborating part of git. 
![Steps image]({{site.baseurl}}/images/reflections/3.jpg " ")

Here we should also mention firebase. Nobody knew exactly how to achieve in-app comunication: There was a faint idea on the direction we should follow, but we had no idea how firebase worked. Was it a push notification service? was it something else? No clue. At the beggining we thought it would actually work as socket server, so we built the application logic on an offline SQLite database that will sync appropriately when there is something new on the socket channel. This idea actually came due to some member's experience with socket.io and webSockets with Javascript. It goes without saying that firebase was what we thought it was. We actually found out that it was a live Database that automatically syncronize with the client, built in on a giant JSON tree (kinda like NoSql databases). A lot of time was wasted implementing those beautiful Entities for SQLite. We need to elaborate a bit more on the NoSql part of firebase. This was something we had never faced before. We actually changed the structure of our Model Classes at least twice in order to make them usable for queries. And queries was a nightmare at least in the beggining. No more "SELECT * FROM Trackings t JOIN Followers f on t.creator = f.id  WHERE follower=someoone". It was a completely different way of thinking. Was it simple? In the end it was, but it took us quite some time to become familiar.

![Steps image]({{site.baseurl}}/images/reflections/4.jpg " ")

What is more, there were times during development that we actually spent hours trying to fix problems that we created by some accidental merges and mishappenings. In the beggining it took us quite some time just to even set up the project in git, although it was easier than it might seemed at the moment. Around the last month we managed to achieve a fluent workflow, having already fallen into every possible trap that a begginer in git can fall. 


In the end it was worth it. A lot of experience were gained through the proccess, most of us can now claim to be fluent in Java (something that the author of this blog post never imagined he would say for himself.) and an almost ready for release app came out. Lastly git and github will no longer be a terryfying thought, we did made some strong relations with this octapus-cat.

Finally although we weren't able to enforce scrum principles 100% and we definetely fall a lot out of those sprint deadlines (mainly because we didn't know most of the tools we were dealing with) This course gave us the fundamentals that we might need in order to work into a professional enviroment at some point in our lives.

![Steps image]({{site.baseurl}}/images/reflections/5.jpg " ")



