---
layout: post
title: "System Design"
date: 2018-05-27
---

![Steps image]({{site.baseurl}}/images/systemDesign/intro2.png "Steps towards the first stable release")
 
We are down to, almost, the last phase before we start the actual implemetation, that means we got some diagrams to write about today along with some other things. 


# Behaviour  
Firstly we will look through how the application will  behave in one certain scenario. 

The case scenario is as follows: The publisher is creating a tracking by filling the corresponding form and choosing his followers and then publish it. After that the tracking details along with the follower list that are actively following the publisher right now. 
The publisher doesn't arrive in his destination on time, that is why a a prompt will appear in the publisher's screen so that he can chose if he wants to abort the opeation and explain with a message the reason or continue and recalculate. The publisher does not respond and therefore the follower will be notified tha user is unresponsive. We can see this behaviour in the following diagrams 

![Sequence Diagram]({{site.baseurl}}/images/systemDesign/sequence-d.jpg " ")

![state Diagram]({{site.baseurl}}/images/systemDesign/state-d.png " ")

# Classes and patterns
We specified some first drafts of  Interfaces, abstract classes and concrete classes that are going to be  "hopefully" used in our design. We tried to specify them in a way so that we can easily make our code readable and as extensible as possible. But as all things in life, those specifications might change when we get into the implementation part.

![class Diagram]({{site.baseurl}}/images/systemDesign/class-d.png " ")

Let's look trhough some of the basic classes: 

* Observable: This is abstract class which follows the observer pattern. The is the class which other classes(IObservers) need to monitor.

* The ObserverImpl implements the interface (IObserver).
Subject class is an Observable with additional features. This include setting up the track and maintaining relationship with other necessary classes.

* StatusList: This is an enumeration created to hold all possible status of the subject.

* StatusSelector: This is a service class which is responsible for methods logic that triggers change of status in the Subject class.

* IMessage: Interface which is responsible for all messaging service. This enhances the ability to modify our message types and channels

As can somebody see through the class diagram we used the observer design pattern. This will prove extremely usefull since it is ideal for event driven application like ours.

The Observer pattern is used in this design and we have named our classes according to this pattern.

# How do we plan to work

With the Implementation phase finally on the horizon, we have to make some plans on how we will start developing. 

firstly, decide to devote a small 2-3 sprint for creating a very simple application by following the git workflow and try to cooperate through that. This may seem not unimportant but it will definetely help us to avoid some mistakes during the development of the actuall application.

 After that, we are going to start tackling our product backlog. Therefore we plan an one week, or even less, sprint and hopefully try to implement the basic functionality of the app such us tracking creation, and have look a bit into the notification system. 

From this point we will revaluate what we have done and create more sprints accordingly.

Finally we have already make a mockup of the application ui that the client approved and we will try to follow it as close as possible

# No key revisions... only dreams

We can proudly claim that there weren't any changes. We are still confident that with the current structure we will be able to implement the requirements and the objectives that we have already specified. 

Let's hope that our confidence is well based and there will be no suprises. 

*[Spoiler alert]* from our experience, this is just a developer's utopian dream, and utopian dreams are called like this for one reason: they are never fulfilled 

![ Diagram]({{site.baseurl}}/images/systemDesign/diagrams.png " ")
