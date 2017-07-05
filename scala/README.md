# Some Scala Q's<br>
### *How might we pass a List[Int] or Vector[Int] to a function that expects variable args? (pcq)*
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

### *The Scala compiler maps an Array[Int] to what?*
Because everything in Scala is an object, your first guess might be that it gets maps to Integer[] on the VM but in fact the Scala compiler does some optimisation to make sure it gets mapped to int[]!
