### Q.1<br>

```scala
def signum(number:Int) : Int = if(number >= 0) number else -number
```
<br>
In Groovy it might look like:

```groovy
def signum = {number -> if(number >= 0) number else -number}

signum(4)
signum(-4)
```

### Q.2<br>

Run the following in a worksheet:
```scala
val empty = {}
```
<br>
You see that the value is ( ) and the type is Unit.
<br>

### Q.4<br>

```scala
for(i <- 10.to(0, -1)) println(i)
```
<br>
In Groovy it might look like:

```groovy
(10..0).each{println(it)}
```
<br>


### Q.5<br>
```scala
def countdown(n: Int) = for(i <- n.to(0, -1)) println(i)
```
In Groovy:
```groovy
def countdown = {n-> (n..0).each{println(it)}}
countdown(10)
```
<br>

### Q.6<br>
```scala
def unicodes(s: String): Long = {
  (for(i <- 0 until s.length()) yield s.codePointAt(i).toLong) product
}  
 ```
<br>

### Q.7/8/9<br>
My initial stab at this was:
```scala
def unicodes(s: String): Long = {
  if(s.length() == 0) 1L
  else s.head.toLong * unicodes(s.tail)
}  
```
<br>
But you could use this which seems more *functional* but what would I know:<br>

```scala
def unicodes(s: String): Long = {
  s.foldLeft(1L)(_ *_.toLong)   
}  
```

In Groovy:
```groovy
def unicodes = {s -> s.inject(1){acc, val -> acc * ((long)val)} } 
unicodes('Hello') 
```

### Q.10<br>
Now this looks like a candidate for refactoring...
```scala
def x2n(x:Double, n:Int) : Double = {
  if(n < 0) 1.0 / x2n(x, -n)
  else {
    if(n == 0) 1.0
    else {
      if(n % 2 == 0) x2n(x, n/2) * x2n(x, n/2)
      else x * x2n(x, n - 1)
    }
  }
}      
```

I like the way Scala has functions that are independent of a class - like C++ for example. To do this in Groovy we need to use a closure but with the def of the closure ahead of the body.
```groovy
def x2n // when closure below is created it needs to see x2n
x2n = {int x, double n ->
  if(n < 0.0) 1.0 / x2n(x, -n)
  else {
    if(n == 0.0) 1.0
    else {
      if(n % 2 == 0.0) x2n(x, n/2) * x2n(x, n/2)
      else x * x2n(x, n - 1)
    }
  }
}
x2n(2, 16)
```

<br>

### Q.11<br>
```scala
implicit class DateInterpolator(val sc: StringContext) extends AnyVal {

    def date(args: Any*): LocalDate = {
      // have we enough args?
      if (args.length != 3) throw new Exception("Invalid Format: expecting '$year-$month-$day'")
      val year:Int  = getValueFor("year", args(0))
      val month:Int = getValueFor("month", args(1))
      val day:Int   = getValueFor("day", args(2))
      LocalDate.of(year, month, day)
    }

    private def getValueFor(yearMonthOrDay:String, s:Any): Int = {
      try {
        s.toString.toInt
      } catch {
        case _: Exception => throw new Exception(s"$yearMonthOrDay = '$s' is not valid Int")
      }
    }
  }  
 ```
<br>

