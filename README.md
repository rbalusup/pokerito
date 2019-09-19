# pokerito
How does the poker game look like?

We have cash games and tournaments. <br> 
In every tournaments there are N tables. <br> 
In cash game there are N tables too. <br> 
On each table there are N users. One user can play one 1 or more tables simultaneously. <br> 
Each user has money balance in USD. And on the table it has poker chips.
Every tournament has time of starting.
Every table has 1 deck of cards. One deck of card consider 52 cards.
If server crashes we have to restore all deck of cards. <br>

It means that we don't need to store data in NoSQL. SQL is enough. To much connections. <br>

Front end exchange data with back end via grpc. <br>
Back end structure: <br>

Logic service - handle all request from front end. And it decides what to do further.
Table service - responsible for tables.
User service - responsible for users (money, auth, ).
Cash service - responsible for all cash games and their life cycle.
Dealer service - responsible for distribution of cards.