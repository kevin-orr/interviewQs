# Some questions relating to the java.util.concurrent package

* ### *Explain what happens when you call get() on a FutureTask?*<br>
*FutureTask* implements the *Future* interface and if you check out what it says for *get()* you might be surprised to find that it blocks, or as the api docs state, the call doesn't immediately return but **"*waits if necessary for the computation to complete, and then retrieves its result.*"** <br>
So the moral of the story is to be caeful when checking for the result of a Task!<br>
If you really need to check for task completion you can call ***isDone()*** or if you can't hang around then call the ***get()*** version which takes a timeout.

