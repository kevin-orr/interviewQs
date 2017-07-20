# Some Scala Q's<br>

I'm also working through the problems from the excellent book 
[Scala for the Impatient](scala.for.the.impatient.2ed.some.solutions/README.md) so please feel free to take and look and comment.
<br>
<br>

### Q. What's the difference between a trait and a class?<br>
* A trait definition looks exactly like that of a class definition except that it uses the keyword trait.
* A trait cannot have any "class" parameters (i.e., parameters passed to the primary constructor of a class - for now).<br>
* In classes, *super* calls are statically bound, in traits, they are dynamically bound.  
Traits are like Interfaces in Java 8 except traits can also hold state. So were a java dev might use and Interface + Abstract Class, a Scala dev would most likely use a trait.
<br>

### Q. What's the difference between call-by-value and call-by-name?<br>
Both strategies reduce to the same final values as long as:

* the reduced expression consists of pure functions (no side effects), and
* both evaluations terminate.<br>

Call-by-value evaluates every function argument only once, whereas, with call-by-name, a function argument is not evaluated if the corresponding parameter is unused in the evaluation of the function body.
<br>
Cf. [Scala Docs](http://docs.scala-lang.org/glossary/#by-name-parameter) or [Scala Tutorial](http://docs.scala-lang.org/tutorials/tour/by-name-parameters.html) - when a function/method argument is passed by value, the argument expression will first be evaluted before then being passed into the function/method - argument is evaluated only once. <br>Whereas, when an argument expression is passed by name, the parameter is passed into the function/method and evaluated each time the parameter is referenced by name inside the function/method. It's not evaluated if the arugment is not used.<br>You can tell that pass-by-name symantics are used when a parameter is marked with a *rocket*, i.e., *=>*, in front of the parameter type, e.g., (x: => Int).<br>


### Q. How can I create an array/list containing 10 random values?<br>
Worth noting that second argument to *fill* is using pass by name symantics. <br>

```scala
Array.fill(10)(math.random) 
// or
new Array[Number](10).map(x => math.random) 
// or
Array.tabulate(10)( x => math.random))
// or
for(i <- (0 until 10)) yield math.random   
// or
(0 until 10 ).toArray.map(x => math.random)
// or
s"1-".*(10).split('-').map(x => math.random)
// or
math.random.toString().replace("0.","").take(10).toArray
```
<br>


### Q. What's a procedure in Scala?<br>
A procedure is a function that doesn't return a value and is really only used for its side effect.
You can either explicitly include the Unit return type or leave it out along with the =, for example:<br>

```scala
def func(name: String) {
  println(s"Hi there $name")
}
```
<br>or<br>

```scala
def func(name: String) : Unit = {
  println(s"Hi there $name")
}
```
<br>

### Q. What does this function return?
<br>

```scala
def ???(x:Int) {
  val x = 10
  x * x
}
???(2)  // what's the value returned from this function
```
<br>
The function above is actually a procedure - it doesn't return anything.<br>Had we placed an equal sign *=* just before first curly bracket then it would have returned 100 - as in: 
<br>

```scala
def ???(x:Int) = {
  val x = 10
  x * x
}
???(2)  // this now returns 100!
```
<br>

### Q. How might we pass a List[Int] or Vector[Int] to a function that expects variable args? (pcq)<br>
For example, suppose we have the function **sum** below. When we try to pass a List[Int] as the argument we get a compiler error.<br>

```scala
def sum(args: Int*) = {
  (for(arg <- args) yield arg) sum
}

sum(1,2,3)          // gives 6 as expected, however,
// sum(List(1,2,3)) // gives compiler error
// sum(1 to 3)      // gives compiler error etc.
```

<br>
You have to use the special syntax:

```java
 :_* 
```

<br>
This notation tells the compiler to pass each element of args as its own argument to sum, rather than all of it as a single argument:<br>

```scala
sum(1,2,3)           // gives 6 as expected, however,
sum(List(1,2,3) :_*) // this now gives 6 as expected 
sum(1 to 3 :_*)      // and this also gives 6 as expected
```

<br>

### Q. The Scala compiler maps an Array[Int] to what?<br>
Because everything in Scala is an object, your first guess might be that it gets maps to Integer[] on the VM but in fact the Scala compiler does some optimisation to make sure it gets mapped to int[]!
<br>


### Q. How would you compare 2 Arrays?<br>

```scala
val first = Array(1,2,3)
val second = Array(1,2,3)
val same = first.sameElements(second)
// or
same = first.deep == second.deep
```

<br>

### Q. Given an array of integers, write a function that returns a new array with same order as original but with all negative numbers removed except for the very first negative number in the collection. What if the order of the elements isn't important? (pcq)<br>
I've lifted this question from Cay S. Horstmann's excellent book [Scala for the Impatient](https://www.google.nl/webhp?sourceid=chrome-instant&rlz=1C1XYJR_en__726__727&ion=1&espv=2&ie=UTF-8#q=amazon+Cay+S.+horstmann+) and is deffo well worth a read - especially for his solutions to the problem.
<br>

```scala
// So, for example, 
func(Array(1, -2, -3, 7, -1, 1) // ==> Array(1, -2, 7, 1)
func(Array(1, 2, 3, -7)  // ==> Array(1, 2, 3, -7)
func(Array(1, 2, 3, 7)   // ==> Array(1, 2, 3, 7)
func(Array(-1, -2, -3, -7) // ==> Array(-1)
// etc.
```

<br>
Now I'm only new to Scala so there's gonna be much more efficient, sexier and cooler ways to do this but here's an attempt:
<br>

```scala
def func(array: Array[Int]) : Array[Int] = {
  val (up2AndIncludingFirstNegative, afterFirstNegative) = array.splitAt(array.indexWhere(_ < 0) + 1)
  up2AndIncludingFirstNegative ++ (for(i <- afterFirstNegative if(i >= 0)) yield i)
}
```

<br>
if the order isn't important we could do something like this:
<br>

```scala
def func(array: Array[Int]): Array[Int] = {
  val (positives, negatives) = array.partition(_ >=0)
  positives ++ negatives.take(1)
}
```
<br>

### Q. What's the value printed out?<br>

```scala
def loop: Int = loop
val x = loop
println(s"${new java.util.Date()}")
println(s"$x")
```

<br>
The *println* expression is never reached!<br>Using *val* will cause an infinite loop as the compiler tries to evaluate the expression. <br>How about this then?<br>

```scala
def loop: Int = loop
def x = loop
println(s"${new java.util.Date()}")
println(s"$x")
```

<br>
This time we do see something printed out - the current date and time - but again we enter an infinite loop when the compiler tries to evaluate the expression <br> 

```scala
println(s"$x")
```

<br>

### Q. When must you explicitly declare the return type for a function?<br>
Most of the time the compiler will infer the result type but for *recursive* functions you must explicitly declare the return type.
<br>
or
<br>

### Q. Why does this not compile?<br>

```scala
def factorial(n:Int) = {
  if (n == 0) 1
  else n*factorial(n-1)
}
```

<br>
Becasuse we haven't declared the return type.
<br>



