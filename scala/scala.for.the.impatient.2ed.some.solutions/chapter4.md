### Q.1<br>

```scala
val nonDiscountedPrices = Map(("iphone", 500.50), ("imac", 2040.99), ("apple", 0.50))
val discountedPrices = scala.collection.mutable.Map[String, Double]()
for((k,v) <- nonDiscountedPrices) discountedPrices(k) = v - v * 0.1

```
<br>
In Groovy:

```groovy
def nonDiscountedPrices = ["iphone": 500.50, "imac": 2040.99, "apple": 0.50]
nonDiscountedPrices.collectEntries{ k, v -> ["$k" :v - v*0.1]}
//  [iphone:450.450, imac:1836.891, apple:0.450]
```
<br>

### Q.2<br>
I'm going to suck in the text from *Project Gutenberg’s Alice’s Adventures in Wonderland, by Lewis Carroll*  
```scala
lazy val in = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream)
val map = scala.collection.mutable.Map[String, Int]()
while (in.hasNext()) {
  val word = in.next()
  map(word) = map.getOrElse(word, 0) + 1
}
map.toString
```
<br>

In Groovy:
```groovy
def inn = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream())
def map = [:]
while (inn.hasNext()) {
    def word = inn.next()
    map[word] = map.getOrDefault(word, 0) + 1
}
map.toString()
```
<br>

### Q.3<br>
```scala
lazy val in = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream)
var map = Map[String, Int]()
while (in.hasNext()) {
  val word = in.next()
  map = map + ((word, map.getOrElse(word, 0) + 1))
}
map.toString
```
<br>

### Q.4<br>
```scala
lazy val in = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream)
var map = SortedMap[String, Int]()
while (in.hasNext()) {
  val word = in.next()
  map = map + ((word, map.getOrElse(word, 0) + 1))
}
map.toString
```
<br>

### Q.5<br>
```scala
lazy val in = new java.util.Scanner(new java.net.URL("http://www.gutenberg.org/files/11/11-0.txt").openStream)
var map = TreeMap[String, Int]()
while (in.hasNext()) {
  val word = in.next()
  map = map + ((word, map.getOrElse(word, 0) + 1))
}
map.toString
```
<br>

### Q.6<br>
```scala
val daysOfWeek = Map[String, Int](("SUNDAY", java.util.Calendar.SUNDAY),
                                  ("MONDAY", java.util.Calendar.MONDAY),
                                  ("TUESDAY", java.util.Calendar.TUESDAY),
                                  ("WEDNESDAY", java.util.Calendar.WEDNESDAY),
                                  ("THURSDAY", java.util.Calendar.THURSDAY),
                                  ("FRIDAY", java.util.Calendar.FRIDAY),
                                  ("SATURDAY", java.util.Calendar.SATURDAY))

val linky = LinkedHashMap[String, Any]()
for((day,value) <- daysOfWeek) yield linky.put(day,  {println(s"Inserting $day")})
for((day,value) <- linky) println(day)            

```
<br>

### Q.8<br>
```scala
def minmax(values: Array[Int]) = (values.min, values.max)            

```
In Groovy, for some reason, tuples aren't first class citizens like in Scala or Python!
```groovy
def v = [0, -1, 2, 66, 3]
def minmax = {a -> new Tuple2(a.min(), a.max())}
minmax(v).first
minmax(v).second
```

<br>

<br>

### Q.9<br>
```scala
def lteqgt(values: Array[Int], v: Int) = (values.filter(_ < v).size, values.filter(_ == v).size, values.filter(_ > v).size)

```
<br>
In groovy:

```groovy
def v = [0, -1, 2, -20, 12, 66, 0, 3]
def minmax = {a -> [ltzero:a.grep{it < 0}, zero:a.grep{it == 0}, gtzero:a.grep{it > 0}]}
minmax(v).ltzero // =  [-1, -20]
```
<br>

### Q.10<br>
Use zip to create a Map where the first array represent *keys* and the second the *values* to be associated with those keys:
```scala
"Hello".zip("World").toMap 
```
<br>
In groovy, again not as natural as say Scala or Haskell:

```groovy
def zip = { l, r -> (0..<l.size()).collect { new Tuple2(l[it], r[it])}}
zip("Hello", "world")
//or use the 'transpose()' method on list:
["hello".toList(), "world".toList()].transpose() 

```

<br>
