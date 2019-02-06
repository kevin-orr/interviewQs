# Some questions relating to the java.util.concurrent package

### Q. *Explain what happens when you call get() on a FutureTask?*<br>
*FutureTask* implements the *Future* interface and if you check out what it says for *get()* you might be surprised to find that it blocks, or as the api docs state, the call doesn't immediately return but **"*waits if necessary for the computation to complete, and then retrieves its result.*"** <br>
So the moral of the story is to be caeful when checking for the result of a Task!<br>
If you really need to check for task completion you can call ***isDone()*** or if you can't hang around then call the ***get()*** version which takes a timeout.<br>

### Q. *What's printed out to stdout when this program executes:<br>(a) the threads print 1-1000 one after the other, or <br>(b) all threads printing 1-1000 at the same time?*<br>

```java

public class App {

    public static void main(String[] args) {

        Worker worker1 = new Worker(new Counter());
        Worker worker2 = new Worker(new Counter());
        Worker worker3 = new Worker(new Counter());

        worker1.start();
        worker2.start();
        worker3.start();
    }
}

class Worker extends Thread {
    private Counter counter;

    public Worker(Counter counter) {
        this.counter = counter;
    }
    public void run() {
        counter.printTheCount();
    }
}

class Counter {
    public synchronized void printTheCount() {
        for (int i = 1; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
     
```
<br>
The answer is (b).
Although the method on Counter is synchronized, it's only going to be synchronized for calls on that object. We've passed a new Counter object to each Worker thread and the run() method calls the synchronized method on that object. So all 3 threads will print to 1-1000 to stdout but will be at the mercy of the sceduler - resulting in the print statements being interleaved - output from each thread will not be in blocks of 1-1000. <br> To make each thread print out it's full 1-1000 then use one instance of Counter as in: <br>

```java

public class App {

    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Worker worker1 = new Worker(counter);
        Worker worker2 = new Worker(counter);
        Worker worker3 = new Worker(counter);

        worker1.start();
        worker2.start();
        worker3.start();
    }
}
...
     
```

<br>
or, change the **printTheCount()** method to **static**, as in
<br>

```java

public class App {

    public static void main(String[] args) {
        Worker worker1 = new Worker(new Counter());
        Worker worker2 = new Worker(new Counter());
        Worker worker3 = new Worker(new Counter());

        worker1.start();
        worker2.start();
        worker3.start();
    }
}

class Counter {
    /* add static modifier */
    public static synchronized void printTheCount() {
        for (int i = 1; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
```
<br>

