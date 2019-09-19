# pokerito
How does the poker game look like?

Thoughts. <br>
We have cash games and tournaments. <br> 
In every tournament there are N tables. <br> 
In every cash game there are N tables too. <br> 
On each table there are N users. One user can play on one or more tables simultaneously. <br> 
Each user has money balance in USD. On the table it has poker chips. <br>
Every tournament has time of starting. <br>
Every table has 1 deck of cards. One deck of card consider 52 cards. <br>
If server crashes we have to restore all deck of cards. <br>

It means that we don't need to store data in NoSQL. SQL is enough. To much connections. <br>

Front end exchange data with back end via grpc. <br>
Back end structure: <br>

Logic service - handle all request from front end. And it decides what to do further. <br>
Table service - responsible for tables. <br>
User service - responsible for users (money, auth, ). <br>
Cash service - responsible for all cash games and their life cycle. <br>
Dealer service - responsible for distribution of cards. <br>
