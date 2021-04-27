[Monte Carlo method] (https://en.wikipedia.org/wiki/Monte_Carlo_method) is an efficient method to converge to an appreciable result without simulate all the possible combinations. 

A Slam draw is composed by 128 players and 127 matches, the all possibile matches combinations are: 2^64 + 2^32 + 2 ^16 + 2^8 + 2^4 + 2^2 + 2 = 1.844674407·10^19. A very big number. But with this method we can reduce the time to find the right result. 

Setting the variable 'simulations' we can appreciate the powerful of this method. We can start with 1000, then 10.000 and finally 100.000. This last number is the right one to have a 2 decimal places in winning percentage. That's enough! It's useless increasing the simulations number.

On this repository is stored the Australian Open 2009 draw (PlayersAO20019.txt). For every player is stored his Elo ranking (based on chess ranking method adjusted by Jeff Sackmann).

An alternative ranking method is using the ATP points (PlayersATPts.tx) in order to classify the current player powerful

The last one is a "fake" example with the same score for all players
