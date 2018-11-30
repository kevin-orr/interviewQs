# Workday Scala/Java Test

## Objective
Create a simple command line (ascii text) API mashup of GitHub and Twitter APIs. Search
for "Reactive" projects on GitHub, then for each project search for tweets that mention it.
You should output a summary of each project with a short list of recent tweets, in JSON
format.

## Instructions
 Complete this assignment in Scala (preferred) or Java.
 We expect from you to submit a Production quality like code with all what this
means to you.
 You will require a Twitter consumer key and secret, to complete this.
 Any private information and should not be included in your submission. However, you
must make this information easily configurable via a configuration or properties file.
 Twitter API is rate limited so you should only try to retrieve tweets for 10 projects.
 The code should be submitted in a .zip or .tar archive with instructions to build and
run the project. We recommend to upload it with Dropbox (or similar) to share it
easily.
## Deadline
 You’ll have up to a week from now onwards to finish it. Any submissions later on
won’t be evaluated.
 Remember that we do not evaluate how fast you code: we will measure your code
quality.
## More information
The GitHub API documentation
https://developer.github.com/v3/search/#search-repositories

Searching for reactive projects
curl https://api.github.com/search/repositories\?q\=reactive


The Twitter API documentation
* https://dev.twitter.com/oauth/application-only
*  https://dev.twitter.com/oauth/overview/application-owner-access-tokens
* https://developer.github.com/v3/activity/events/#list-public-events-for-a-network-of-repositories
 

Confidentiality
This assessment is intended for the recipient only so we request that it is treated in a
confidential manner and not share or released on any 3rd party sites.