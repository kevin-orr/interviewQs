# Create a simple command line API mashup of GitHub and Twitter APIs. 

Search for "Reactive" projects on GitHub, then for each project search for tweets that mention it. 
You should output a summary of each project with a short list of recent tweets, in JSON format.


## Instructions
- Complete this assignment in Scala or Java or whatever language you'd like.
- Expect Production quality like code.
- You will require a Twitter consumer key and secret, to complete this.
- Any private information should not be included in your submission. However, you must make this information easily configurable via a configuration or properties file.
- Twitter API is rate limited so you should only try to retrieve tweets for 10 projects.

## Deadline
- Take up to a week to finish it.


## More information
### The GitHub API documentation
- https://developer.github.com/v3/search/#search-repositories

### Searching for reactive projects
- curl https://api.github.com/search/repositories\?q\=reactive

### The Twitter API documentation
- https://dev.twitter.com/oauth/application-only
- https://dev.twitter.com/oauth/overview/application-owner-access-tokens
- https://developer.github.com/v3/activity/events/#list-public-events-for-a-network-of-repositories
