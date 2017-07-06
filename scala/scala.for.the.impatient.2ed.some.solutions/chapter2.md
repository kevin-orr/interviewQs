### Q.1<br>

```scala
def signum(number:Int) : Int = if(number >= 0) number else -number
```
<br>

### Q.2<br>

Run the following in a worksheet:
```scala
val empty = {}
```
<br>
You see that the value is *( )* and the type is *Unit*.
<br>

### Q.4<br>

```scala
for(i <- 10.to(0, -1)) println(i)
```
<br>

### Q.5<br>
```scala
def countdown(n: Int) = for(i <- n.to(0, -1)) println(i)
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
```scala
def unicodes(s: String): Long = {
  if(s.length() == 0) 1L
  else s.head.toLong * unicodes(s.tail)
}  
```
<br>

### Q.10<br>

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
<br>


