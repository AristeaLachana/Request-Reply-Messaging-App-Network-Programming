# Request-Reply-Messaging-App-Network-Programming
Project for university | understanding Network Programming and Client - Server Model.

Network Programming using RMI.

In this system, users will be able to create accounts and exchange messages to each other. 

The system has:

- Server Program, which is able to manage at the same time multiple different requests.

- Client Programs and each of them has the ability to send requests to the Server.


# Server


**Command execution:**

*java server < port number >*


# Client


**Command execution:** 

*java client < ip > < port number > < FN_ID > < args >*

ip: Server IP address

FN_ID: ID for the function that is about to be executed


- FN_ID=1: Creates an account

- FN_ID=2: Shows accounts

- FN_ID=3: Sends Message

- FN_ID=4: Shows inbox

- FN_ID=5: Reads Message

- FN_ID=6: Deletes Message


# Auth Token

No user can read data from another user.
No user can send messages pretending to be another user.

That's why we use AuthToken in every Request, to make sure that the data are safe. 
The Server should correlate each AuthToken with an account (when the account is created).

