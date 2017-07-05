# Some Scala Q's<br>
### Q. *How might we pass a List[Int] or Vector[Int] to a function that expects variable args? (pcq)*
For example, suppose we have the function **sum** below. When we try to pass a List[Int] as the argument we get a compiler error.
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

```bash
 :_* 
```

to force the List or Range to be considered as a Seq[Int]<br>

```scala
sum(1,2,3)           // gives 6 as expected, however,
sum(List(1,2,3) :_*) // this now gives 6 as expected 
sum(1 to 3 :_*)      // and this also gives 6 as expected
```
<br>

### Q. *The Scala compiler maps an Array[Int] to what?*
Because everything in Scala is an object, your first guess might be that it gets maps to Integer[] on the VM but in fact the Scala compiler does some optimisation to make sure it gets mapped to int[]!

### Q. *How would you compare 2 Arrays?*
```scala
val first = Array(1,2,3)
val second = Array(1,2,3)
val same = first.sameElements(second)
// or
same = first.deep == second.deep
```
<br>


### Q. *Given an array of integers, write a function that returns a new array with same order as original but with all negative numbers removed except for the very first negative number in the collection. What if the order of the elements isn't important?*<br>
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

```scala
def func(array: Array[Int]) : Array[Int] = {
  val (up2AndIncludingFirstNegative, afterFirstNegative) = array.splitAt(array.indexWhere(_ < 0) + 1)
  up2AndIncludingFirstNegative ++ (for(i <- afterFirstNegative if(i >= 0)) yield i)
}
```
<br>
if the order isn't important we could do something like this:

```scala
def func(array: Array[Int]): Array[Int] = {
  val (positives, negatives) = array.partition(_ >=0)
  positives ++ negatives.take(1)
}
```

<br>
