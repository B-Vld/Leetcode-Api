# Leetcode-Api

Leetcode problem fetcher using graphql queries

## Example Requests

Fetching the current daily challenge :

`curl -i -H 'Accept: application/json' https://api4leetcode.herokuapp.com/api/daily-challenge`

```json

{
  "questionId" : "744",
  "title" : "Network Delay Time",
  "link" : "https://leetcode.com/problems/network-delay-time/",
  "difficulty" : "MEDIUM",
  "acceptanceRate" : "49.71429855239607",
  "topicTags" : [ "Depth-First Search", "Breadth-First Search", "Graph", "Heap (Priority Queue)", "Shortest Path" ],
  "likes" : 4663,
  "dislikes" : 299,
  "date" : "2022-05-14"
}

```

Fetching a random question based on difficulty levels **[EASY,MEDIUM,HARD]** :

`curl -i -H 'Accept: application/json' https://api4leetcode.herokuapp.com/api/random-question/EASY`

```json

{
  "questionId" : "717",
  "title" : "1-bit and 2-bit Characters",
  "link" : "https://leetcode.com/problems/1-bit-and-2-bit-characters/",
  "difficulty" : "EASY",
  "acceptanceRate" : "46.19098473724024",
  "topicTags" : [ "Array" ],
  "likes" : 670,
  "dislikes" : 1725,
  "date" : "2022-05-14"
}

```

## License

[MIT](https://choosealicense.com/licenses/mit/)
